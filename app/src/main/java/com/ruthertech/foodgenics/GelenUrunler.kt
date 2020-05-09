package com.ruthertech.foodgenics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_gelen_urunler.*

class GelenUrunler : AppCompatActivity() {

    var durum =
        arrayOf("Halal", "Suspicious", "Haram")

   lateinit var tumGelenler : ArrayList<GELENURUNDATA>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gelen_urunler)



        init()


    }


    fun init(){
        getVeriler()
    }


    private fun getVeriler() {
        var ref = FirebaseDatabase.getInstance().reference
        tumGelenler = ArrayList<GELENURUNDATA>()
        var sorgu = ref.child("itemsfromusers").addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children){
                   var  gelenData = data.getValue(GELENURUNDATA::class.java)
                    var obje  = GELENURUNDATA()
                    obje.urun_resmi = gelenData?.urun_resmi
                    obje.urun_barkod = gelenData?.urun_barkod

                    tumGelenler.add(obje)
                }

                if (tumGelenler.size >0){
                  var  myAdapter = GelenUrunlerRecylerView(tumGelenler!!,this@GelenUrunler)
                    gelenler_recyler_view.adapter = myAdapter
                    gelenler_recyler_view.layoutManager =  LinearLayoutManager(applicationContext)
                }
                else{
                    Toast.makeText(this@GelenUrunler,"Gelen hiçbir ürün yok!",Toast.LENGTH_LONG).show()
                }


            }

        })
    }






}
