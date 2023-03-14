package com.example.zibook.feature_book.presentation.reader

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.TextNode
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(ReaderState())
    val state : State<ReaderState> = _state


    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if (bookId != -1) {
                val chapterUrl = savedStateHandle.get<String>("chapterUrl")
                val chapterParts = mutableListOf<SpineItem>()
                viewModelScope.launch {
                    if (chapterUrl != null) {
                        var nextChapterUrl: String?
                        val currentChapterId = bookUseCases.getToc(chapterUrl)!!.tocId!!
                        bookUseCases.getToc(currentChapterId + 1)?.location.let { nextChapterUrl = it }
                        if (nextChapterUrl != null) {
                            var i = 1
                            chapterParts.add(bookUseCases.getSpine(chapterUrl)!!)
                            while (
                                    bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.location != nextChapterUrl &&
                                    bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.bookId == bookId
                                    ) {
                                chapterParts.add(bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!)
                                i++
                            }
                            val output = mutableListOf<Output>()
                            chapterParts.forEach {  spineItem ->
                                if (File(spineItem.location!!).exists()) {
                                    output.add(EpubXMLFileParser(File(spineItem.location).readBytes()).parse())
                                }
                            }
                            val body = mutableListOf<String>()
                            output.forEach {
                                it.body.forEach { string ->
                                    body.add(string)
                                }
                            }
                            val finalOutput = Output(
                                title = output[0].title,
                                body = body.toList()
                            )
                            _state.value = _state.value.copy(
                                data = finalOutput,
                                currentChapterId = currentChapterId,
                                book = bookUseCases.getBookById(bookId)
                            )
                        }

                    }
                }


            }
        }
    }

    fun onEvent(event: ReaderEvent) {
        when(event) {
            is ReaderEvent.NextChapter -> {
                viewModelScope.launch {
                    val id = state.value.currentChapterId!!
                    val bookId = state.value.book!!.id!!
                    var nextChapterUrl: String?
                    val chapterParts = mutableListOf<SpineItem>()
                    val query = bookUseCases.getToc(id.plus(1))
                    if (query != null && query.bookId == bookId) {
                        val chapterUrl = query.location
                        bookUseCases.getToc(query.tocId!! + 1)?.location.let { nextChapterUrl = it }
                        if (nextChapterUrl != null) {
                            var i = 1
                            chapterParts.add(chapterUrl?.let { bookUseCases.getSpine(it) }!!)
                            while (
                                bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.location != nextChapterUrl &&
                                bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.bookId == bookId
                            ) {
                                chapterParts.add(
                                    bookUseCases.getSpine(
                                        chapterParts[0].spineId!!.plus(
                                            i
                                        )
                                    )!!
                                )
                                i++
                            }
                            val output = mutableListOf<Output>()
                            chapterParts.forEach {  spineItem ->
                                if (File(spineItem.location!!).exists()) {
                                    output.add(EpubXMLFileParser(File(spineItem.location).readBytes()).parse())
                                }
                            }
                            val body = mutableListOf<String>()
                            output.forEach {
                                it.body.forEach { string ->
                                    body.add(string)
                                }
                            }
                            val finalOutput = Output(
                                title = output[0].title,
                                body = body.toList()
                            )
                            _state.value = _state.value.copy(
                                currentChapterId = id + 1,
                                data = finalOutput
                            )
                        }
                    }
                }
            }
            is ReaderEvent.PreviousChapter -> {
                viewModelScope.launch {
                    val id = state.value.currentChapterId!!
                    val bookId = state.value.book!!.id!!
                    var nextChapterUrl: String?
                    val chapterParts = mutableListOf<SpineItem>()
                    val query = bookUseCases.getToc(id.minus(1))
                    if (query != null && query.bookId == bookId) {
                        val chapterUrl = query.location
                        bookUseCases.getToc(query.tocId!! + 1)?.location.let { nextChapterUrl = it }
                        if (nextChapterUrl != null) {
                            var i = 1
                            chapterParts.add(chapterUrl?.let { bookUseCases.getSpine(it) }!!)
                            while (
                                bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.location != nextChapterUrl &&
                                bookUseCases.getSpine(chapterParts[0].spineId!!.plus(i))!!.bookId == bookId
                            ) {
                                chapterParts.add(
                                    bookUseCases.getSpine(
                                        chapterParts[0].spineId!!.plus(
                                            i
                                        )
                                    )!!
                                )
                                i++
                            }
                            val output = mutableListOf<Output>()
                            chapterParts.forEach {  spineItem ->
                                if (File(spineItem.location!!).exists()) {
                                    output.add(EpubXMLFileParser(File(spineItem.location).readBytes()).parse())
                                }
                            }
                            val body = mutableListOf<String>()
                            output.forEach {
                                it.body.forEach { string ->
                                    body.add(string)
                                }
                            }
                            val finalOutput = Output(
                                title = output[0].title,
                                body = body.toList()
                            )
                            _state.value = _state.value.copy(
                                currentChapterId = id + 1,
                                data = finalOutput
                            )
                        }
                    }
                }
            }
        }
    }

    private class EpubXMLFileParser(
        val data: ByteArray
    ) {
        fun parse(): Output {
            val body = Jsoup.parse(data.inputStream(), "UTF-8", "").body()
            val title = body.selectFirst("h1, h2, h3, h4, h5, h6")?.text()
            body.selectFirst("h1, h2, h3, h4, h5, h6")?.remove()
            return Output(
                title = title,
                body = getNodeStructuredText(body)
            )
        }
        // Rewrites the image node to xml for the next stage.

        private fun getPTraverse(node: org.jsoup.nodes.Node): List<String> {
            fun innerTraverse(node: org.jsoup.nodes.Node): List<String> {
                val pList = mutableListOf<String>()
                node.childNodes().forEach { child ->
                    when {
                        child.nodeName() == "br" -> pList.add("\n")
                        child.nodeName() == "img" -> pList.add("//image=${child.attr("src")}")
                        child is TextNode -> pList.add(child.text().trim())
                        else -> innerTraverse(child).forEach { string -> pList.add(string) }
                    }
                }
                return pList
            }
            val paragraph = innerTraverse(node).toMutableList()
            if (paragraph.isNotEmpty()) paragraph.add("\n\n")
            return paragraph
        }
        private fun getNodeTextTraverse(node: org.jsoup.nodes.Node): List<String> {
            val pList = mutableListOf<String>()
            val children = node.childNodes()
            if (children.isEmpty())
                return emptyList()
            children.forEach { child ->
                when {
                    child.nodeName() == "p" -> getPTraverse(child).forEach { string -> pList.add(string) }
                    child.nodeName() == "br" -> pList.add("\n")
                    child.nodeName() == "hr" -> pList.add("\n\n")
                    child.nodeName() == "img" -> pList.add("//image=${child.attr("src")}")
                    child is TextNode -> {
                        val text = child.text().trim()
                        if (text.isEmpty()) pList.add("") else pList.add(text + "\n\n")
                    }
                    else -> getNodeTextTraverse(child).forEach { string -> pList.add(string) }
                }
            }
            return pList
        }
        private fun getNodeStructuredText(node: org.jsoup.nodes.Node): List<String> {
            val pList = mutableListOf<String>()
            val children = node.childNodes()
            if (children.isEmpty())
                return emptyList()
            children.forEach { child ->
                when {
                    child.nodeName() == "p" -> getPTraverse(child).forEach { string -> pList.add(string) }
                    child.nodeName() == "br" -> pList.add("\n")
                    child.nodeName() == "hr" -> pList.add("\n\n")
                    child.nodeName() == "img" -> pList.add("//image=${child.attr("src")}")
                    child is TextNode -> pList.add(child.text().trim())
                    else -> getNodeTextTraverse(child).forEach { string -> pList.add(string) }
                }
            }
            return pList
        }
    }

}