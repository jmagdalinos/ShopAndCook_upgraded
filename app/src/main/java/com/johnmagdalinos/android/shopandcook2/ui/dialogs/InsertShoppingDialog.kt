package com.johnmagdalinos.android.shopandcook2.ui.dialogs

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.adapters.ColorAdapter
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.INSERT_DIALOG_CODE
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_COLOR
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_MEASURE
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_NAME
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_NOTES
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_POSITION
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.SHOPPING_ENTRY_QUANTITY
import com.johnmagdalinos.android.shopandcook2.utils.Conversions
import kotlinx.android.synthetic.main.dialog_insert.view.*

class InsertShoppingDialog: DialogFragment() {

    companion object {
        var color: Int = 0
        fun newInstance(position: Int, name: String?, measure: Int, quantity: Float, color:
        Int, notes: String) : InsertShoppingDialog {
            val fragment: InsertShoppingDialog = InsertShoppingDialog()
            val args: Bundle = Bundle()
            args.putInt(SHOPPING_ENTRY_POSITION, position)
            if (!position.equals(-1)) {
                args.putString(SHOPPING_ENTRY_NAME, name)
                args.putInt(SHOPPING_ENTRY_MEASURE, measure)
                args.putFloat(SHOPPING_ENTRY_QUANTITY, quantity)
                args.putInt(SHOPPING_ENTRY_COLOR, color)
                args.putString(SHOPPING_ENTRY_NOTES, notes)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Get the custom layout for the dialog
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_insert, null)
        val spinner: Spinner = view.findViewById(R.id.sp_insert_shopping_measure)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_insert_shopping_color)

        val position: Int = arguments!!.getInt(SHOPPING_ENTRY_POSITION)
        if (savedInstanceState != null) {
            color = savedInstanceState.getInt("test")
        } else {
            color = arguments!!.getInt(SHOPPING_ENTRY_COLOR)
        }

        // Setup and populate the spinner and RecyclerView
        setupSpinner(spinner, recyclerView)

        if (!position.equals(-1)) {
            val name: String = arguments!!.getString(SHOPPING_ENTRY_NAME)
            val measure: Int = arguments!!.getInt(SHOPPING_ENTRY_MEASURE)
            val quantity: Float = arguments!!.getFloat(SHOPPING_ENTRY_QUANTITY)
            val notes: String  = arguments!!.getString(SHOPPING_ENTRY_NOTES)

            // Set the values on the views
            view.et_insert_shopping_name.setText(name)
            view.et_insert_shopping_notes.setText(notes)
            if (!quantity.equals(0.0f)) {
                view.et_insert_shopping_quantity.setText(Conversions.convertQuantityToString(quantity))
            }

            if (!measure.equals(-1)) {
                spinner.setSelection(measure)
            }
        }

        val dialog: AlertDialog = AlertDialog.Builder(activity)
                .setView(view)
                .create()

        view.imb_pos.setOnClickListener {
            // Get and validate the values from the EditTexts
            val newName: String? = view.et_insert_shopping_name.text.toString()
            val newMeasure: Int = spinner.selectedItemPosition
            val newQuantityFloat: Float? = view.et_insert_shopping_quantity.text.toString()
                    .toFloatOrNull()
            val newQuantity: Float? = if (newQuantityFloat == null || newQuantityFloat.compareTo
                    (0) == -1) 0.0f else newQuantityFloat
            val newNotes: String = view.et_insert_shopping_notes.text.toString()

            // Send result to the target fragment in order for it to delete the data
            val intent: Intent = Intent()
            intent.putExtra(SHOPPING_ENTRY_POSITION, position)
            intent.putExtra(SHOPPING_ENTRY_NAME, newName)
            intent.putExtra(SHOPPING_ENTRY_MEASURE, newMeasure)
            intent.putExtra(SHOPPING_ENTRY_QUANTITY, newQuantity)
            intent.putExtra(SHOPPING_ENTRY_COLOR, color)
            intent.putExtra(SHOPPING_ENTRY_NOTES, newNotes)

            targetFragment?.onActivityResult(INSERT_DIALOG_CODE, RESULT_OK, intent)
            if (newName != null) {
                dialog.dismiss()
            } else {
                // There should be a valid name
                Toast.makeText(context!!, R.string.add_entry_name, Toast.LENGTH_SHORT).show()
            }
        }
        view.imb_neg.setOnClickListener { dialog.dismiss() }
        return dialog
    }

    /** Setup the spinner with the list of measures */
    private fun setupSpinner(spinner: Spinner, recyclerView: RecyclerView) {
        // Setup the spinner
        ArrayAdapter.createFromResource(activity,
                R.array.measure_spinner_singular,
                android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }

        // Setup the GridView
        recyclerView.layoutManager = GridLayoutManager(activity!!, 6)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ColorAdapter(activity!!, color) {
            newColor: Int -> color = newColor
            recyclerView.requestFocus()
        }

        // TODO: ontouch selector expands to entire view...
        // TODO: ItemAnimator
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("test", color)
        super.onSaveInstanceState(outState)
    }
}