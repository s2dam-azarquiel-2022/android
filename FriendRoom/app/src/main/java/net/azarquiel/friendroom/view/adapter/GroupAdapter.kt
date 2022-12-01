package net.azarquiel.friendroom.view.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.model.Group
import net.azarquiel.friendroom.model.ProductSwipeHandler
import net.azarquiel.friendroom.view.FriendActivity
import net.azarquiel.friendroom.view.MainActivity
import net.azarquiel.friendroom.viewModel.GroupViewModel

class GroupAdapter(
    private val context: MainActivity,
    private val thisView: RecyclerView,
    private val itemLayout: Int,
    private val groupViewModel: GroupViewModel,
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var data: List<Group> = emptyList()
    private val groupClickHandler = GroupClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        groupViewModel.getAll().observe(context) { groups ->
            groups.let { this.setData(it) }
        }
        ItemTouchHelper(GroupSwipeRightHandler()).attachToRecyclerView(thisView)
        ItemTouchHelper(GroupSwipeLeftHandler()).attachToRecyclerView(thisView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Group>) {
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

        fun bind(item: Group) {
            (itemView as CardView).setCardBackgroundColor(item.color)
            R.id.groupName.setText(item.name)
            R.id.groupEmail.setText(item.email)

            itemView.tag = item
            itemView.setOnClickListener(groupClickHandler)
        }
    }

    inner class GroupClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Group).let { group ->
                Intent(context, FriendActivity::class.java).let {
                    it.putExtra("group", group)
                    context.startActivity(it)
                }
            }
        }
    }

    inner class GroupSwipeRightHandler : ProductSwipeHandler(
        ItemTouchHelper.LEFT,
        ItemTouchHelper.RIGHT
    ) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val group = data[viewHolder.adapterPosition]
            groupViewModel.remove(group.id)
            Snackbar.make(
                thisView,
                group.name,
                Snackbar.LENGTH_LONG
            ).setAction("Undo") {
                groupViewModel.add(group)
            }.show()
        }
    }

    inner class GroupSwipeLeftHandler : ProductSwipeHandler(
        ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT
    ) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun View.setText(id: Int, text: String) {
            this.findViewById<EditText>(id).setText(text)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val group = data[pos]
            val dialogView = LayoutInflater.from(context).inflate(R.layout.new_group_alert, null)
            dialogView.setText(R.id.newGroupName, group.name)
            dialogView.setText(R.id.newGroupEmail, group.email)
            AlertDialog.Builder(context)
                .setTitle("Editar grupo")
                .setView(dialogView)
                .setPositiveButton(
                    "Guardar",
                    GroupEditHandler(dialogView, group)
                )
                .setNegativeButton(
                    "Cancelar"
                ) { _, _ -> this@GroupAdapter.notifyItemChanged(pos) }
                .show()
        }

        inner class GroupEditHandler(
            private val view: View,
            private val group: Group,
        ): DialogInterface.OnClickListener {
            @Suppress("NOTHING_TO_INLINE")
            private inline fun Int.getText(): String {
                return view.findViewById<EditText>(this).text.toString()
            }

            override fun onClick(p0: DialogInterface?, p1: Int) {
                groupViewModel.update(Group(
                    group.id,
                    (R.id.newGroupName).getText(),
                    (R.id.newGroupEmail).getText(),
                    group.color
                ))

                Snackbar.make(
                    thisView,
                    group.name,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    groupViewModel.update(group)
                }.show()
            }
        }
    }
}