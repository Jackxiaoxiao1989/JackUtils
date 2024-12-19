package com.example.kotlinstudy.room

import androidx.room.*


@Dao
interface UserRoomDao {
    @Insert
    fun insertRooms(vararg datarooms: UserRoom)

    @Query("SELECT * FROM UserRoom")
    fun allRooms(): List<UserRoom>

    @Query("SELECT * FROM UserRoom WHERE id=:id")
    fun getAllRoomsById(id: Int): List<UserRoom>

    @Query("DELETE FROM UserRoom")
    fun clearAll()

    @Query("DELETE FROM UserRoom WHERE id=:id")
    fun deleteRoomsById(id:Int)

    @Update
    fun upData(vararg datarooms: UserRoom)

    @Delete
    fun delete(vararg datarooms: UserRoom)
}