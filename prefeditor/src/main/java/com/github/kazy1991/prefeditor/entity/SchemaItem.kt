package com.github.kazy1991.prefeditor.entity


class SchemaItem {

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SchemaItem

        if (name != other.name) return false
        if (annotatedName != other.annotatedName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (annotatedName?.hashCode() ?: 0)
        return result
    }

}
