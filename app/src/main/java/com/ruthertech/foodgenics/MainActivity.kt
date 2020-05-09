package com.ruthertech.foodgenics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var clicktime = 0
  lateinit  var okunanBarkod : String



    lateinit var mAdView : AdView

    private lateinit var scan_item_inter: InterstitialAd
    private lateinit var emadde_inter: InterstitialAd






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adViewmain)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        scan_item_inter = InterstitialAd(this)
        scan_item_inter.adUnitId = "ca-app-pub-6291312999750067/3267411846"
        scan_item_inter.loadAd(AdRequest.Builder().build())

        emadde_inter = InterstitialAd(this)
        emadde_inter.adUnitId = "ca-app-pub-6291312999750067/9598329553"
        emadde_inter.loadAd(AdRequest.Builder().build())







        imglogo.setOnClickListener {
            clicktime++
            checkClikTime()
        }

        scan_item_btn.setOnClickListener {

                if (scan_item_inter.isLoaded) {
                    scan_item_inter.show()
                    scan_item_inter.adListener = object :  AdListener(){
                        override fun onAdFailedToLoad(p0: Int) {
                            super.onAdFailedToLoad(p0)
                            scanItem()

                        }
                        override fun onAdClosed() {
                            super.onAdClosed()
                            scanItem()

                        }
                    }

                }
                else {
                    scanItem()
                }

        }

        foodadditives_btn.setOnClickListener {
            if (emadde_inter.isLoaded) {
                emadde_inter.show()
                emadde_inter.adListener = object :  AdListener(){
                    override fun onAdFailedToLoad(p0: Int) {
                        super.onAdFailedToLoad(p0)
                        var intent = Intent(this@MainActivity,FoodAdditives::class.java)
                        startActivity(intent)
                    }
                    override fun onAdClosed() {
                        super.onAdClosed()
                        var intent = Intent(this@MainActivity,FoodAdditives::class.java)
                        startActivity(intent)
                    }
                }
            }
            else {
                var intent = Intent(this@MainActivity,FoodAdditives::class.java)
                startActivity(intent)
            }

        }


        contact_btn.setOnClickListener {
            var intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }
        coffee_btn.setOnClickListener {
            SupportMe().show(supportFragmentManager,"REKLAMGOSTER")
        }








    }


    private fun scanItem() {

        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        scanner.setCameraId(0);
        scanner.setBeepEnabled(true)
        scanner.setOrientationLocked(false);
        scanner.initiateScan()


    }

    private fun checkClikTime() {
        if (clicktime.toInt() == 5){
            showAdminPanel()
            clicktime = 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)


        if(result != null) {
            if(result.getContents() == null) {
            } else {
                okunanBarkod = result.contents.toString()
                control()
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }



        super.onActivityResult(requestCode, resultCode, data)


    }

    private fun control() {
        readDataFromFirebase()
    }

    private fun readDataFromFirebase() {
        var ref = FirebaseDatabase.getInstance().reference
        var sorgu = ref.child("items").child(okunanBarkod).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                    var gelenData = p0.getValue(BarkodData::class.java)
                    if (gelenData?.urun_barkod.toString() == okunanBarkod){
                        Product(gelenData?.urun_adi,gelenData?.yenilebilirlik,gelenData?.urun_barkod).show(supportFragmentManager,"UrunFragment")
                    }
                    else{
                        Toast.makeText(this@MainActivity,"Ürün Bulunamadı",Toast.LENGTH_LONG).show()
                        ItemNotFound(okunanBarkod).show(supportFragmentManager,"ItemNotFound")
                    }

            }

        })
    }

    private fun showAdminPanel() {
        PasswordFragment().show(supportFragmentManager,"PasswordFragment")
    }
}
