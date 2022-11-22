package net.azarquiel.friendroom.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.friendroom.model.Friend
import net.azarquiel.friendroom.model.FriendsRepository

@OptIn(DelicateCoroutinesApi::class)
class FriendViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var repository = FriendsRepository(application)

    fun getAll(): LiveData<List<Friend>> = repository.getAllFriends()
    fun add(friend: Friend) = GlobalScope.launch { repository.addFriend(friend) }
    fun remove(id: Int) = GlobalScope.launch { repository.removeFriend(id) }
    fun update(friend: Friend) = GlobalScope.launch { repository.updateFriend(friend) }
}