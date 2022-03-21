package com.yeong.sample

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.yeong.edit_filter.EditorFilter
import java.lang.Math.max

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et = findViewById<EditText>(R.id.et)
        EditorFilter(object : EditorFilter.Filter {
            override fun requestFilter(afterString: CharSequence?): Boolean =
                (afterString?.length ?: 0) > 10

            override fun filter(afterString: CharSequence?, added: CharSequence?): CharSequence? {
                val afterStringLength = afterString?.length ?: 0
                val addedStringLength = added?.length ?: 0
                val gap = 10 - afterStringLength
                return if (gap <= 0) null else return added?.take(gap)
//                afterStringLength
//                added?.take(((afterString?.length ?: 0) - (added.length)).coerceAtMost(0))
            }

        }).attachToEditText(et)
    }
}