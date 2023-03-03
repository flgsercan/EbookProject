package com.example.zibook

import com.example.zibook.feature_book.domain.model.EbupSpineReferenceModel
import com.example.zibook.feature_book.domain.model.EpubSpineModel
import com.example.zibook.EpubConstants.OPF_NAMESPACE
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubSpineParser {

    fun parse(
        opfDocument: Document,
        validationListeners: ValidationListeners?
    ): EpubSpineModel {

        val spineElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, SPINE_TAG)
            .orValidationError { validationListeners?.onSpineMissing() }
        val spineModel = spineElement?.getNodeListByTagNameNS(OPF_NAMESPACE, ITEM_REF_TAG)
            .orValidationError { validationListeners?.onAttributeMissing(SPINE_TAG, ITEM_REF_TAG) }
            ?.map {
                val element = it as Element
                val idReference = element.getAttribute(ID_REF_ATTR)
                val isLinear = element.getAttribute(IS_LINEAR_ATTR) == IS_LINEAR_POSITIVE_VALUE
                EbupSpineReferenceModel(idReference, isLinear)
            }
        return EpubSpineModel(spineModel)
    }

    private companion object {
        private const val SPINE_TAG = "spine"
        private const val ITEM_REF_TAG = "itemref"
        private const val ID_REF_ATTR = "idref"
        private const val IS_LINEAR_ATTR = "linear"
        private const val IS_LINEAR_POSITIVE_VALUE = "yes"
    }
}