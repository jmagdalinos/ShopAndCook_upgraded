package com.johnmagdalinos.android.shopandcook2.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.adapters.ShoppingListAdapter
import com.johnmagdalinos.android.shopandcook2.ui.viewmodels.DetailViewModel
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: DetailViewModel
    private var recyclerAdapter: ShoppingListAdapter? = null

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)

        activity?.title = activity?.getString(R.string.main_shopping_list)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_shopping_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter

            if (savedInstanceState != null) {
                layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE))
            }
        }

        viewModel = ViewModelProviders.of(activity!!).get(DetailViewModel::class.java)

        // TODO: For testing only
        viewModel.addTestEntries()
        viewModel.getShoppingList().observe(activity!!, Observer { shoppingList ->
            recyclerAdapter?.swapList(shoppingList) })

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE, recyclerView
                .layoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }
}