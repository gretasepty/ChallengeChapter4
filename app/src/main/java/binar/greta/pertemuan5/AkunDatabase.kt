package binar.greta.pertemuan5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Akun::class], version = 1)
abstract class AkunDatabase : RoomDatabase(){

    abstract fun akunDao() : AkunDao

    companion object{
        private var INSTANCE : AkunDatabase? = null
        fun getInstance(context: Context) : AkunDatabase?{
            if(INSTANCE == null){
                synchronized(AkunDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AkunDatabase::class.java, "Akun.db"
                    ).build()
                }
            }
            return  INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}