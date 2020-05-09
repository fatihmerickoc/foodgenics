package com.ruthertech.foodgenics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_barkod_ekle.*
import kotlinx.android.synthetic.main.gelenurunler_tek_satir.view.*

class GelenUrunlerRecylerView(tumGelenler : ArrayList<GELENURUNDATA>,mContext : Context): RecyclerView.Adapter<GelenUrunlerRecylerView.GelenUrunHolder>() {

        var tumGelenler = tumGelenler
        var myContext = mContext

    var durum =
        arrayOf("Halal", "Suspicious", "Haram")


        inner class GelenUrunHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            var layout = itemView as ConstraintLayout
            var gelen_urun_resim = layout.gelen_urun_resim
            var gelen_urun_adi_ = layout.gelen_urun_adi
            var gelen_urun_sil_img = layout.gelen_urun_sil_img
            var gelen_urun_gonder_img = layout.gelen_urun_gonder_img
            var halay_spinner  = layout.spinnerhalay


            fun setData(momentEmaddesi: GELENURUNDATA, position: Int){

                val adapter= ArrayAdapter(itemView.context, R.layout.spinner_custom, durum)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                halay_spinner.adapter = adapter


                var path = momentEmaddesi.urun_resmi
                Picasso.get().load(path).into(gelen_urun_resim)

                gelen_urun_sil_img.setOnClickListener {

                    tumGelenler.remove(momentEmaddesi)
                    layout.removeViewAt(position)
                    notifyItemRemoved(position)
                    (myContext as GelenUrunler).init()

                    var ref = FirebaseDatabase.getInstance().reference
                    ref.child("itemsfromusers").child(momentEmaddesi?.urun_barkod.toString()).removeValue()
                    FirebaseStorage.getInstance().reference.child("images/fromusers").child(momentEmaddesi?.urun_barkod.toString()).child("urun_resmi").delete()

                    Toast.makeText(itemView.context,"Ürün silindi!",Toast.LENGTH_LONG).show()

                }

                gelen_urun_gonder_img.setOnClickListener {

                    if (gelen_urun_adi_.text.toString().isNotEmpty()){


                        var obje = BarkodData()
                        obje.urun_barkod = momentEmaddesi?.urun_barkod
                        obje.yenilebilirlik = halay_spinner.selectedItem.toString()
                        obje.urun_adi = gelen_urun_adi_.text.toString()
                        var ref = FirebaseDatabase.getInstance().reference
                        ref.child("items").child(momentEmaddesi?.urun_barkod.toString()).setValue(obje)



                        tumGelenler.remove(momentEmaddesi)
                        layout.removeViewAt(position)
                        notifyItemRemoved(position)
                        (myContext as GelenUrunler).init()

                        var ref1 = FirebaseDatabase.getInstance().reference
                        ref1.child("itemsfromusers").child(momentEmaddesi?.urun_barkod.toString()).removeValue()
                        FirebaseStorage.getInstance().reference.child("images/fromusers").child(momentEmaddesi?.urun_barkod.toString()).child("urun_resmi").delete()


                        Toast.makeText(itemView.context,"Ürün silindi!",Toast.LENGTH_LONG).show()







                    }
                    else{
                        Toast.makeText(itemView.context,"Lütfen ürünün adını girin!",Toast.LENGTH_LONG).show()

                    }
                }

            }




        }







        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GelenUrunlerRecylerView.GelenUrunHolder {
            var inflate = LayoutInflater.from(myContext)
            var row=   inflate.inflate(R.layout.gelenurunler_tek_satir,parent,false)

            return GelenUrunHolder(row)
        }

        override fun getItemCount(): Int {
            return tumGelenler.size
        }



        override fun onBindViewHolder(holder: GelenUrunlerRecylerView.GelenUrunHolder, position: Int) {
            var momentGelenUrun = tumGelenler.get(position)
            holder.setData(momentGelenUrun,position)
        }



    }