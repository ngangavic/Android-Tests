package com.ngangavic.test.conextmenutest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngangavic.test.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.toolbar.*

class TestActivity : AppCompatActivity() {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        initToolbar()
        initMenuFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
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

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

//        toolbar.apply {
//            setNavigationIcon(R.drawable.ic_arrow_back)
//            setNavigationOnClickListener { onBackPressed() }
//        }

        tvToolbarTitle.text = "Samantha"
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
                actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
//                actionBarSize = 56,
                menuObjects = getMenuObjects(),
                isClosableOutside = false,
                gravity = MenuGravity.START
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                Toast.makeText(
                        this@TestActivity,
                        "Clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
            menuItemLongClickListener = { view, position ->
                Toast.makeText(
                        this@TestActivity,
                        "Long clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply { setResourceValue(R.drawable.ic_close) }
        val send = MenuObject("Send message").apply { setResourceValue(R.drawable.ic_message) }
//        val like = MenuObject("Like profile").apply {
//            setBitmapValue(BitmapFactory.decodeResource(resources, R.drawable.icn_2))
//        }
//        val addFriend = MenuObject("Add to friends").apply {
//            drawable = BitmapDrawable(
//                    resources,
//                    BitmapFactory.decodeResource(resources, R.drawable.icn_3)
//            )
//        }
//        val addFavorite = MenuObject("Add to favorites").apply {
//            setResourceValue(R.drawable.icn_4)
//        }
//        val block = MenuObject("Block user").apply { setResourceValue(R.drawable.icn_5) }

        add(close)
        add(send)
//        add(like)
//        add(addFriend)
//        add(addFavorite)
//        add(block)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }
}
