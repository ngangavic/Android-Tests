package com.ngangavic.test.checkbox

class SelectableItem : Item() {
    private var isSelected = false

    fun SelectableItem(item: Item, isSelected: Boolean) {
//        super.(item.name, item.surname)
        super.name
        super.surname
        this.isSelected = isSelected
    }

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }
}