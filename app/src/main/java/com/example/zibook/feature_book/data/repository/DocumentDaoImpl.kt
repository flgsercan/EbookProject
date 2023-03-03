package com.example.zibook.feature_book.data.repository

import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.data.data_source.DocumentDao
import com.example.zibook.EpubParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.inject.Inject

class DocumentDaoImpl @Inject constructor(): DocumentDao {
    override suspend fun getDocuments(): List<EpubBook> {
        return withContext(Dispatchers.IO) {
            val files = File("/storage/emulated/0/Books/books/").listFiles()
            val filePaths = arrayOfNulls<String>(files?.size ?: 0)
            val epubParser = EpubParser()
            val listEpub = mutableListOf<EpubBook>()
            files?.mapIndexed {index, item ->
                filePaths[index] = item?.absolutePath
            }
            filePaths.forEach {
                val book = epubParser.parse(FileInputStream(it?.let { it1 -> File(it1) }), it)
                listEpub.add(book)
            }
            listEpub.toList()
        }
    }

    override suspend fun getDocumentByPath(path: String): EpubBook {
        return withContext(Dispatchers.IO) {
            val epubParser = EpubParser()
            epubParser.parse(FileInputStream(File(path)), path)
        }
    }

    override suspend fun unzipDocument(zipFile: ZipFile,path: String): Boolean {

        return withContext(Dispatchers.IO) {
            val result = mutableListOf<ZipEntry>()
            File(path).mkdir()

            zipFile.entries().asSequence().forEach { entry ->
                result.add(entry)
                val destinationFile = File(path, entry.name)
                destinationFile.parentFile?.mkdirs()

                if (!entry.isDirectory) {
                    val inputStream = BufferedInputStream(zipFile.getInputStream(entry))
                    val outputStream = BufferedOutputStream(FileOutputStream(destinationFile))

                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
            result.isNotEmpty()
        }
    }
}