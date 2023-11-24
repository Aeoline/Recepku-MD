package com.aubrey.recepku.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.aubrey.recepku.R

class EmailCustomVIew: AppCompatEditText {
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
    private fun validEmail(email: String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun init(){
        doOnTextChanged { text, _,_,_ ->
            if (!text.isNullOrBlank()){
                error = if (validEmail(text.toString())){
                    null
                }else{
                    resources.getString(R.string.alertEmail)
                }
            }
        }
    }
}