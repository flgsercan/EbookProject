package com.example.zibook

import com.example.zibook.feature_book.domain.model.EpubTableOfContentsModel
import com.example.zibook.feature_book.domain.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.util.zip.ZipEntry

internal class Epub3TableOfContentsParser : EpubTableOfContentsParser() {

    private var validationListeners: ValidationListeners? = null

    override fun parse(
        tocDocument: Document?,
        validationListeners: ValidationListeners?,
        zipFile: Map<String, Pair<ZipEntry, ByteArray>>
    ): EpubTableOfContentsModel {

        this.validationListeners = validationListeners
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        val tocNav = tocDocument?.getElementsByTagName(NAV_TAG)
            .orValidationError { validationListeners?.onTableOfContentsMissing() }
            ?.firstWithAttributeNS(EpubConstants.ND_NAMESPACE, TYPE_ATTR, TOC_ATTR_VALUE)
            .orValidationError {
                validationListeners?.onAttributeMissing(TOC_ATTR_VALUE, TABLE_OF_CONTENTS_TAG)
            } as Element

        tocNav.getFirstElementByTag(OL_TAG)
            .orValidationError {
                validationListeners?.onAttributeMissing(OL_TAG, TABLE_OF_CONTENTS_TAG)
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

    override fun createNavigationItemModel(node: Node): List<NavigationItemModel> {
        val navItems = mutableListOf<NavigationItemModel>()
        val ref = (node as Element).getFirstElementByTag(A_TAG)
        val label = ref?.textContent
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(LABEL_FIELD_NAME, TABLE_OF_CONTENTS_TAG)
            }
        val source = ref?.getAttribute(HREF_ATTR)
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(HREF_ATTR, TABLE_OF_CONTENTS_TAG)
            }
        navItems.add(NavigationItemModel(null, label, source))
        createNavigationSubItemModel(node.getFirstElementByTag(OL_TAG)?.childNodes).forEach { navItems.add(it) }

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

    override fun Node.isNavPoint() =
        (this as? Element)?.tagName == NAV_POINT_TAG

    private companion object {
        private const val NAV_POINT_TAG = "li"
        private const val OL_TAG = "ol"
        private const val A_TAG = "a"
        private const val NAV_TAG = "nav"
        private const val LABEL_FIELD_NAME = "label"
        private const val TYPE_ATTR = "type"
        private const val TOC_ATTR_VALUE = "toc"
        private const val HREF_ATTR = "href"
        private const val TABLE_OF_CONTENTS_TAG = "table of contents"
    }
}