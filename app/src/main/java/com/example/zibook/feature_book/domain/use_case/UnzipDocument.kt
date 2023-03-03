package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.repository.DocumentRepository
import java.util.zip.ZipFile


class UnzipDocument(private val repository: DocumentRepository) {

    suspend operator fun invoke(zipFile: ZipFile,path: String): Boolean {
        return repository.unzipDocument(zipFile, path)
    }
}