package ru.skillbranch.devintensive.extensions


fun String.truncate(numSymbols: Int=16) =
    if (this.trim().length > numSymbols)
        this.trim().dropLast(this.trim().length - numSymbols).trim() + "..."
    else this.trim()


fun String.trimDoubleWhitespace() = this.replace(" {2,}".toRegex(), " ")

fun String.stripHtml():String {
    if(this == "" || this == " ")
        return ""

    return this
        .replace("<[^>]+>".toRegex(), "")
        .replace("&[a-z;#\\d]+;".toRegex(), "")
        .trimDoubleWhitespace()
}