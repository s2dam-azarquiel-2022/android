package net.azarquiel.friendroom.model

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

class AddFriendMenuHandler(
    private val context: AppCompatActivity,
    private val friendViewModel: FriendViewModel,
    groupViewModel: GroupViewModel
) {
    private var groups: List<Group>? = null
    private var selectedGroup: Int? = null
    private var dialogView: View? = null

    init {
        groupViewModel.getAll().let {
            it.observe(context)  { data ->
                groups = data
                dialogView = LayoutInflater.from(context).inflate(R.layout.new_friend_alert, null)
                setupSpinner()
                setupAlert()
            }
        }
    }

    private fun setupSpinner() {
        dialogView!!.findViewById<Spinner>(R.id.newFriendsGroup).let {
            it.adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                groups!!.map { group -> group.name }
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            it.onItemSelectedListener = GroupSelectedHandler()
        }
    }

    private fun setupAlert() {
        AlertDialog.Builder(context)
            .setTitle("Nuevo amigo")
            .setView(dialogView)
            .setPositiveButton("Añadir", FriendAddHandler())
            .setNegativeButton("Cancelar") { _, _ -> /* do nothing */ }
            .show()
    }

    inner class GroupSelectedHandler : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            selectedGroup = pos
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    inner class FriendAddHandler : DialogInterface.OnClickListener {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.getText(): String {
            return dialogView!!.findViewById<EditText>(this).text.toString()
        }

        override fun onClick(p0: DialogInterface?, p1: Int) {
            selectedGroup?.let {
                friendViewModel.add(Friend(
                    name = R.id.newFriendName.getText(),
                    email = R.id.newFriendEmail.getText(),
                    groupID = groups!![it].id,
                ))
            } ?: run {
                Toast.makeText(context, "No has seleccionado un grupo", Toast.LENGTH_LONG).show()
            }
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
