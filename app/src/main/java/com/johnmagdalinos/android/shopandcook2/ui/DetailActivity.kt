package com.johnmagdalinos.android.shopandcook2.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.utils.Constants

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Toast.makeText(this, intent?.getStringExtra(Constants.DETAIL_ACTIVITY_KEY), Toast
                .LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                val upIntent: Intent? = NavUtils.getParentActivityIntent(this)

                when {
                    upIntent == null -> throw IllegalStateException("No Parent Activity Intent")
                    NavUtils.shouldUpRecreateTask(this, upIntent) -> {
                        // This activity is NOT part of this app's task, so create a new task
                        // when navigating up, with a synthesized back stack.
                        TaskStackBuilder.create(this)
                                // Add all of this activity's parents to the back stack
                                .addNextIntentWithParentStack(upIntent)
                                // Navigate up to the closest parent
                                .startActivities()
                    }
                    else -> {
                        // This activity is part of this app's task, so simply
                        // navigate up to the logical parent activity.
                        NavUtils.navigateUpTo(this, upIntent)
                    }
                }
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
