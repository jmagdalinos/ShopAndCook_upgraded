package com.johnmagdalinos.android.shopandcook2.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.adapters.RecipeListAdapter
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        // Setup the ActionBar
        activity?.title = activity?.getString(R.string.main_recipes)
        val toolbarImageView = activity?.findViewById<ImageView>(R.id.iv_toolbar)
        toolbarImageView?.setImageDrawable(context?.getDrawable(R.drawable.recipes))
        setHasOptionsMenu(true)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_recipes_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = RecipeListAdapter(context)

            if (savedInstanceState != null) {
                layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(Constants
                        .RECIPE_LIST_FRAGMENT_RECYCLER_STATE))
            }
        }


        return view
    }
    /** Save the current state of the RecyclerView */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constants.RECIPE_LIST_FRAGMENT_RECYCLER_STATE, recyclerView
                .layoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_shopping_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_shopping_delete_all -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}