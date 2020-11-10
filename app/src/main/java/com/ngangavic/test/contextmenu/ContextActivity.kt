package com.ngangavic.test.contextmenu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.activity_context.*

class ContextActivity : AppCompatActivity() {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context)
        initToolbar()
        initMenuFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let {
            when (it.itemId) {
                R.id.action_add -> {
                    showContextMenuDialogFragment()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (::contextMenuDialogFragment.isInitialized && contextMenuDialogFragment.isAdded) {
            contextMenuDialogFragment.dismiss()
        } else {
            finish()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
                actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
                menuObjects = getMenuObjects(),
                isClosableOutside = false,
                gravity = MenuGravity.START
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                Toast.makeText(
                        this@ContextActivity,
                        "Clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
            menuItemLongClickListener = { view, position ->
                Toast.makeText(
                        this@ContextActivity,
                        "Long clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply { setResourceValue(R.drawable.ic_close) }
        val send = MenuObject("Send message").apply { setResourceValue(R.drawable.ic_message) }
        add(close)
        add(send)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }
}
