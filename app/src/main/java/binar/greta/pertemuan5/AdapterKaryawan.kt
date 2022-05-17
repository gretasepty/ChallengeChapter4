package binar.greta.pertemuan5

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_edit.view.*
import kotlinx.android.synthetic.main.item_adapter_karyawan.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterKaryawan(val listDataKaryawan : List<Karyawan>) : RecyclerView.Adapter<AdapterKaryawan.ViewHolder>() {

    private var mdb : KaryawanDatabase? = null

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val viewitem = LayoutInflater.from(parent.context).inflate(R.layout
               .item_adapter_karyawan, parent, false)
        return  ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txt_id.text = listDataKaryawan[position].id.toString()
        holder.itemView.txt_nama.text = listDataKaryawan[position].nama
        holder.itemView.txt_alamat.text = listDataKaryawan[position].alamat

//        Hapus Data Karyawan
        holder.itemView.btn_delete.setOnClickListener {
            mdb = KaryawanDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                    .setTitle("Hapus Data")
                    .setMessage("Yakin Ingin Menghapus Data?")
                    .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                        GlobalScope.async {
                            val result = mdb?.karyawanDao()?.deleteKaryawan(listDataKaryawan[position])
                            (holder.itemView.context as MainActivity).runOnUiThread {
                                if(result !=0 ){
                                    Toast.makeText(it.context, "Data ${listDataKaryawan[position].nama} Berhasil Dihapus",
                                            Toast.LENGTH_LONG).show()
                                    (it.context as MainActivity).recreate()
                                }else{
                                    Toast.makeText(it.context, "Data ${listDataKaryawan[position].nama} Gagal Dihapus",
                                            Toast.LENGTH_LONG).show()
                                    Log.i("Data ${listDataKaryawan[position].nama} Gagal Dihapus", result.toString())
                                }
                            }
                            (holder.itemView.context as HomeFragment).getDataKaryawan()
                        }
                    }
                    .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    .show()
        }

//        Edit Data Karyawan
        holder.itemView.btn_edit.setOnClickListener {
            mdb = KaryawanDatabase.getInstance(it.context)

            val customEdit = LayoutInflater.from(it.context)
                    .inflate(R.layout.custom_dialog_edit, null, false)
            val alertEdit = AlertDialog.Builder(it.context)
                    .setView(customEdit)
                    .create()

            customEdit.edt_namaEdit.setText(listDataKaryawan[position].nama)
            customEdit.edt_alamatEdit.setText(listDataKaryawan[position].alamat)

            customEdit.btn_simpanEdit.setOnClickListener {
                val getNama = customEdit.edt_namaEdit.text.toString()
                val getAlamat = customEdit.edt_alamatEdit.text.toString()
                listDataKaryawan[position].nama = getNama
                listDataKaryawan[position].alamat = getAlamat

                GlobalScope.async {
                    val perintah = mdb?.karyawanDao()?.updateKaryawan(listDataKaryawan[position])
                    (customEdit.context as MainActivity).runOnUiThread {
                        if (perintah != 0){
                            Toast.makeText(it.context, "Berhasil diedit", Toast.LENGTH_LONG).show()
                            (customEdit.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Gagal diedit", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            alertEdit.show()


        }



    }

    override fun getItemCount(): Int {
        return  listDataKaryawan.size

    }


}