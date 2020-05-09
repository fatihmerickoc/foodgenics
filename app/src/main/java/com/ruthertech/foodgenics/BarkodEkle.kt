package com.ruthertech.foodgenics

import android.R.attr.country
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_barkod_ekle.*


class BarkodEkle : AppCompatActivity() {

    var durum =
        arrayOf("Halal", "Suspicious", "Haram")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barkod_ekle)

        barcode_et.setOnClickListener {
            scanItem()
        }

        val adapter= ArrayAdapter(this, R.layout.spinner_custom, durum)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yenme_spinner.adapter = adapter


        ekle_btn.setOnClickListener {
            if (barcode_et.text.toString().isNotEmpty()&&productname_et.text.toString().isNotEmpty()){


                var ref = FirebaseDatabase.getInstance().reference

                var barkodObje =  BarkodData()
                barkodObje.urun_adi =productname_et.text.toString()
                barkodObje.yenilebilirlik = yenme_spinner.selectedItem.toString()
                barkodObje.urun_barkod  = barcode_et.text.toString()

                ref.child("items").child(barcode_et.text.toString()).setValue(barkodObje).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Ürün eklendi. ", Toast.LENGTH_LONG).show();

                        productname_et.text.clear()
                        barcode_et.text.clear()

                    }
                }

            }
            else{
                Toast.makeText(this, "Tüm alanları doldurun ", Toast.LENGTH_LONG).show();
            }
        }
        urunekle_txt.setOnClickListener {
            var intent = Intent(this,GelenUrunler::class.java)
            startActivity(intent)
        }




    }


     fun scanItem() {
        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        scanner.setCameraId(0);
        scanner.setBeepEnabled(true)
        scanner.setOrientationLocked(false);
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)


        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                barcode_et.setText(result.contents)
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

}
