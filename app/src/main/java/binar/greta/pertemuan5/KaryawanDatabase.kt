package binar.greta.pertemuan5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Karyawan::class], version = 1)
abstract class KaryawanDatabase : RoomDatabase() {

    abstract fun karyawanDao(): KaryawanDao


    companion object {
        private var INSTANCE: KaryawanDatabase? = null
        fun getInstance(context: Context): KaryawanDatabase? {
            if (INSTANCE == null) {
                synchronized(KaryawanDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        KaryawanDatabase::class.java, "Karyawan.db"
                    ).build()
                }

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}