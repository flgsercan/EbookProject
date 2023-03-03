package com.example.zibook

import com.example.zibook.feature_book.domain.model.EpubManifestModel
import com.example.zibook.feature_book.domain.model.EpubResourceModel
import com.example.zibook.EpubConstants.OPF_NAMESPACE
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.zip.ZipEntry

internal class EpubManifestParser {

    fun parse(
        opfDocument: Document,
        validationListeners: ValidationListeners?,
        zipFile: Map<String, Pair<ZipEntry, ByteArray>>
    ): EpubManifestModel {

        val manifestElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, MANIFEST_TAG)
            .orValidationError { validationListeners?.onManifestMissing() }

        val itemModel = manifestElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_TAG)
            ?.orValidationError { validationListeners?.onAttributeMissing(MANIFEST_TAG, ITEM_TAG) }
            ?.map {
                val element = it as Element
                val id = element.getAttribute(ID_TAG)
                    .orNullIfEmpty()
                    .orValidationError {
                        validationListeners?.onAttributeMissing(MANIFEST_TAG, ID_TAG)
                    }
                val href = element.getAttribute(HREF_TAG)
                    .orNullIfEmpty()
                    .orValidationError {
                        validationListeners?.onAttributeMissing(MANIFEST_TAG, HREF_TAG)
                    }
                val mediaType = element.getAttribute(MEDIA_TYPE_TAG)
                    .orNullIfEmpty()
                    .orValidationError {
                        validationListeners?.onAttributeMissing(MANIFEST_TAG, MEDIA_TYPE_TAG)
                    }
                var properties: HashSet<String>? = null
                element.getAttribute(PROPERTIES_TAG)
                    .orNullIfEmpty()
                    .let { property ->
                        if (property != null) {
                            properties = property.split(PROPERTY_SEPARATOR).toHashSet()
                        }
                    }

                val byte = zipFile.filterKeys { key -> key.contains(href?:"",true) }.toList().firstOrNull()?.second?.second
                EpubResourceModel(id, href, mediaType, properties, byte)
            }

        return EpubManifestModel(itemModel)
    }

    companion object {
        private const val MANIFEST_TAG = "manifest"
        private const val ITEM_TAG = "item"
        private const val ID_TAG = "id"
        private const val HREF_TAG = "href"
        private const val MEDIA_TYPE_TAG = "media-type"
        private const val PROPERTIES_TAG = "properties"
        private const val PROPERTY_SEPARATOR = " "
    }
}