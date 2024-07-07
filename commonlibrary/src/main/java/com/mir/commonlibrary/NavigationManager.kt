package com.mir.commonlibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
Created by Shitab Mir on 8/6/24.
shitabmir@gmail.com
 **/
object NavigationManager {
    /**
     * Navigate to the specified activity.
     *
     * @param targetActivity The activity class to navigate to.
     * @param finishCurrentActivity Whether to finish the current activity.
     */
    fun navigateTo(
        context: Context,
        targetActivity: Class<out Activity>,
        finishCurrentActivity: Boolean = false
    ) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
        if (finishCurrentActivity && context is Activity) {
            context.finish()
        }
    }

    /**
     * Navigate to the specified activity with additional data.
     *
     * @param targetActivity The activity class to navigate to.
     * @param extras The data to pass to the target activity.
     * @param finishCurrentActivity Whether to finish the current activity.
     */
    fun navigateToWithExtras(
        context: Context,
        targetActivity: Class<out Activity>,
        extras: Map<String, Any>,
        finishCurrentActivity: Boolean = false
    ) {
        val intent = Intent(context, targetActivity)
        for ((key, value) in extras) {
            when (value) {
                is String -> intent.putExtra(key, value)
                is Int -> intent.putExtra(key, value)
                is Boolean -> intent.putExtra(key, value)
                is Float -> intent.putExtra(key, value)
                is Double -> intent.putExtra(key, value)
                is Long -> intent.putExtra(key, value)
                else -> throw IllegalArgumentException("Unsupported extra type")
            }
        }
        context.startActivity(intent)
        if (finishCurrentActivity && context is Activity) {
            context.finish()
        }
    }

    /**
     * Navigate to the specified activity with additional data using Bundle.
     *
     * @param targetActivity The activity class to navigate to.
     * @param bundle The data to pass to the target activity.
     * @param finishCurrentActivity Whether to finish the current activity.
     */
    fun navigateToWithBundle(
        context: Context,
        targetActivity: Class<out Activity>,
        bundle: Bundle,
        finishCurrentActivity: Boolean = false
    ) {
        val intent = Intent(context, targetActivity).apply {
            putExtras(bundle)
        }
        context.startActivity(intent)
        if (finishCurrentActivity && context is Activity) {
            context.finish()
        }
    }

    /**
     * Navigate to the specified activity and clear the back stack.
     *
     * @param targetActivity The activity class to navigate to.
     */
    fun navigateToAndClearBackStack(context: Context, targetActivity: Class<out Activity>) {
        val intent = Intent(context, targetActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    /**
     * Navigate to the specified activity and clear the top of the back stack.
     *
     * @param targetActivity The activity class to navigate to.
     */
    fun navigateToAndClearTop(context: Context, targetActivity: Class<out Activity>) {
        val intent = Intent(context, targetActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }

    /**Navigate to the specified activity to back
     * @param targetActivity The activity class to navigate to. */

    fun navigateGoBack(context: Context, targetActivity: Class<out Activity>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    /**
     * Navigate to the specified activity and keep the back stack.
     *
     * @param targetActivity The activity class to navigate to.
     */
    fun navigateToAndKeepBackStack(context: Context, targetActivity: Class<out Activity>) {
        val intent = Intent(context, targetActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(intent)
    }

    /**
     * Navigate to the specified activity and remove previous activity from the back stack.
     *
     * @param targetActivity The activity class to navigate to.
     */
    fun navigateToAndRemovePrevious(context: Context, targetActivity: Class<out Activity>) {
        val intent = Intent(context, targetActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}