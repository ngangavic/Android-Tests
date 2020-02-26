package com.ngangavic.test.contextmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngangavic.test.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams

class ContextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add->{
                showContextMenuDialogFragment()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun showContextMenuDialogFragment() {
        val close = MenuObject().apply { setResourceValue(R.drawable.ic_close) }
        val send = MenuObject("Send message").apply { setResourceValue(R.drawable.ic_message) }

        val menuObjects = mutableListOf<MenuObject>().apply {
            add(close)
            add(send)
        }

//        val menuParams = MenuParams(
//                actionBarSize = resources.getDimension(R.dimen.menu_text_size).toInt(),
//                menuObjects = getMenuObjects(),
//                isClosableOutside = false
//                // set other settings to meet your needs
//        )

        val menuParams = MenuParams(
                actionBarSize = resources.getDimension(R.dimen.menu_item_padding).toInt(),
                menuObjects = menuObjects,
                isClosableOutside = false,
                gravity = MenuGravity.END
        )

        val contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams)
    }
}
