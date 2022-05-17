package binar.greta.pertemuan5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class RegisterFragment : Fragment() {

    lateinit var prefs : SharedPreferences
    private var dbakun : AkunDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbakun = AkunDatabase.getInstance(requireContext())
        prefs = requireContext().getSharedPreferences("tugas", Context.MODE_PRIVATE)

        btn_register.setOnClickListener {
            if (edt_usernameReg.text.isNotEmpty() &&
                    edt_emailReg.text.isNotEmpty() &&
                    edt_passwordReg.text.isNotEmpty() &&
                    edt_UlangPasswordReg.text.isNotEmpty()){

                if(edt_passwordReg.text.toString() != edt_UlangPasswordReg.text.toString()){
                    Toast.makeText(requireContext(), "Konfirmasi Password tidak sesuai", Toast.LENGTH_SHORT).show()
                }else{
                    saveRegister()
                }
            }else{
                Toast.makeText(requireContext(), "DATA BELUM TERISI", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    Penyimpanan data register menggunaka DB dan shared preferences
    fun saveRegister(){
        val sf = prefs.edit()
        val username = edt_usernameReg.text.toString()
        val email = edt_emailReg.text.toString()
        val password = edt_passwordReg.text.toString()


        sf.putString("USERNAME", username )
        sf.putString("EMAIL", email )
        sf.putString("PASSWORD", password )
        sf.apply()

        GlobalScope.async {
            val perintahReg = dbakun?.akunDao()?.insertAkun(Akun(null, username, email, password ))

            activity?.runOnUiThread {
                if (perintahReg != 0.toLong()){
                    Toast.makeText(requireContext(), "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(requireView()).navigate(R.id.registerKe_loginFrag)
                }else
                    Toast.makeText(requireContext(), "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}