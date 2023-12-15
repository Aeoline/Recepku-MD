package com.aubrey.recepku.view.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.aubrey.recepku.R

class ResultActivity : AppCompatActivity() {

    val result : TextView = findViewById(R.id.tv_makanan)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }
}