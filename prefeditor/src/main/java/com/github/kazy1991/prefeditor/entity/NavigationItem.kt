package com.github.kazy1991.prefeditor.entity


class NavigationItem {

    val name: String

    var annotatedName: String? = null

    constructor(name: String) {
        this.name = name
    }

    constructor(name: String, annotatedName: String) {
        this.name = name
        this.annotatedName = annotatedName
    }

    fun normalizedName(): String {
        return annotatedName ?: name
    }
}
