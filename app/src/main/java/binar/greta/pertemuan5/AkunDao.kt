package binar.greta.pertemuan5

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AkunDao {

    @Insert
    fun insertAkun(akun : Akun) : Long
}