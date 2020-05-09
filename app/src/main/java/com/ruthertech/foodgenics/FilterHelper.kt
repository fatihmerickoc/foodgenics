package com.ruthertech.foodgenics
import android.widget.Filter

class FilterHelper(tumEmaddeleri : ArrayList<UrunData>,adapter: Erecylerview) : Filter() {

    var suankiListe = tumEmaddeleri
    var suankiAdapter = adapter


    override fun performFiltering(constraint: CharSequence?): FilterResults {

        var sonuc = FilterResults()

        if (constraint!= null&&constraint.length >0){

            var aranilanAd = constraint.toString().toLowerCase()
            var eslesenler = ArrayList<UrunData>()

            for (emadde in suankiListe){

                    var adi  = emadde.eMaddeleri!!.toLowerCase()

                    if (adi.contains(aranilanAd.toString())){

                        eslesenler.add(emadde)


                    }




            }


            sonuc.values= eslesenler
            sonuc.count = eslesenler.size



        }else{
            sonuc.values = suankiListe
            sonuc.count = suankiListe.size
        }





        return sonuc
    }


    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

        suankiAdapter.setFilter(results?.values as ArrayList<UrunData>)
        suankiAdapter.notifyDataSetChanged()

    }




}
