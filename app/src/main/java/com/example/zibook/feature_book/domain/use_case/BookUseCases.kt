package com.example.zibook.feature_book.domain.use_case

data class BookUseCases(
    val getBooks: GetBooks,
    val deleteBook: DeleteBook,
    val addBook: AddBook,
    val getBookById: GetBookById,
    val getBookByPath: GetBookByPath,
    val getTocs: GetTocs,
    val deleteToc: DeleteToc,
    val getToc: GetToc,
    val addToc: AddToc,
    val addSpineItem: AddSpineItem,
    val getSpine: GetSpine,
    val getSpines: GetSpines,
    val deleteSpine: DeleteSpine
)
