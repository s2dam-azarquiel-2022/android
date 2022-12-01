package net.azarquiel.friendroom.model

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.viewModel.FriendViewModel
import net.azarquiel.friendroom.viewModel.GroupViewModel
import kotlin.random.Random

class AddFriendBtnHandler(
    private val context: Context,
    private val friendViewModel: FriendViewModel,
    private val groupID: Int,
) : View.OnClickListener {
    override fun onClick(view: View?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.new_friend_from_group_alert, null)
        AlertDialog.Builder(context)
            .setTitle("Nuevo amigo")
            .setView(dialogView)
            .setPositiveButton("Añadir", FriendAddHandler(dialogView))
            .setNegativeButton("Cancelar") { _, _ -> /* do nothing */ }
            .show()
    }

    inner class FriendAddHandler(private val view: View) : DialogInterface.OnClickListener {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.getText(): String {
            return view.findViewById<EditText>(this).text.toString()
        }

        override fun onClick(p0: DialogInterface?, p1: Int) {
            friendViewModel.add(Friend(
                name = R.id.newFriendName.getText(),
                email = R.id.newFriendEmail.getText(),
                groupID = groupID,
            ))
        }
    }
}
class AddGroupBtnHandler(
    private val context: Context,
    private val groupViewModel: GroupViewModel,
) : View.OnClickListener {
    override fun onClick(view: View?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.new_group_alert, null)
        AlertDialog.Builder(context)
            .setTitle("Nuevo grupo")
            .setView(dialogView)
            .setPositiveButton("Añadir", GroupAddHandler(dialogView))
            .setNegativeButton("Cancelar") { _, _ -> /* do nothing */ }
            .show()
    }

    inner class GroupAddHandler(private val view: View) : DialogInterface.OnClickListener {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.getText(): String {
            return view.findViewById<EditText>(this).text.toString()
        }

        override fun onClick(p0: DialogInterface?, p1: Int) {
            groupViewModel.add( Group(
                name = R.id.newGroupName.getText(),
                email = R.id.newGroupEmail.getText(),
                color = Random(System.currentTimeMillis()).let { Color.rgb(
                    it.nextInt(),
                    it.nextInt(),
                    it.nextInt())
                }
            ))
        }
    }
}

abstract class ProductSwipeHandler(
    from: Int,
    direction: Int
) : ItemTouchHelper.SimpleCallback(from, direction) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    abstract override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
}
