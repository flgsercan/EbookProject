package com.example.zibook.feature_book.presentation.reader

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import com.example.zibook.feature_book.domain.util.BookOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    private var getChapterJob: Job? = null

    init {
        savedStateHandle.get<Long>("bookId")?.let { bookId ->
            savedStateHandle.get<Long>("chapterId")?.let {chapterId ->
                if (bookId != -1L) {
                    if (chapterId != -1L) {
                        getChapter(bookId, chapterId)
                    }
                }
            }

        }
    }

    fun onEvent(event: ReaderEvent) {
        when(event) {
            is ReaderEvent.NextChapter -> {
                getChapter(state.value.book?.id!!, state.value.tocId!! + 1)
            }
            is ReaderEvent.PreviousChapter -> {
                getChapter(state.value.book?.id!!, state.value.tocId!! -1)
            }
        }
    }

    private fun getChapter(bookId: Long, tocId: Long) {
        getChapterJob?.cancel()
        getChapterJob = bookUseCases.getSpines(bookId, tocId).onEach {
            var body = mutableListOf<String>()
            val data: Output
            if (it.isNotEmpty()) {
                it.forEach {
                    if (File(it.location).exists()) {
                        val output = EpubXMLFileParser(File(it.location).readBytes()).parse()
                        body = body.plus(output.body) as MutableList<String>
                    }
                    else {
                        body.add("THERE IS A PROBLEM... ${it.location}")
                    }
                }
                    data = Output(
                    title = EpubXMLFileParser(File(it[0].location).readBytes()).parse().title,
                    body = body.toList()
                )
            } else {
                body.add("THIS SHIT IS EMPTY...")
                data = Output(
                    title = "Problem",
                    body = body.toList()
                )
            }

            _state.value = state.value.copy(
                data = data,
                book = bookUseCases.getBookById(bookId),
                tocId = tocId,
                location = it[0].location
            )
        }
            .launchIn(viewModelScope)
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