package com.example.zibook.feature_book.data.repository

import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.data.data_source.DocumentDao
import com.example.zibook.feature_book.domain.repository.DocumentRepository
import java.util.zip.ZipFile

class DocumentRepositoryImpl(
    private val dao: DocumentDao
    ): DocumentRepository {
    override suspend fun getDocuments(): List<EpubBook> {
        return dao.getDocuments()
    }

    override suspend fun getDocumentByPath(path: String): EpubBook {
        return dao.getDocumentByPath(path)
    }

    override suspend fun unzipDocument(zipFile: ZipFile, path: String): Boolean {
        return dao.unzipDocument(zipFile, path)
    }
}