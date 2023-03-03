package com.example.zibook

internal fun <T> T.orValidationError(action: (() -> Unit)?): T {
    if (this == null) action?.invoke()
    return this
}