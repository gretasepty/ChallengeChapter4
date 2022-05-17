package binar.greta.pertemuan5

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.custom_dialog_edit.*
import kotlinx.android.synthetic.main.custom_dialog_edit.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_adapter_karyawan.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

//    private var mDBnew : KaryawanDatabase? = null

    lateinit var pref : SharedPreferences
    private var dbkaryawan : KaryawanDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbkaryawan = KaryawanDatabase.getInstance(requireContext())

        pref = requireContext().getSharedPreferences("tugas", Context.MODE_PRIVATE)
        val dataEmail = pref.getString("EMAIL", "")
        val dataUsername = pref.getString("USERNAME", "")
        txt_usernameHome.text = dataUsername

        getDataKaryawan()

//        Logout
        txt_logoutHome.setOnClickListener {
            val alertLogout = AlertDialog.Builder(requireContext())
                    .setTitle("LOGOUT")
                    .setMessage("Yakin ingin keluar dari halaman ini?")
                    .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                        pref.edit().clear().apply()

                        val intent = activity?.intent
                        activity?.finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    alertLogout.show()
        }

//      Tambah Data Karyawan
        fab_add.setOnClickListener {
            val custom = LayoutInflater.from(requireContext())
                    .inflate(R.layout.custom_dialog, null, false)
            val alert = AlertDialog.Builder(requireContext())
                    .setView(custom)
                    .create()

            custom.btn_simpanDialog.setOnClickListener {
                GlobalScope.async {
                    val nama = custom.edt_namaDialog.text.toString()
                    val alamat = custom.edt_alamatDialog.text.toString()

                    val hasil = dbkaryawan?.karyawanDao()?.
                    insertKaryawan(Karyawan(null, nama, alamat))

                    activity?.runOnUiThread {
                        if (hasil != 0.toLong()){
                            Toast.makeText(requireContext(), "Sukses", Toast.LENGTH_LONG).show()
                            activity?.recreate()

                        }else{
                            Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                alert.dismiss()
            }
            alert.show()
        }


    }
//    Menampilkan data Karyawan
      fun getDataKaryawan() {
        rv_home.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        GlobalScope.launch {
            val listdata = dbkaryawan?.karyawanDao()?.getAllKaryawan()

            activity?.runOnUiThread {
                listdata.let {
                    val adapt = AdapterKaryawan(it!!)
                    rv_home.adapter = adapt
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataKaryawan()
    }

    override fun onDestroy() {
        super.onDestroy()
        KaryawanDatabase.destroyInstance()
    }


}