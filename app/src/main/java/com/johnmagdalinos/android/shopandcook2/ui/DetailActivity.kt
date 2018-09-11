package com.johnmagdalinos.android.shopandcook2.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.ui.fragments.RecipeListFragment
import com.johnmagdalinos.android.shopandcook2.ui.fragments.ShoppingListFragment
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class DetailActivity : AppCompatActivity() {
    private lateinit var currentFragment: String
    private var shoppingListFragment: ShoppingListFragment? = null
    private var recipeListFragment: RecipeListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState != null) {
            this.currentFragment = savedInstanceState.getString(Constants.DETAIL_ACTIVITY_CURRENT_FRAGMENT)
            when(currentFragment) {
                Constants.SHOPPING_LIST_KEY -> {
                    shoppingListFragment = supportFragmentManager.getFragment(savedInstanceState,
                            Constants.DETAIL_ACTIVITY_FRAGMENT_STATE) as ShoppingListFragment
                }
                Constants.RECIPE_LIST_KEY -> {
                    recipeListFragment = supportFragmentManager.getFragment(savedInstanceState,
                            Constants.DETAIL_ACTIVITY_FRAGMENT_STATE) as RecipeListFragment
                }
            }
        }

        startFragment()
    }

    /** Replaces the correct fragment based on the intent. Also saves the fragment key */
    private fun startFragment() {
        when(intent?.getStringExtra(Constants.DETAIL_ACTIVITY_KEY)) {
            Constants.SHOPPING_LIST_KEY -> {
                currentFragment = Constants.SHOPPING_LIST_KEY

                if(shoppingListFragment == null) shoppingListFragment = ShoppingListFragment.newInstance()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.cl_detail, shoppingListFragment)
                        .addToBackStack(null)
                        .commit()
            }
            Constants.RECIPE_LIST_KEY -> {
                currentFragment = Constants.RECIPE_LIST_KEY

                if(recipeListFragment == null) recipeListFragment = RecipeListFragment.newInstance()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.cl_detail, recipeListFragment)
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    /** Save the current fragment key as well as the fragment itself */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(Constants.DETAIL_ACTIVITY_CURRENT_FRAGMENT, currentFragment)
        when(currentFragment) {
            Constants.SHOPPING_LIST_KEY -> supportFragmentManager.putFragment(outState, Constants
                    .DETAIL_ACTIVITY_FRAGMENT_STATE, shoppingListFragment)
            Constants.RECIPE_LIST_KEY -> supportFragmentManager.putFragment(outState, Constants
                    .DETAIL_ACTIVITY_FRAGMENT_STATE, recipeListFragment)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        // Respond to the action bar's Up/Home button
        supportFinishAfterTransition()
//        val upIntent: Intent? = NavUtils.getParentActivityIntent(this)
//
//        when {
//            upIntent == null -> throw IllegalStateException("No Parent Activity Intent")
//
//            NavUtils.shouldUpRecreateTask(this, upIntent) -> {
//                // This activity is NOT part of this app's task, so create a new task
//                // when navigating up, with a synthesized back stack.
//                TaskStackBuilder.create(this)
//                        // Add all of this activity's parents to the back stack
//                        .addNextIntentWithParentStack(upIntent)
//                        // Navigate up to the closest parent
//                        .startActivities()
//            }
//            else -> {
//                // This activity is part of this app's task, so simply
//                // navigate up to the logical parent activity.
//                NavUtils.navigateUpTo(this, upIntent)
//            }
//        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {

                supportFinishAfterTransition()
//                // Respond to the action bar's Up/Home button
//                val upIntent: Intent? = NavUtils.getParentActivityIntent(this)
//
//                when {
//                    upIntent == null -> throw IllegalStateException("No Parent Activity Intent")
//                    NavUtils.shouldUpRecreateTask(this, upIntent) -> {
//                        // This activity is NOT part of this app's task, so create a new task
//                        // when navigating up, with a synthesized back stack.
//                        TaskStackBuilder.create(this)
//                                // Add all of this activity's parents to the back stack
//                                .addNextIntentWithParentStack(upIntent)
//                                // Navigate up to the closest parent
//                                .startActivities()
//                    }
//
//                    else -> {
//                        // This activity is part of this app's task, so simply
//                        // navigate up to the logical parent activity.
//                        NavUtils.navigateUpTo(this, upIntent)
//                    }
//                }
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
