package com.ngangavic.test.checkbox

class Item {
    var name: String? = null
    var surname: String? = null

    constructor(name: String?, surname: String?) {
        this.name = name
        this.surname = surname
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        val compare: Item = other as Item
        if (compare.name == this.name)
            return true

        return false
    }

}