package com.example.kotlinstudy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [UserRoom::class], version = 1, exportSchema = true)
abstract class UserRoomDatabase : RoomDatabase() {
   // abstract val userRoomDao: UserRoomDao?
    abstract fun getUserRoomDao(): UserRoomDao?
    companion object {
        private var mInstance: UserRoomDatabase? = null
        private const val DATABASE_NAME = "userroom.db"

        @Synchronized
        fun getInstance(context: Context): UserRoomDatabase? {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2) // 数据库升级时
                    .fallbackToDestructiveMigration() // 数据库版本异常时 会清空原来的数据  然后转到目前版本
                    .build()
            }
            return mInstance
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //database.execSQL("ALTER TABLE userroom ADD COLUMN data TEXT NOT NULL DEFAULT 1") // 添加表字段
                database.execSQL("ALTER TABLE UserRoom ADD COLUMN data TEXT") // 添加表字段
            }
        }
    }
}