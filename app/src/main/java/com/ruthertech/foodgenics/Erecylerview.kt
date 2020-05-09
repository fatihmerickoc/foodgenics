package com.ruthertech.foodgenics

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tek_satir_layout.view.*

class Erecylerview(tumEmaddeleri : ArrayList<UrunData>,mContext : Context) : RecyclerView.Adapter<Erecylerview.UrunHolder>(),
    Filterable {

    var tumEmaddeleri =tumEmaddeleri
    var myContext = mContext
    var myFilter = FilterHelper(tumEmaddeleri,this)



    inner class UrunHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var layout = itemView as ConstraintLayout
        var aciklama = layout.e_Aciklama
        var yenme_durumu = layout.yenme_durumu_txt
        var kare_layout  = layout.relativelayout
        var e_maddeint = kare_layout.e_madde_txt


        fun setData(momentEmaddesi: UrunData, position: Int){
            e_maddeint.text = momentEmaddesi.eMaddeleri
            aciklama.text = momentEmaddesi.eAciklamalar
            yenme_durumu.text = momentEmaddesi.helallik

            when(momentEmaddesi.helallik.toString()){
                "HELAL"->{
                    kare_layout.setBackgroundColor(Color.rgb(39, 174, 96))
                    //layout.setBackgroundColor(Color.rgb(39, 174, 96))
                }
                "HARAM"->{
                    kare_layout.setBackgroundColor(Color.rgb(176, 5, 5))
                    //layout.setBackgroundColor(Color.rgb(176, 5, 5))


                }
                "SUPHELI"->{
                    kare_layout.setBackgroundColor(Color.rgb(255, 153, 0))
                   // layout.setBackgroundColor(Color.rgb(255, 153, 0))

                }
            }



        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Erecylerview.UrunHolder {
        var inflate = LayoutInflater.from(myContext)
        var row=   inflate.inflate(R.layout.tek_satir_layout,parent,false)

        return UrunHolder(row)
    }

    override fun getItemCount(): Int {
        return tumEmaddeleri.size
    }

    override fun onBindViewHolder(holder: Erecylerview.UrunHolder, position: Int) {
        var momentEmaddesi = tumEmaddeleri.get(position)
        holder.setData(momentEmaddesi,position)
    }


    fun setFilter(arrayList: ArrayList<UrunData>) {

        tumEmaddeleri = arrayList

    }
    override fun getFilter(): Filter {
        return myFilter
    }

}