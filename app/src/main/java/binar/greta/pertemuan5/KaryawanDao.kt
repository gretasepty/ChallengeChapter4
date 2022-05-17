package binar.greta.pertemuan5

import androidx.room.*

@Dao
interface KaryawanDao {

    @Insert
    fun insertKaryawan(karyawan : Karyawan) : Long

    @Query("SELECT * FROM Karyawan")
    fun getAllKaryawan() : List<Karyawan>

    @Delete
    fun deleteKaryawan(karyawan: Karyawan) : Int

    @Update
    fun updateKaryawan(karyawan : Karyawan) : Int

}