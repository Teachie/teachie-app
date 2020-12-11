package id.teachly.ui.register.fragments.filldata

import id.teachly.data.Category

interface DialogInterestImpl {
    fun addItem(category: Category)
    fun removeItem(category: Category)
}