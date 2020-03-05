package com.br.appmodal.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.br.appmodal.R

class EditTextWithClear : AppCompatEditText {
    var mClearButtonImage: Drawable? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() { // Initialize Drawable member variable.
        mClearButtonImage = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_clear_opaque_24dp, null
        )
        setOnTouchListener(OnTouchListener { v, event ->
            if (compoundDrawablesRelative[2] != null) {
                val clearButtonStart: Float
                val clearButtonEnd: Float
                var isClearButtonClicked = false
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = mClearButtonImage!!.getIntrinsicWidth() + paddingStart.toFloat()
                    if (event.x < clearButtonEnd) {
                        isClearButtonClicked = true
                    }
                } else {
                    clearButtonStart = (width - paddingEnd
                            - mClearButtonImage!!.intrinsicWidth).toFloat()
                    if (event.x > clearButtonStart) {
                        isClearButtonClicked = true
                    }
                }

                if (isClearButtonClicked) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_black_24dp, null
                        )
                        showClearButton()
                    }
                    if (event.action == MotionEvent.ACTION_UP) {
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_opaque_24dp, null
                        )
                        text!!.clear()
                        hideClearButton()
                        return@OnTouchListener true
                    }
                } else {
                    return@OnTouchListener false
                }
            }
            false
        })
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int, before: Int, count: Int
            ) {
                showClearButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    /**
     * Shows the clear (X) button.
     */
    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,  // Start of text.
            null,  // Top of text.
            mClearButtonImage,  // End of text.
            null
        ) // Below text.
    }

    /**
     * Hides the clear button.
     */
    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,  // Start of text.
            null,  // Top of text.
            null,  // End of text.
            null
        ) // Below text.
    }
}