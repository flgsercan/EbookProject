package com.example.zibook.feature_book.domain.use_case

data class DocumentUseCases(
    val getDocuments: GetDocuments,
    val getDocumentByPath: GetDocumentByPath,
    val unzipDocument: UnzipDocument,
    val getTocByPath: GetTocByPath
)