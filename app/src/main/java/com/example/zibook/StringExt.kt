package com.example.zibook

internal fun String.orNullIfEmpty() = if (this.isBlank()) null else this