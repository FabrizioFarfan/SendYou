package com.fabrizio.sendyou.ui.fragment.details

import com.airbnb.epoxy.EpoxyController
import com.fabrizio.sendyou.R
import com.fabrizio.sendyou.databinding.ModelHeaderImageBinding
import com.fabrizio.sendyou.ui.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class HeaderEpoxyController(
    val imageUrls: List<String>
): EpoxyController() {

    override fun buildModels() {
        imageUrls.forEachIndexed { index, url->
            HeaderImageEpoxyModel(url).id(index).addTo(this)
        }
    }

    inner class HeaderImageEpoxyModel(
        val imageUrl: String
    ): ViewBindingKotlinModel<ModelHeaderImageBinding>(R.layout.model_header_image) {

        override fun ModelHeaderImageBinding.bind() {
            Picasso.get().load(imageUrl).into(imageView)
        }
    }
}