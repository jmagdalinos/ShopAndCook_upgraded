package com.johnmagdalinos.android.shopandcook2.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.adapters.ShoppingListAdapter
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_shopping, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_shopping_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingListAdapter(context)

            if (savedInstanceState != null) {
                layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE))
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE, recyclerView
                .layoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }
}