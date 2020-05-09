package com.ruthertech.foodgenics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_food_additives.*

class FoodAdditives : AppCompatActivity() {


    lateinit var tumEmaddeler : Array<String?>
    lateinit var tumEAciklamalar : Array<String?>
    lateinit var tumHelallik : Array<String?>
    lateinit var tumUrunler : ArrayList<UrunData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_additives)

        tumUrunler = ArrayList<UrunData>()

        tumEmaddeler  = resources.getStringArray(R.array.enumbers)
        tumHelallik  = resources.getStringArray(R.array.edurum)
        tumEAciklamalar = resources.getStringArray(R.array.urunaciklama)


        searchViewEmadde.setOnClickListener {
            searchViewEmadde.isIconified = false
        }

        for (madde in 0..tumEmaddeler.size-1){
            var urunObje = UrunData()
            urunObje.eMaddeleri = tumEmaddeler?.get(madde).toString()
            urunObje.eAciklamalar =  tumEAciklamalar?.get(madde).toString()
            urunObje.helallik = tumHelallik?.get(madde).toString()

            tumUrunler.add(urunObje)
        }


        if (tumUrunler.size>0){
            var  myAdapter = Erecylerview(tumUrunler,this)
            e_recylerview.adapter = myAdapter
            e_recylerview.layoutManager =  LinearLayoutManager(applicationContext)
            searchViewEmadde.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    myAdapter.filter.filter(newText)
                    return false
                }

            })

        }






    }


}
