package com.johnmagdalinos.android.shopandcook2.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.fragments.MainFragment
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentCallback {
    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        mainFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, Constants
                    .MAIN_ACTIVITY_FRAGMENT_STATE) as MainFragment
        } else {
            MainFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_main, mainFragment)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        supportFragmentManager.putFragment(outState, Constants.MAIN_ACTIVITY_FRAGMENT_STATE, mainFragment)
        super.onSaveInstanceState(outState)
    }

    override fun onMainFragmentCallback(title: String) {
        var intent: Intent = Intent(this, DetailActivity::class.java)

        intent.putExtra(Constants.DETAIL_ACTIVITY_KEY, when(title) {
            getString(R.string.main_shopping_list) -> Constants.SHOPPING_LIST_KEY
            getString(R.string.main_meal_planner) -> Constants.MEAL_PLANNER_KEY
            getString(R.string.main_recipes) -> Constants.RECIPE_LIST_KEY
            getString(R.string.main_meals) -> Constants.MEAL_LIST_KEY
            getString(R.string.main_recipe_ideas) -> Constants.RECIPE_IDEAS_KEY
            getString(R.string.main_sign_out) -> Constants.SIGN_OUT_KEY
            else -> throw IllegalArgumentException("Wrong intent extra")
        })
        startActivity(intent)
    }
}

