package com.example.zibook.feature_book.domain.use_case

data class BookUseCases(
    val getBooks: GetBooks,
    val deleteBook: DeleteBook,
    val addBook: AddBook,
    val getBookById: GetBookById,
    val getBookByPath: GetBookByPath,
    val getToc: GetToc,
    val deleteToc: DeleteToc,
    val getTocById: GetTocById,
    val addToc: AddToc
)
