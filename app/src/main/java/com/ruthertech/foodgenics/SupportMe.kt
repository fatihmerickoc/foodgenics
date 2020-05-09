package com.ruthertech.foodgenics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.fragment_support_me.view.*


class SupportMe : DialogFragment() {




    lateinit var support_me_btn : Button
     var mContext : FragmentActivity? = null
    private lateinit var mRewardedVideoAd: RewardedVideoAd



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_support_me, container, false)


        mContext =activity
        support_me_btn = view.reklamizle





        support_me_btn.setOnClickListener {

            Toast.makeText(mContext, "Lütfen bekleyin! Reklam yükleniyor...", Toast.LENGTH_LONG).show()

            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext)
            mRewardedVideoAd.loadAd("ca-app-pub-6291312999750067/4386365475", AdRequest.Builder().build())


            mRewardedVideoAd.rewardedVideoAdListener = object : RewardedVideoAdListener {
                override fun onRewardedVideoAdLoaded() {
                    mRewardedVideoAd.show()
                }

                override fun onRewardedVideoAdOpened() {
                }

                override fun onRewardedVideoCompleted() {

                }

                override fun onRewardedVideoStarted() {
                }

                override fun onRewardedVideoAdClosed() {
                    Toast.makeText(mContext, "Lütfen tekrar izleyin!", Toast.LENGTH_SHORT).show()
                }

                override fun onRewarded(rewardItem: RewardItem) {

                    Toast.makeText(mContext, "Reklam izlediğiniz için teşekkür ederiz. Lütfen tekrar izleyin!", Toast.LENGTH_SHORT).show()
                }

                override fun onRewardedVideoAdLeftApplication() {
                    Toast.makeText(mContext, "Ad Left Application", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onRewardedVideoAdFailedToLoad(i: Int) {
                    Toast.makeText(mContext, "Reklam yüklenemedi ", Toast.LENGTH_SHORT).show()
                }
            }
        }



        return view
    }


}
