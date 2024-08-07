package com.example.zibook

import com.example.zibook.EpubConstants.NCX_NAMESPACE
import com.example.zibook.feature_book.domain.model.EpubTableOfContentsModel
import com.example.zibook.feature_book.domain.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.util.zip.ZipEntry

internal class Epub2TableOfContentsParser : EpubTableOfContentsParser() {

    private var validationListeners: ValidationListeners? = null

    override fun parse(
        tocDocument: Document?,
        validationListeners: ValidationListeners?,
        zipFile: Map<String, Pair<ZipEntry, ByteArray>>
    ): EpubTableOfContentsModel {


        this.validationListeners = validationListeners
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        tocDocument?.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_MAP_TAG)
            .orValidationError {
                validationListeners?.onTableOfContentsMissing()
            }
            ?.childNodes.forEach {
                if (it.isNavPoint()) {
                    createNavigationItemModel(it).forEach { navItem ->  tableOfContentsReferences.add(navItem) }
                } else {
                    orValidationError {
                        validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, NAV_POINT_TAG)
                    }
                }
            }
        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    override fun createNavigationItemModel(
        node: Node,
    ): List<NavigationItemModel> {
        val element = node as Element
        val navItems = mutableListOf<NavigationItemModel>()

        val id = element.getAttribute(ID_ATTR)
            .orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, ID_ATTR)
            }
        val label = element.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_LABEL_TAG)
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, NAV_LABEL_TAG)
            }
            ?.getFirstElementByTagNameNS(NCX_NAMESPACE, TEXT_TAG)?.textContent
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, TEXT_TAG)
            }
        val source = element.getFirstElementByTagNameNS(NCX_NAMESPACE, CONTENT_TAG)
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, CONTENT_TAG)
            }
            ?.getAttribute(SRC_ATTR)
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, SRC_ATTR)
            }
        navItems.add(NavigationItemModel(id, label, source))
        createNavigationSubItemModel(element.childNodes).forEach { navItems.add(it) }


        return navItems
    }

    override fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel> {
        val navSubItems = mutableListOf<NavigationItemModel>()
        childrenNodes?.forEach {
            if (it.isNavPoint()) {
                createNavigationItemModel(it).let { navigationItem ->
                    navigationItem.forEach { navItem -> navSubItems.add(navItem) }
                }
            }
        }
        return navSubItems
    }

    override fun Node.isNavPoint() = (this as? Element)?.tagName == NAV_POINT_TAG

    private companion object {
        private const val TABLE_OF_CONTENTS_TAG = "table of contents"
        private const val NAV_MAP_TAG = "navMap"
        private const val NAV_POINT_TAG = "navPoint"
        private const val NAV_LABEL_TAG = "navLabel"
        private const val CONTENT_TAG = "content"
        private const val TEXT_TAG = "text"
        private const val SRC_ATTR = "src"
        private const val ID_ATTR = "id"
        private const val HREF_ATTR = "href"
    }
}