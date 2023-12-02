package com.aubrey.recepku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.DashboardBinding
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

class Dashboard : AppCompatActivity() {

    private lateinit var binding: DashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://www.herofincorp.com/public/admin_assets/upload/blog/64b91a06ab1c8_food%20business%20ideas.webp"))
        imageList.add(SlideModel("https://cdn.themistakenman.com/wp-content/uploads/2022/08/fast-food-business.webp"))
        imageList.add(SlideModel("https://images.bizbuysell.com/shared/listings/204/2045821/1d720199-35d9-4667-876a-03ef268f58f0-W336.jpg"))

        val imageSlider = findViewById<ImageSlider>(R.id.slider)
        imageSlider.setImageList(imageList)
    }
}