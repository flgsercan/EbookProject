package com.example.zibook.feature_book.domain.model

data class EpubBook(
    val path: String? = null,
    val epubOpfFilePath: String? = null,
    val epubTocFilePath: String? = null,
    val epubCoverImage: EpubResourceModel? = null,
    val epubMetadataModel: EpubMetadataModel? = null,
    val epubManifestModel: EpubManifestModel? = null,
    val epubSpineModel: EpubSpineModel? = null,
    val epubTableOfContentsModel: EpubTableOfContentsModel? = null,
    val id : Int? = idCounter()

)
var idCount = 0
fun idCounter () : Int {
    idCount++
    return (idCount - 1)
}