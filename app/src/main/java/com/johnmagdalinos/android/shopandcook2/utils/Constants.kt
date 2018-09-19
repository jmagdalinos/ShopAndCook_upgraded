package com.johnmagdalinos.android.shopandcook2.utils

class Constants {
    companion object {
        // Keys for intents
        const val DETAIL_ACTIVITY_KEY: String = "detail_activity"
        const val SHOPPING_LIST_KEY: String = "shopping_list"
        const val MEAL_PLANNER_KEY: String = "meal_planner"
        const val RECIPE_LIST_KEY: String = "recipe_list"
        const val MEAL_LIST_KEY: String = "meal_list"
        const val RECIPE_IDEAS_KEY: String = "recipe_ideas"
        const val SIGN_OUT_KEY: String = "sign_out"

        // Keys for saving state
        const val MAIN_ACTIVITY_FRAGMENT_STATE: String = "main_activity_fragment"
        const val DETAIL_ACTIVITY_FRAGMENT_STATE: String = "detail_activity_fragment"
        const val DETAIL_ACTIVITY_CURRENT_FRAGMENT: String = "detail_activity_current_fragment"
        const val MAIN_FRAGMENT_RECYCLER_STATE = "main_recycler_state"
        const val SHOPPING_LIST_FRAGMENT_RECYCLER_STATE = "shopping_list_recycler_state"
        const val INSERT_SHOPPING_DIALOG_CURRENT_COLOR = "insert_shopping_dialog_color"
        const val RECIPE_LIST_FRAGMENT_RECYCLER_STATE = "recipe_list_recycler_state"

        // Shared Preferences
        const val PREFS_SHOPPING_LIST_ORDER: String = "shopping_list_order"
        const val PREFS_SHOPPING_LIST_METHOD: String = "shopping_list_method"
        const val PREFS_METHOD_BY_NAME: String = "name"
        const val PREFS_METHOD_BY_COLOR: String = "color"

        // Keys for Fragment bundles
        const val DELETE_DIALOG_ARGS: String = "delete_dialog_args"

        // Request codes
        const val INSERT_DIALOG_CODE: Int = 101
        const val DELETE_DIALOG_CODE: Int = 102

        // Keys for inserting a shopping entry
        const val SHOPPING_ENTRY_POSITION = "position"
        const val SHOPPING_ENTRY_NAME = "name"
        const val SHOPPING_ENTRY_NOTES = "notes"
        const val SHOPPING_ENTRY_MEASURE = "measure"
        const val SHOPPING_ENTRY_QUANTITY = "quantity"
        const val SHOPPING_ENTRY_COLOR = "color"
    }
}