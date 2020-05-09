package com.ruthertech.foodgenics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.fragment_product.view.buttonOk

class Product(urunAdi: String?, yenilebilirlik: String?, urunBarkod: String?) : DialogFragment() {

     var mContext : FragmentActivity? = null
     var urunAdi = urunAdi
     var urunBarkod = urunBarkod
     var yenilebilirlik = yenilebilirlik
    lateinit var urunadi_txt : TextView
    lateinit var durum : TextView
    lateinit var aciklama : TextView
    lateinit var  buttonOk : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.fragment_product, container, false)
        mContext = activity
        durum = view.durum
        urunadi_txt = view.product_name
        aciklama = view.aciklama_text
        buttonOk = view.buttonOk

        if (yenilebilirlik.equals("Halal")){
            view.relative.setBackgroundColor(Color.rgb(3, 172, 19))
            durum.text = "Helal"
            aciklama.text = "Helal, dinin kurallarına aykırı olmayan, dince yasaklanmamış olan şeylerdir. Dinen yapılması veya yenip içilmesi yasaklanmayan, serbest bırakılan şeyler helaldir."
        }
        else if (yenilebilirlik.equals("Haram")){
            view.relative.setBackgroundColor(Color.rgb(213, 41, 24  ))
            durum.text = "Haram"
            aciklama.text = "Haram, yapılması dinî bakımdan yasaklanan herhangi bir şeydir."
        }
        else if (yenilebilirlik.equals("Suspicious")){
            view.relative.setBackgroundColor(Color.rgb(243, 156, 18 ))
            durum.text =  "Şüpheli"
            aciklama.text= "Helal veya Haram olduğu belli olmayan şeyler."
        }


        urunadi_txt.text = urunAdi

        buttonOk.setOnClickListener {
            dialog?.dismiss()
        }




        return view
    }

}
