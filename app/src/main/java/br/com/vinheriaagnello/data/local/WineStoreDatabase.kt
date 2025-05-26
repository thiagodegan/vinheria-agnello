package br.com.vinheriaagnello.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class WineStoreDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: WineStoreDatabase? = null

        fun getDatabase(context: Context): WineStoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WineStoreDatabase::class.java,
                    "wine_store_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
