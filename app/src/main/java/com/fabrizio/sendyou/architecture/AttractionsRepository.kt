package com.fabrizio.sendyou.architecture

import android.content.Context
import com.fabrizio.sendyou.R
import com.fabrizio.sendyou.data.Attraction
import com.fabrizio.sendyou.data.AttractionsResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AttractionsRepository {

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    fun parseAttractions(context: Context): ArrayList<Attraction> {
        val textFromFile =
            context.resources.openRawResource(R.raw.croatia).bufferedReader().use { it.readText() }
        val adapter: JsonAdapter<AttractionsResponse> =
            moshi.adapter(AttractionsResponse::class.java)
        return adapter.fromJson(textFromFile)!!.attractions as ArrayList<Attraction>
    }
}