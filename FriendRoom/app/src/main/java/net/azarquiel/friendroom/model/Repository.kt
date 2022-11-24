package net.azarquiel.friendroom.model

import android.app.Application
import androidx.lifecycle.LiveData

class FriendsRepository(application: Application) {
    val friendDAO = FriendsDB.getDB(application).friendDAO()
    val groupDAO = FriendsDB.getDB(application).groupDAO()

    fun getAllFriends(): LiveData<List<Friend>> = friendDAO.getAll()
    fun getFriendById(id: Int): LiveData<List<Friend>> = friendDAO.getById(id)
    fun addFriend(friend: Friend) = friendDAO.add(friend)
    fun removeFriend(id: Int) = friendDAO.remove(id)
    fun updateFriend(friend: Friend) = friendDAO.update(friend)

    fun getAllGroups(): LiveData<List<Group>> = groupDAO.getAll()
    fun getGroupById(id: Int): LiveData<List<Group>> = groupDAO.getById(id)
    fun addGroup(group: Group) = groupDAO.add(group)
    fun removeGroup(id: Int) = groupDAO.remove(id)
    fun updateGroup(group: Group) = groupDAO.update(group)
}