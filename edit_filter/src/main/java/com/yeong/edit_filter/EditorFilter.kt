package com.yeong.edit_filter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class EditorFilter(private val filter: Filter) {
    interface Filter {
        fun requestFilter(afterString: CharSequence?): Boolean
        fun filter(afterString: CharSequence?, added: CharSequence?): CharSequence?
    }

    private var editText: EditText? = null
    private var beforeText: String? = null
    private val filterWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            beforeText = p0.toString()
        }

        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            if (filter.requestFilter(p0)) {
                val addFilterText = filter.filter(beforeText, p0?.drop(start)?.take(count))
                if (addFilterText == null) {
                    editText?.setText(beforeText)
                    editText?.setSelection(start)
                    return
                }
                val beforeText = beforeText
                val text =
                    if (beforeText == null) {
                        addFilterText
                    } else {
                        val stringBuilder = StringBuilder()
                        stringBuilder.append(beforeText.take(start))
                        stringBuilder.append(addFilterText)
                        stringBuilder.append(beforeText.drop(start))
                    }
                editText?.setText(text)
                editText?.setSelection(start + addFilterText.length)
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }


    fun attachToEditText(editText: EditText) {
        this.editText = editText
        editText.addTextChangedListener(filterWatcher)
        val initString: CharSequence? = editText.text
        beforeText = initString.toString()
        if (filter.requestFilter(initString)) {
            val addFilterText = filter.filter(null, initString)
            editText.setText(addFilterText)
        }
    }
}