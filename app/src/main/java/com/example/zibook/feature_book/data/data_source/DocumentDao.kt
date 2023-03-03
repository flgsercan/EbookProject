package com.example.zibook.feature_book.data.data_source


import com.example.zibook.feature_book.domain.model.EpubBook
import java.util.zip.ZipFile

interface DocumentDao {
    suspend fun getDocuments(): List<EpubBook>
    suspend fun getDocumentByPath(path : String): EpubBook
    suspend fun unzipDocument(zipFile: ZipFile, path: String) : Boolean
}