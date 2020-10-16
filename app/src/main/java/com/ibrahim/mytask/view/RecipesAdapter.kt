package com.ibrahim.mytask.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavAction
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.mytask.R
import com.ibrahim.mytask.databinding.RecipesItemViewBinding
import com.ibrahim.mytask.databinding.RecipesItemViewBindingImpl
import com.ibrahim.mytask.model.RecipesModel
import kotlinx.android.synthetic.main.recipes_item_view.view.*

class RecipesAdapter(var recipeList: ArrayList<RecipesModel>) :RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    fun updateDogList(newRecipesList: List<RecipesModel>) {
        recipeList.clear()
        recipeList.addAll(newRecipesList)
        notifyDataSetChanged()
    }
   class RecipesViewHolder(val view:RecipesItemViewBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val  layoutInflater= LayoutInflater.from(parent.context)
      //  val  view=layoutInflater.inflate(R.layout.recipes_item_view ,parent,false)
        val view=DataBindingUtil.inflate<RecipesItemViewBinding>(layoutInflater ,R.layout.recipes_item_view ,parent ,false)
        return  RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
            holder.view.recipe=recipeList[position]
        val uuid=recipeList[position].uuId
           holder.view.root.setOnClickListener {

               Log.d("Respose" , " Clicked")
               val action=RecipesListScreenDirections.actionRecipesDetailsScreen(uuid)
               Navigation.findNavController(it).navigate(action)
           }

    }

    override fun getItemCount(): Int {
        return recipeList.size;
    }


}