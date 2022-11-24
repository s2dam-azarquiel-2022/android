package net.azarquiel.friendroom.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.friendroom.model.Friend
import net.azarquiel.friendroom.model.FriendsRepository
import net.azarquiel.friendroom.model.Group

@OptIn(DelicateCoroutinesApi::class)
class GroupViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var repository = FriendsRepository(application)

    fun getAll(): LiveData<List<Group>> = repository.getAllGroups()
    fun getById(id: Int): LiveData<List<Group>> = repository.getGroupById(id)
    fun add(group: Group) = GlobalScope.launch { repository.addGroup(group) }
    fun remove(id: Int) = GlobalScope.launch { repository.removeGroup(id) }
    fun update(group: Group) = GlobalScope.launch { repository.updateGroup(group) }
}