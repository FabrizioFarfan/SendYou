package com.fabrizio.sendyou.ui.fragment.details

import android.app.AlertDialog

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper

import com.fabrizio.sendyou.R

import com.fabrizio.sendyou.databinding.FragmentAttractionDetailBinding
import com.fabrizio.sendyou.ui.fragment.BaseFragment

import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class AttractionDetailFragment : BaseFragment() {


    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activityViewModel.selectedAttractionLiveData.observe(viewLifecycleOwner) { attraction ->
            binding.titleTextView.text = attraction.title
            binding.headerEpoxyRecyclerView.setControllerAndBuildModels(
                HeaderEpoxyController(
                    attraction.image_urls
                )
            )
            LinearSnapHelper().attachToRecyclerView(binding.headerEpoxyRecyclerView)
            binding.indicator.attachToRecyclerView(binding.headerEpoxyRecyclerView)


            var isGridMode: Boolean = binding.contentEpoxyRecyclerView.layoutManager is GridLayoutManager
            val contentEpoxyController = ContentEpoxyController(attraction)
            contentEpoxyController.isGridMode
            contentEpoxyController.onChangeLayoutCallback= {
                if (isGridMode){
                    binding.contentEpoxyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                }else{
                    binding.contentEpoxyRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                }

                isGridMode=!isGridMode
                contentEpoxyController.isGridMode= isGridMode
                contentEpoxyController.requestModelBuild()
            }

            binding.contentEpoxyRecyclerView.setControllerAndBuildModels(
                contentEpoxyController)

       //     binding.numberOfFactsTextView.setOnClickListener {
       //         val stringBuilder = StringBuilder("")
       //         attraction.facts.forEach {
       //             stringBuilder.append("*$it")
       //             stringBuilder.append("\n\n")
       //         }
       //         val message =
       //             stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("\n\n"))
       //         AlertDialog.Builder(requireContext(), R.style.MyDialog)
       //             .setTitle("${attraction.facts} facts")
       //             .setMessage(message)
       //             .setPositiveButton("Ok") { dialog, which ->
       //                 dialog.dismiss()
       //             }
       //             .setNegativeButton("No") { dialog, which ->
       //                 dialog.dismiss()
       //             }
       //             .show()
       //     }

        }

        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_attraction_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menuItemLocation -> {
                        val attraction = activityViewModel.selectedAttractionLiveData.value ?: return true
                        activityViewModel.locationSelectedLiveData.postValue(attraction)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}