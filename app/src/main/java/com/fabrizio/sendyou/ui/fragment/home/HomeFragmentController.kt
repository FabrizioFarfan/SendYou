package com.fabrizio.sendyou.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyControllerAdapter
import com.fabrizio.sendyou.R
import com.fabrizio.sendyou.data.Attraction
import com.fabrizio.sendyou.databinding.FragmentHomeBinding
import com.fabrizio.sendyou.databinding.ViewHolderAttractionBinding
import com.fabrizio.sendyou.ui.MainActivity
import com.fabrizio.sendyou.ui.epoxy.LoadingEpoxyModel
import com.fabrizio.sendyou.ui.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso


class  HomeFragmentController(
    private val onClickedCallBack: (String) -> Unit
    ): EpoxyController() {
    var isLoading: Boolean = false
        set(value) {
            field= value
            if(field){
                requestModelBuild()
            }
        }
    var attractions = ArrayList<Attraction>()
        set(value){
            field= value
            isLoading= false
            requestModelBuild()
        }

    override fun buildModels() {
        if(isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }
        if(attractions.isEmpty()) {

            return
        }

        attractions.forEach { attraction->
            AttractionEpoxyModel(attraction, onClickedCallBack)
                .id(attraction.id)
                .addTo(this)
        }

    }

    data class AttractionEpoxyModel(
        val attraction: Attraction,
        val onClicked: (String) -> Unit
    ): ViewBindingKotlinModel<ViewHolderAttractionBinding>(R.layout.view_holder_attraction){

        override fun ViewHolderAttractionBinding.bind() {
            titleTextView.text= attraction.title
            if(attraction.image_urls.isNotEmpty()) {
                Picasso.get().load(attraction.image_urls[0]).into(headerEpoxyRecyclerView);
            } else  {
                // error handling
            }
            monthsToVisitTextView.text= attraction.months_to_visit

            root.setOnClickListener {
                onClicked(attraction.id)
            }
        }
    }


}