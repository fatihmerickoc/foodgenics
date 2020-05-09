package com.ruthertech.foodgenics

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_password.view.*

/**
 * A simple [Fragment] subclass.
 */
class PasswordFragment() :DialogFragment()  {

     var mContext  : FragmentActivity ? = null
    lateinit var btn_check:Button
    lateinit var  password_et : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=  inflater.inflate(R.layout.fragment_password, container, false)

        mContext = activity
        btn_check = view.check_btn
        password_et = view.password_et

        btn_check.setOnClickListener {

            if (password_et.text.toString() == "782246Fmk."){
                dialog?.dismiss()
                Toast.makeText(mContext,"Welcome Fatih Meric Koc (Boss)",Toast.LENGTH_LONG).show()
                var intent = Intent(mContext,BarkodEkle::class.java)
                (mContext as MainActivity).startActivity(intent)
            }
            else{
                Toast.makeText(mContext, "Wrong password", Toast.LENGTH_LONG).show()
                dialog!!.dismiss()
            }
        }


        return view
    }

}
