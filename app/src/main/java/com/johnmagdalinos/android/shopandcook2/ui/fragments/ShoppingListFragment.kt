package com.johnmagdalinos.android.shopandcook2.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.ImageView
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry
import com.johnmagdalinos.android.shopandcook2.ui.adapters.ShoppingListAdapter
import com.johnmagdalinos.android.shopandcook2.ui.adapters.SwipeToDeleteCallback
import com.johnmagdalinos.android.shopandcook2.utils.Constants
import com.johnmagdalinos.android.shopandcook2.utils.InjectorUtils
import com.johnmagdalinos.android.shopandcook2.viewmodels.DetailViewModel
import com.johnmagdalinos.android.shopandcook2.viewmodels.DetailViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*



class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: DetailViewModel
    private lateinit var recyclerAdapter: ShoppingListAdapter

    // Observer used to monitor the insertion of new items in the adapter
    private lateinit var dataObserver: AdapterObserver

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)

        // Setup the ActionBar
        activity?.title = activity?.getString(R.string.main_shopping_list)
        val toolbarImageView = activity?.findViewById<ImageView>(R.id.iv_toolbar)
        toolbarImageView?.setImageDrawable(context?.getDrawable(R.drawable.shopping_list))
        setHasOptionsMenu(true)

        // Setup the ViewModel
        val factory: DetailViewModelFactory = InjectorUtils().provideDetailViewModelFactory(context!!)

        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)

        setupRecyclerView(view, savedInstanceState)

        // Get last sorting method and order from the preferences
        // Initiate the Observer
        initiateObserver(retrievePreferences())

        // Setup the FAB
        activity?.fab_shopping?.setOnClickListener {
            viewModel.addTestEntries()}
        return view
    }

    /** Initiates the LiveData Observer */
    private fun initiateObserver(sortPair: Pair<String, Boolean>) {
        viewModel.init(sortPair)

        viewModel.getShoppingList()?.observe(this, Observer<List<ShoppingEntry>> {
            shoppingList -> recyclerAdapter.submitList(shoppingList)
        })

        // Save the sorting method and order from the preferences
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(prefs.edit()) {
            putString(Constants.PREFS_SHOPPING_LIST_METHOD, sortPair.first)
            putBoolean(Constants.PREFS_SHOPPING_LIST_ORDER, sortPair.second)
            apply()
        }
    }

    /** Setup the RecyclerView, Adapter and SwipeCallback */
    private fun setupRecyclerView(view: View, savedInstanceState: Bundle?) {
        // Setup the RecyclerView
        recyclerAdapter = ShoppingListAdapter(context!!) { position: Int, isChecked: Boolean ->
            updateEntry(position, isChecked)}

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_shopping_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
            itemAnimator = DefaultItemAnimator()

            if (savedInstanceState != null) {
                layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE))
            }
        }

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewModel.deleteShoppingEntry(recyclerAdapter.list?.get(viewHolder!!.adapterPosition)!!)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /** Save the current state of the RecyclerView */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE, recyclerView
                .layoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    /** An item was pressed. Update the db */
    private fun updateEntry(position: Int, isChecked: Boolean) {
        val itemChanged = recyclerAdapter.getItemAtPosition(position)
        if (itemChanged != null) {
            itemChanged.checked = if (isChecked) 1 else 0
            viewModel.updateShoppingEntry(itemChanged)
        }
    }

    /** Retrieves the last used sort method and order */
    private fun retrievePreferences(): Pair<String, Boolean> {
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        val sortMethod = prefs?.getString(Constants.PREFS_SHOPPING_LIST_METHOD, Constants
                .PREFS_METHOD_BY_NAME) ?: Constants.PREFS_METHOD_BY_NAME
        val sortOrder = prefs?.getBoolean(Constants.PREFS_SHOPPING_LIST_ORDER, true) ?: true

        return Pair(sortMethod, sortOrder)
    }

    /** Create the AdapterDataObserver to scroll the RecyclerView to the new item */
    override fun onStart() {
        dataObserver = AdapterObserver()
        recyclerAdapter.registerAdapterDataObserver(dataObserver)

        super.onStart()
    }

    /** Unregisters the AdapterDataObserver */
    override fun onPause() {
        recyclerAdapter.unregisterAdapterDataObserver(dataObserver)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_shopping_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_shopping_delete_all -> {
                viewModel.deleteAllShopping()
                true
            }

            R.id.menu_sort_name -> {
                viewModel.getShoppingList()?.removeObservers(this)

                // Retrieve the last sort method and order
                val orderPair: Pair<String, Boolean> = retrievePreferences()
                val newOrderPair: Pair<String, Boolean> = if (orderPair.first == Constants
                                .PREFS_METHOD_BY_NAME) {
                    // The method was name, reverse the order
                    Pair(Constants.PREFS_METHOD_BY_NAME, !(orderPair.second))
                } else {
                    // The method was color; Set it to by_name and sort ascending
                    Pair(Constants.PREFS_METHOD_BY_NAME, true)
                }

                // Initiate the Observer
                initiateObserver(newOrderPair)

                true
            }

            R.id.menu_sort_color -> {
                viewModel.getShoppingList()?.removeObservers(this)

                // Retrieve the last sort method and order
                val orderPair: Pair<String, Boolean> = retrievePreferences()
                val newOrderPair: Pair<String, Boolean> = if (orderPair.first == Constants
                                .PREFS_METHOD_BY_COLOR) {
                    // The method was color, reverse the order
                    Pair(Constants.PREFS_METHOD_BY_COLOR, !(orderPair.second))
                } else {
                    // The method was name; Set it to by_name and sort ascending
                    Pair(Constants.PREFS_METHOD_BY_COLOR, true)
                }

                // Initiate the Observer
                initiateObserver(newOrderPair)

                true
            }

            R.id.menu_insert_all -> {
                viewModel.addAllTestEntries()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    /**
     * Inner class used to observe changes to the adapter data and trigger a smooth scroll of the
     * RecyclerView
     */
    inner class AdapterObserver: RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (recyclerAdapter.list != null && itemCount == 1) {
                recyclerView.scrollToPosition(positionStart)
            }
            super.onItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (fromPosition ==0) recyclerView.scrollToPosition(0)
//            if (recyclerAdapter.list != null && itemCount == 1) {
//                recyclerView.scrollToPosition(fromPosition)
//            }
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
        }
    }
}