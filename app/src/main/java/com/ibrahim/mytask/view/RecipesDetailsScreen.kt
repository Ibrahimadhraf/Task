package com.ibrahim.mytask.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ibrahim.mytask.R
import com.ibrahim.mytask.databinding.ReipiesDetailsBinding
import com.ibrahim.mytask.model.RecipePalette
import com.ibrahim.mytask.model.RecipesModel
import com.ibrahim.mytask.viewModel.RecipesDetailsViewModel


class RecipesDetailsScreen :Fragment(){
       private lateinit var dataBinding:ReipiesDetailsBinding
    private  lateinit var detailsViewModel: RecipesDetailsViewModel
    private var recipeUid = 0
    private var currentRecipe:RecipesModel?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).supportActionBar!!.show()

        // Inflate the layout for this fragment
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.reipies_details, container, false)
        return  dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel=ViewModelProviders.of(this).get(RecipesDetailsViewModel::class.java)
         arguments?.let {
             recipeUid=RecipesDetailsScreenArgs.fromBundle(it).recipesUid
         }
         detailsViewModel.fetch(recipeUid)
        observeRecipesDetailsViewModel()
    }
    private fun observeRecipesDetailsViewModel() {
        detailsViewModel.recipesDetails.observe(viewLifecycleOwner, Observer { recipe ->
            recipe?.let {
                dataBinding.recipe=it
                it.image?.let {url->
                    setBackgroundColor(url)

                }
            }

        })

    }
    private fun setBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->

                            val intColor = palette?.vibrantSwatch?.rgb ?: 0
                            val imagePalette =  RecipePalette(intColor)
                            dataBinding.palette = imagePalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}