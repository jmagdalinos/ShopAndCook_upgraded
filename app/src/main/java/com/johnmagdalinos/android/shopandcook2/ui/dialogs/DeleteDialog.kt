package com.johnmagdalinos.android.shopandcook2.ui.dialogs

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.utils.Constants
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.DELETE_DIALOG_ARGS
import com.johnmagdalinos.android.shopandcook2.utils.Constants.Companion.DELETE_DIALOG_CODE
import kotlinx.android.synthetic.main.dialog_delete.view.*


class DeleteDialog : DialogFragment() {
    companion object {
        fun newInstance(mode: String) : DeleteDialog {
            val fragment: DeleteDialog = DeleteDialog()
            val args: Bundle = Bundle()
            args.putString(DELETE_DIALOG_ARGS, mode)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message: String = getMessage()

        // Get the custom layout for the dialog
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_delete, null)
        view.tv_dialog_delete_title.text = context!!.resources.getString(R.string.dialog_delete_are_you_sure)
        view.tv_dialog_delete_message.text = message

        val dialog: AlertDialog = AlertDialog.Builder(activity)
                .setView(view)
                .create()

        // Set the background to transparent so the round corners are visible
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.imb_pos.setOnClickListener {
            // Send result to the target fragment in order for it to delete the data
            targetFragment?.onActivityResult(DELETE_DIALOG_CODE,RESULT_OK,null)
            dialog.dismiss()}
        view.imb_neg.setOnClickListener { dialog.dismiss() }

        // Set the animation for the dialog
        dialog.window.setWindowAnimations(R.style.dialog_delete_animation)

        return dialog
    }

    /** Returns the message to show in the dialog based on which activity shows it */
    private fun getMessage(): String {
        return when (arguments?.getString(Constants.DELETE_DIALOG_ARGS)) {
            Constants.SHOPPING_LIST_KEY -> context!!.resources.getString(R.string
                    .dialog_clear_shopping_list)
            Constants.RECIPE_LIST_KEY -> context!!.resources.getString(R.string
                    .dialog_clear_recipes)
            Constants.MEAL_LIST_KEY -> context!!.resources.getString(R.string.dialog_clear_meals)
            else -> throw IllegalArgumentException("Fragment not supported")
        }
    }
}