package com.johnmagdalinos.android.shopandcook2.ui.fragments

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry
import com.johnmagdalinos.android.shopandcook2.ui.adapters.ShoppingListAdapter
import com.johnmagdalinos.android.shopandcook2.ui.adapters.SwipeToDeleteCallback
import com.johnmagdalinos.android.shopandcook2.ui.dialogs.DeleteDialog
import com.johnmagdalinos.android.shopandcook2.ui.dialogs.InsertShoppingDialog
import com.johnmagdalinos.android.shopandcook2.utils.Constants
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_COLOR
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_MEASURE
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_NAME
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_NOTES
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_POSITION
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_QUANTITY
import com.johnmagdalinos.android.shopandcook2.utils.InjectorUtils
import com.johnmagdalinos.android.shopandcook2.viewmodels.DetailViewModel
import com.johnmagdalinos.android.shopandcook2.viewmodels.DetailViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*


class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    /** Setup the ViewModel */
    private val viewModel: DetailViewModel by lazy {
        val factory: DetailViewModelFactory = InjectorUtils().provideDetailViewModelFactory(context!!)
        ViewModelProviders.of(this.activity!!, factory).get(DetailViewModel::class.java)
    }

    private lateinit var recyclerAdapter: ShoppingListAdapter

    // Observer used to monitor the insertion of new items in the adapter
    private lateinit var dataObserver: AdapterObserver

    companion object {
        // Var used to state whether the observer has been initialized
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

        // Get last sorting method and order from the preferences
        // Initiate the Observer
        initiateObserver(retrievePreferences(), savedInstanceState)
        setupRecyclerView(view)

        // Setup the FAB
        val slideAnimation: Animation = AnimationUtils.loadAnimation(activity!!,R.anim.fab_enter_anim)
        activity?.fab_shopping?.startAnimation(slideAnimation)
        activity?.fab_shopping?.setOnClickListener {
            val dialogFragment: InsertShoppingDialog = InsertShoppingDialog.newInstance(
                    -1, null, 0, 0.0f, 0, "")
            dialogFragment.setTargetFragment(this, Constants.INSERT_DIALOG_CODE)
            dialogFragment.show(activity!!.supportFragmentManager, null)
        }
        return view
    }

    /** Initiates the LiveData Observer */
    private fun initiateObserver(sortPair: Pair<String, Boolean>, savedInstanceState: Bundle?) {

        viewModel.init(sortPair)
        viewModel.getShoppingList()?.observe(this.activity!!, Observer<List<ShoppingEntry>> {
            shoppingList -> recyclerAdapter.submitList(shoppingList)
            recyclerView.layoutManager.onRestoreInstanceState(savedInstanceState?.getParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE))
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
    private fun setupRecyclerView(view: View) {
        // Setup the RecyclerView
        recyclerAdapter = ShoppingListAdapter(context!!) { position: Int, isChecked: Boolean,
                                                           isItemClicked: Boolean ->
            updateEntry(position, isChecked, isItemClicked)}

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_shopping_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = recyclerAdapter
            itemAnimator = DefaultItemAnimator()
        }

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                // Save the entry to be deleted in case the user wants to undo the delete action
                val entry = recyclerAdapter.list?.get(viewHolder!!.adapterPosition)
                createUndoSnackbar(entry)

                // Remove the entry from the database
                viewModel.deleteShoppingEntry(recyclerAdapter.list?.get(viewHolder!!
                        .adapterPosition)!!)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /** Save the originalPosition state of the RecyclerView */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(Constants.SHOPPING_LIST_FRAGMENT_RECYCLER_STATE, recyclerView
                .layoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    /** An item was pressed. Update the db */
    private fun updateEntry(position: Int, isChecked: Boolean, isItemClicked: Boolean) {
        if (isItemClicked) {
            // The item was clicked
            val shoppingEntry: ShoppingEntry = recyclerAdapter.getItemAtPosition(position)!!
            val dialogFragment: InsertShoppingDialog = InsertShoppingDialog.newInstance(
                    position,
                    shoppingEntry.name,
                    shoppingEntry.measure ?: -1,
                    shoppingEntry.quantity ?: 0.0f,
                    shoppingEntry.color ?: 0,
                    shoppingEntry.notes ?: "")
            dialogFragment.setTargetFragment(this, Constants.INSERT_DIALOG_CODE)
            dialogFragment.show(activity!!.supportFragmentManager, null)
        } else {
            // The item's checkbox was clicked
            val itemChanged = recyclerAdapter.getItemAtPosition(position)
            if (itemChanged != null) {
                itemChanged.checked = if (isChecked) 1 else 0
                viewModel.updateShoppingEntry(itemChanged)
            }
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

    /** Creates a Snackbar to allow undo action when deleting an entry */
    private fun createUndoSnackbar(entry: ShoppingEntry?) {
        if (entry == null) return

        val snackbar: Snackbar = Snackbar.make(activity!!.findViewById(R.id.cl_detail),
                context!!.getString(R.string.snackbar_delete_shopping_item),
                Snackbar.LENGTH_SHORT)
        snackbar.setAction(R.string.snackbar_undo) { viewModel.insertShoppingEntry(entry)}
        snackbar.setActionTextColor(ContextCompat.getColor(activity!!, R.color.snackBarTextColor))
        snackbar.show()
    }

    /** Called when the delete dialog closes */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((resultCode == RESULT_OK)) {
            when (requestCode) {
                Constants.DELETE_DIALOG_CODE ->
                    // The result comes from a delete dialog
                    viewModel.deleteAllShopping()

                Constants.INSERT_DIALOG_CODE -> {
                    // The result comes from an insert dialog
                    val position: Int = data?.getIntExtra(SHOPPING_ENTRY_POSITION, -1) ?: -1
                    val name: String = data?.getStringExtra(SHOPPING_ENTRY_NAME) ?: ""
                    val measure: Int? = data?.getIntExtra(SHOPPING_ENTRY_MEASURE, 0)
                    val quantity: Float? = data?.getFloatExtra(SHOPPING_ENTRY_QUANTITY, 0.0f)
                    val color: Int = data?.getIntExtra(SHOPPING_ENTRY_COLOR, 0) ?: 0
                    val notes: String? = data?.getStringExtra(SHOPPING_ENTRY_NOTES)

                    if (!position.equals(-1)) {
                        // This is an updated entry
                        val shoppingEntry: ShoppingEntry? = recyclerAdapter.getItemAtPosition(position)
                        if (shoppingEntry != null) {
                            val newEntry = ShoppingEntry(null,
                                    name,
                                    notes,
                                    measure,
                                    if(quantity == 0.0f) null else quantity,
                                    color,
                                    shoppingEntry.checked)
                            viewModel.deleteShoppingEntry(shoppingEntry)
                            viewModel.insertShoppingEntry(newEntry)
                        }
                    } else {
                        // This is a new entry
                        val shoppingEntry: ShoppingEntry = ShoppingEntry(null, name, notes,
                        measure, quantity, color, 0)
                        viewModel.insertShoppingEntry(shoppingEntry)
                    }
                }

            }
        }
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
                val dialogFragment = DeleteDialog.newInstance(Constants.SHOPPING_LIST_KEY)
                // Set the originalPosition fragment as the target fragment so the dialog can return a
                // result code if the user confirms the deletion
                dialogFragment.setTargetFragment(this, Constants.DELETE_DIALOG_CODE)
                dialogFragment.show(activity!!.supportFragmentManager, null)
                true
            }

            R.id.menu_sort_name -> {
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

                recyclerAdapter.submitList(null)

                viewModel.getShoppingList()?.removeObservers(this.activity!!)

                // Initiate the Observer
                initiateObserver(newOrderPair, null)

                true
            }

            R.id.menu_sort_color -> {
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

                recyclerAdapter.submitList(null)

                viewModel.getShoppingList()?.removeObservers(this.activity!!)

                // Initiate the Observer
                initiateObserver(newOrderPair, null)

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
            if (fromPosition == 0) recyclerView.scrollToPosition(0)
            if (recyclerAdapter.list != null && itemCount == 1) {
                recyclerView.scrollToPosition(fromPosition)
            }
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
        }
    }
}