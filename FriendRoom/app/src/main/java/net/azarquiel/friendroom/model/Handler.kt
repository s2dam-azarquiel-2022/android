package net.azarquiel.friendroom.model

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.viewModel.FriendViewModel

class AddFriendBtnHandler(
    private val context: Context,
    private val friendViewModel: FriendViewModel,
) : View.OnClickListener {
    override fun onClick(view: View?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.new_friend_alert, null)
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
            ))
        }
    }
}