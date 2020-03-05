package com.br.appmodal.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.br.appmodal.R
import kotlinx.android.synthetic.main.view_filter.view.*



class FilterView(context:Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_filter, this)
    }
}