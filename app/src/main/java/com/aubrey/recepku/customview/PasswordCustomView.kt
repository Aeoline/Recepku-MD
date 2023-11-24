package com.aubrey.recepku.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.aubrey.recepku.R

class PasswordCustomView: AppCompatEditText{
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }
    private fun init(){
        doOnTextChanged { text, _,_,_ ->
            if (!text.isNullOrBlank()){
                error = if (text.length <= 5){
                    resources.getString(R.string.alertPassword)
                }else{
                    null
                }
            }
        }
    }
}