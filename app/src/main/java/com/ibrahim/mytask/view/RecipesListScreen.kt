package com.ibrahim.mytask.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.mytask.R
import com.ibrahim.mytask.model.RecipesModel
import com.ibrahim.mytask.utis.SORT_Type
import com.ibrahim.mytask.viewModel.RecipesListViewModel
import kotlinx.android.synthetic.main.recipes_list_screen.*


class RecipesListScreen : Fragment() {

    private lateinit var recipesListViewModel: RecipesListViewModel
    private var recipesList: List<RecipesModel>? = null
    private var pref: SharedPreferences? = null
    private var sortedBy: String? = null
    private val recipesAdapter = RecipesAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipes_list_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        pref?.apply {
            sortedBy = getString(SORT_Type, "")
            Log.d("Respose", " shared=${sortedBy}")
        }
        errorText.visibility = View.GONE
        rvrecipiesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipesAdapter

        }
        recipesListViewModel = ViewModelProviders.of(this).get(RecipesListViewModel::class.java)
        if (sortedBy == "sortByFats") {
            retrieveSortByFats()
        } else if (sortedBy == "sortByCalories") {
            retrieveSortByCalories()
        } else {
            retrieveAllData()
        }
        ivSearch.setOnClickListener {
            observeByName()
        }
        resetUi.setOnClickListener {
            retrieveAllData()
        }
        btSortByFat.setOnClickListener {
            retrieveSortByFats()
        }
        btSortByCalories.setOnClickListener {
            retrieveSortByCalories()
        }
    }

    private fun observeViewModel() {
        recipesListViewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                Log.d("Respose", " aftergetis=${recipes}")
                loadigView.visibility = View.GONE
                errorText.visibility = View.GONE
                recipesList = recipes
                recipesAdapter.updateDogList(recipes)

            }
        })

        recipesListViewModel.recipesLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { it ->
                Log.d("Respose", " aftergetis=${isError}")
                errorText.visibility = if (it) View.VISIBLE else View.GONE


            }
        })
        recipesListViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                Log.d("Respose", " aftergetis=${isLoading}")

                loadigView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    errorText.visibility = View.GONE


                }
            }

        })


    }

    private fun observeByName() {
        if (editText.text.toString().isNotEmpty()) {
            val name = editText.text.toString().trim()
            Log.d("Respose", " aftergetis=${name}")
            rvrecipiesList.visibility = View.GONE
            loadigView.visibility = View.VISIBLE
            recipesAdapter.updateDogList(arrayListOf())
            recipesListViewModel.fetchByName(name)
            recipesListViewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
                loadigView.visibility = View.INVISIBLE
                if (recipes.isNotEmpty() && recipes != null) {
                    Log.d("Respose", " aftergetis=${recipes}")
                    recipesList = recipes
                    recipesAdapter.updateDogList(recipesList!!)

                    rvrecipiesList.visibility = View.VISIBLE
                } else if (recipes.isEmpty()) {
                    loadigView.visibility = View.INVISIBLE
                    errorText.visibility = View.VISIBLE
                }
            }
            )
        }
    }

    private fun retrieveAllData() {
        recipesListViewModel.invokeUi(requireContext())
        observeViewModel()
    }

    @SuppressLint("CommitPrefEdits")
    private fun retrieveSortByCalories() {
        rvrecipiesList.visibility = View.GONE
        loadigView.visibility = View.VISIBLE
        recipesAdapter.updateDogList(arrayListOf())
        recipesListViewModel.fetchByCalories()
        recipesListViewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            loadigView.visibility = View.INVISIBLE
            if (recipes.isNotEmpty() && recipes != null) {
                Log.d("Respose", " aftergetis=${recipes}")
                recipesList = recipes
                recipesAdapter.updateDogList(recipesList!!)
                rvrecipiesList.visibility = View.VISIBLE

                pref?.edit()?.clear()
                saveDate("sortByCalories")

            } else if (recipes.isEmpty()) {
                loadigView.visibility = View.INVISIBLE
                errorText.visibility = View.VISIBLE
            }
        }
        )
    }

    private fun retrieveSortByFats() {
        rvrecipiesList.visibility = View.GONE
        loadigView.visibility = View.VISIBLE
        recipesAdapter.updateDogList(arrayListOf())
        recipesListViewModel.fetchByFat()
        recipesListViewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            loadigView.visibility = View.INVISIBLE
            if (recipes.isNotEmpty() && recipes != null) {
                Log.d("Respose", " aftergetis=${recipes}")
                recipesList = recipes
                recipesAdapter.updateDogList(recipesList!!)
                rvrecipiesList.visibility = View.VISIBLE

                pref?.edit()?.clear()
                saveDate("sortByFats")
            } else if (recipes.isEmpty()) {
                loadigView.visibility = View.INVISIBLE
                errorText.visibility = View.VISIBLE
            }
        }
        )
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveDate(soertedBy: String) {
        val editor = pref?.edit()
        editor?.putString(SORT_Type, soertedBy)
            ?.apply()

    }

}