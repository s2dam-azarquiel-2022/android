package net.azarquiel.friendroom.model

import android.app.Application
import androidx.lifecycle.LiveData

class ProductRepository(application: Application) {
    val friendDAO = FriendsDB.getDB(application).friendDAO()
    val familyDAO = FriendsDB.getDB(application).familyDAO()

    fun getAllFriends(): LiveData<List<Friend>> = friendDAO.getAll()
    fun getAllFamilies(): LiveData<List<Family>> = familyDAO.getAll()
    fun getFriendById(id: Int): LiveData<List<Friend>> = friendDAO.getById(id)
    fun getFamilyById(id: Int): LiveData<List<Family>> = familyDAO.getById(id)
    fun addFriend(friend: Friend) = friendDAO.add(friend)
    fun addFamily(family: Family) = familyDAO.add(family)
    fun removeFriend(id: Int) = friendDAO.remove(id)
    fun removeFamily(id: Int) = familyDAO.remove(id)
    fun updateFriend(friend: Friend) = friendDAO.update(friend)
    fun updateFamily(family: Family) = familyDAO.update(family)
}