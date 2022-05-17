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


class LoginFragment : Fragment() {

    lateinit var pref : SharedPreferences
    private var mdbakun : AkunDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mdbakun = AkunDatabase.getInstance(requireContext())
        pref = requireContext().getSharedPreferences("tugas", Context.MODE_PRIVATE)

//        Konfirmasi Login untuk masuk ke halaman home
        btn_login.setOnClickListener {
            if(edt_email.text.isNotEmpty() && edt_password.text.isNotEmpty()){  //input email dan password tidak boleh kosong
                val email = edt_email.text.toString()
                val password = edt_password.text.toString()
                val sf = pref.edit()

                if (email.equals(pref.getString("EMAIL", "")) &&    //Menentukan email dan pass menggunakan shared preferences
                        password.equals(pref.getString("PASSWORD", ""))){
                    sf.putString("EMAIL", email)
                    sf.putString("PASSWORD", password)
                    sf.apply()
                    Navigation.findNavController(view).navigate(R.id.loginKe_homeFrag)
                }else{
                    Toast.makeText(requireContext(), "Email atau Password Salah", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Email dan Password belum diisi", Toast.LENGTH_SHORT).show()
            }
        }


//      text register untuk masuk ke halaman register
        txt_register.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.loginKe_registerFrag)
        }
    }

}