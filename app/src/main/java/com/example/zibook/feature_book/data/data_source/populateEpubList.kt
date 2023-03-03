package com.example.zibook.feature_book.data.data_source

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.aregyan.compose.R
import com.example.zibook.EbookApp
import com.example.zibook.ImageScalingUtility
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.NavItem
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import com.example.zibook.feature_book.domain.use_case.DocumentUseCases
import java.io.File
import java.util.zip.ZipFile

suspend fun populateEpubList (documentUseCases: DocumentUseCases, bookUseCases: BookUseCases) {


    val bookList = documentUseCases.getDocuments().toMutableList()
    val zipList = mutableListOf<ZipFile>()

    val coverList = mutableListOf<Bitmap>()
    bookList.forEach() { epub ->
        epub.epubCoverImage?.byteArray.let {
            if (it != null) {

                val bitmap = decodeSampledBitmapFromResource(it, 0,100,100)
                File("/storage/emulated/0/Testappdir/"+
                        (epub.epubMetadataModel?.title ?: "notitle") +
                        "/", "cover.png").write(resizeCover(bitmap), Bitmap.CompressFormat.PNG,100)
            } else {
                val res = EbookApp.applicationContext().resources
                val bitmap = BitmapFactory.decodeResource( res, R.drawable.cover)
                File("/storage/emulated/0/Testappdir/"+
                        (epub.epubMetadataModel?.title ?: "notitle") +
                        "/", "cover.png").write(resizeCover(bitmap), Bitmap.CompressFormat.PNG,100)
            }

        }
        zipList.add(ZipFile(epub.path))

    }
    coverList.forEach() {
        /*
        context.openFileOutput( (bookList[i].epubMetadataModel?.title ?: "notitle") + "/cover.png", MODE_PRIVATE).use { stream ->
            if (!it.compress(Bitmap.CompressFormat.PNG,100, stream)) {
                throw IOException("Couldn't save bitmap!")
            }
        }
         */

    }
    var i = 0
    bookList.forEach { epub ->
        bookUseCases.addBook(Book(
            "/storage/emulated/0/Testappdir/" +  (epub.epubMetadataModel?.title ?: "notitle") + "/cover.png",
            epub.epubMetadataModel?.creators.toString(),
            epub.epubMetadataModel?.title ?: "notitle",
            epub.path ?: "nopath",
            epub.epubOpfFilePath ?: "nopath"
        ))
        documentUseCases.unzipDocument(zipList[i], "/storage/emulated/0/Testappdir/" +
                (epub.epubMetadataModel?.title ?: "notitle") + "/")

        val id = epub.path?.let { bookUseCases.getBookByPath(it)?.id }
        documentUseCases.getTocByPath(epub.path!!).forEach { model ->
            bookUseCases.addToc(
                NavItem(
                    id = model.id,
                    label = model.label,
                    location = model.location,
                    bookId = id
            ))
        }
        ++i
    }

}


fun decodeSampledBitmapFromResource(
    res: ByteArray,
    resId: Int,
    reqWidth: Int,
    reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(res, resId, res.size)

        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeByteArray(res, resId, res.size)
    }
}

 fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}
  fun resizeCover(cover : Bitmap) : Bitmap {

    return ImageScalingUtility.resizeBitmap(cover, 2*320,2*180)


}
 fun File.createFileAndDirs() = apply {
    parentFile?.mkdirs()
    createNewFile()
}

 fun File.write(
    bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, quality: Int = 80
) = apply {
    createFileAndDirs()
    outputStream().use {
        bitmap.compress(format, quality, it)
        it.flush()
        it.close()
    }
}