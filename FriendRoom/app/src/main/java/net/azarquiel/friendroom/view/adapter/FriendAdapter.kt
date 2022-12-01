package net.azarquiel.friendroom.view.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.model.Friend
import net.azarquiel.friendroom.model.Group
import net.azarquiel.friendroom.model.ProductSwipeHandler
import net.azarquiel.friendroom.view.FriendActivity
import net.azarquiel.friendroom.viewModel.FriendViewModel

class FriendAdapter(
    private val context: FriendActivity,
    private val thisView: RecyclerView,
    private val itemLayout: Int,
    private val friendViewModel: FriendViewModel,
    group: Group,
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {
    private var data: List<Friend> = emptyList()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        friendViewModel.getByGroupId(group.id).observe(context) { groups ->
            groups.let { this.setData(it) }
        }
        ItemTouchHelper(FriendSwipeRightHandler()).attachToRecyclerView(thisView)
        ItemTouchHelper(FriendSwipeLeftHandler()).attachToRecyclerView(thisView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Friend>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(itemLayout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: String) {
            itemView.findViewById<TextView>(this).text = text
        }

        fun bind(item: Friend) {
            R.id.friendName.setText(item.name)
            R.id.friendEmail.setText(item.email)

            itemView.tag = item
        }
    }

    inner class FriendSwipeRightHandler : ProductSwipeHandler(
        ItemTouchHelper.LEFT,
        ItemTouchHelper.RIGHT
    ) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val friend = data[viewHolder.adapterPosition]
            friendViewModel.remove(friend.id)
            Snackbar.make(
                thisView,
                friend.name,
                Snackbar.LENGTH_LONG
            ).setAction("Undo") {
                friendViewModel.add(friend)
            }.show()
        }
    }

    inner class FriendSwipeLeftHandler : ProductSwipeHandler(
        ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT
    ) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun View.setText(id: Int, text: String) {
            this.findViewById<EditText>(id).setText(text)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val friend = data[pos]
            val dialogView = LayoutInflater.from(context).inflate(R.layout.new_friend_from_group_alert, null)
            dialogView.setText(R.id.newFriendName, friend.name)
            dialogView.setText(R.id.newFriendEmail, friend.email)
            AlertDialog.Builder(context)
                .setTitle("Editar amigo")
                .setView(dialogView)
                .setPositiveButton(
                    "Guardar",
                    FriendEditHandler(dialogView, friend)
                )
                .setNegativeButton(
                    "Cancelar"
                ) { _, _ -> this@FriendAdapter.notifyItemChanged(pos) }
                .show()
        }

        inner class FriendEditHandler(
            private val view: View,
            private val friend: Friend,
        ): DialogInterface.OnClickListener {
            @Suppress("NOTHING_TO_INLINE")
            private inline fun Int.getText(): String {
                return view.findViewById<EditText>(this).text.toString()
            }

            override fun onClick(p0: DialogInterface?, p1: Int) {
                friendViewModel.update(Friend(
                    friend.id,
                    (R.id.newFriendName).getText(),
                    (R.id.newFriendEmail).getText(),
                    friend.groupID
                ))

                Snackbar.make(
                    thisView,
                    friend.name,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    friendViewModel.update(friend)
                }.show()
            }
        }
    }
}