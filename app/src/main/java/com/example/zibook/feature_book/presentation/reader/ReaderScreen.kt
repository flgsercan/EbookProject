package com.example.zibook.feature_book.presentation.reader

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jsoup.Jsoup
import org.jsoup.nodes.TextNode
import java.io.File

@Composable
fun ReaderScreen(url: String) {

   val data = EpubXMLFileParser(File(url).readBytes()).parse()
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)
    ) {
        item {
            data.title?.let {
                Text(
                    text = it,
                    fontSize = 25.sp
                )
            }
            Text(
                text = data.body,
                fontSize = 20.sp
            )
        }
    }
}

private class EpubXMLFileParser(
    val data: ByteArray
) {
    data class Output(val title: String?, val body: String)
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

    private fun getPTraverse(node: org.jsoup.nodes.Node): String {
        fun innerTraverse(node: org.jsoup.nodes.Node): String =
            node.childNodes().joinToString("") { child ->
                when {
                    child.nodeName() == "br" -> "\n"
                    child.nodeName() == "img" -> "!!_!!?image=${child.attr("src")}"
                    child is TextNode -> child.text()
                    else -> innerTraverse(child)
                }
            }
        val paragraph = innerTraverse(node).trim()
        return if (paragraph.isEmpty()) "" else innerTraverse(node).trim() + "\n\n"
    }
    private fun getNodeTextTraverse(node: org.jsoup.nodes.Node): String {
        val children = node.childNodes()
        if (children.isEmpty())
            return ""
        return children.joinToString("") { child ->
            when {
                child.nodeName() == "p" -> getPTraverse(child)
                child.nodeName() == "br" -> "\n"
                child.nodeName() == "hr" -> "\n\n"
                child.nodeName() == "img" -> "!!_!!?image=${child.attr("src")}"
                child is TextNode -> {
                    val text = child.text().trim()
                    if (text.isEmpty()) "" else text + "\n\n"
                }
                else -> getNodeTextTraverse(child)
            }
        }
    }
    private fun getNodeStructuredText(node: org.jsoup.nodes.Node): String {
        val children = node.childNodes()
        if (children.isEmpty())
            return ""
        return children.joinToString("") { child ->
            when {
                child.nodeName() == "p" -> getPTraverse(child)
                child.nodeName() == "br" -> "\n"
                child.nodeName() == "hr" -> "\n\n"
                child.nodeName() == "img" -> "!!_!!?image=${child.attr("src")}"
                child is TextNode -> child.text().trim()
                else -> getNodeTextTraverse(child)
            }
        }
    }
}
