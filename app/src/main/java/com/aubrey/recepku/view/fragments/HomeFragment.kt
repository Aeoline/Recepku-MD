package com.aubrey.recepku.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aubrey.recepku.R
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel


class HomeFragment : Fragment() {
    private lateinit var imageSlider: ImageSlider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        imageSlider = view.findViewById(R.id.slider)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSlider()
    }

    private fun imageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://www.herofincorp.com/public/admin_assets/upload/blog/64b91a06ab1c8_food%20business%20ideas.webp"))
        imageList.add(SlideModel("https://cdn.themistakenman.com/wp-content/uploads/2022/08/fast-food-business.webp"))
        imageList.add(SlideModel("https://images.bizbuysell.com/shared/listings/204/2045821/1d720199-35d9-4667-876a-03ef268f58f0-W336.jpg"))

        imageSlider.setImageList(imageList)
    }
}