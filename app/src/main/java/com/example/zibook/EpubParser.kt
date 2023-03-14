package com.example.zibook

import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.domain.model.EpubManifestModel
import com.example.zibook.feature_book.domain.model.EpubResourceModel
import java.io.InputStream

/**
 * Main .epub parser class. Allows to input publication path and parse it into model.
 */
class EpubParser() {

    private val decompressor: EpubDecompressor by lazy {
        ParserModuleProvider.epubDecompressor
    }
    private val opfDocumentHandler: OpfDocumentHandler by lazy {
        ParserModuleProvider.opfDocumentHandler
    }
    private val tocDocumentHandler: TocDocumentHandler by lazy {
        ParserModuleProvider.tocDocumentHandler
    }
    private val metadataParser: EpubMetadataParser by lazy {
        ParserModuleProvider.epubMetadataParser
    }
    private val manifestParser: EpubManifestParser by lazy {
        ParserModuleProvider.epubManifestParser
    }
    private val spineParser: EpubSpineParser by lazy {
        ParserModuleProvider.epubSpineParser
    }
    private val tocParserFactory: TableOfContentsParserFactory by lazy {
        ParserModuleProvider.epubTableOfContentsParserFactory
    }
    private val epubCoverHandler: EpubCoverHandler by lazy {
        ParserModuleProvider.epubCoverHandler
    }
    private var validationListeners: ValidationListeners? = null

    /**
     * Function allowing to parse .epub publication into model
     *
     * @param inputPath Path of .epub publication for parsing
     * @param decompressPath Path to which .epub publication will be decompressed
     * @return Parsed .epub publication model
     */
    fun parse(inputStream: InputStream, path: String?): EpubBook {

        val entries = decompressor.decompress(inputStream)

        val zipEntries = entries.values.map { it.first }
        val mainOpfDocument = opfDocumentHandler.createOpfDocument(entries, zipEntries)



        val epubOpfFilePath = opfDocumentHandler.getOpfFullFilePath(entries, zipEntries)

        val epubManifestModel = manifestParser.parse(
            mainOpfDocument,
            validationListeners,
            entries
        )

        val epubMetadataModel = metadataParser.parse(
            mainOpfDocument,
            validationListeners
        )
        val tocDocument = tocDocumentHandler.createTocDocument(
            mainOpfDocument,
            zipEntries,
            epubManifestModel,
            entries,
            epubMetadataModel.getEpubSpecificationMajorVersion()
        )

        val epubTocFilePath = tocDocumentHandler.getTocFullFilePath(
            mainOpfDocument,
            zipEntries,
            epubManifestModel,
            epubMetadataModel.getEpubSpecificationMajorVersion()
        )
        val toc =  tocParserFactory.getTableOfContentsParser(
            epubMetadataModel.getEpubSpecificationMajorVersion()
        )
            .parse(tocDocument, validationListeners, entries)

        val spine = spineParser.parse(mainOpfDocument, validationListeners)

        val sortedManifestList = mutableListOf<EpubResourceModel>()

        spine.orderedReferences?.forEach {
            epubManifestModel.getById(it.idReference)?.let { resource -> sortedManifestList.add(resource) }
        }
        val sortedManifest = EpubManifestModel(sortedManifestList.toList())

        return EpubBook(
            path,
            epubOpfFilePath,
            epubTocFilePath,
            epubCoverHandler.getCoverImageFromManifest(epubManifestModel),
            epubMetadataModel,
            epubManifestModel,
            sortedManifest,
            spine,
           toc
        )
    }

    /**
     * Setter method that calls the lambda expressions passed in the parameter
     *
     * @param init lambda expressions to call
     */
    fun setValidationListeners(init: ValidationListenersHelper.() -> Unit) {
        val validationListener = ValidationListenersHelper()
        validationListener.init()
        this.validationListeners = validationListener
    }



}