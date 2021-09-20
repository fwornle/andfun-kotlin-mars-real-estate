/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

// Retrofit builder
// (deprecated, but seems to be the one that works, see below... 'Moshi')
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// NOTE:
// with this Factory, the app throws an error upon trying to convert the received response (GET)
//
// (MoshiConverterFactory is obviously not the 'updated version' of ScalarsConverterFactory)
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()

// service interface (exposed by retrofit library)
interface MarsApiService {

    // HTTP GET for endpoint "realestate" --> returns "Mars properties data" (JSON)
    @GET("realestate")
    fun getProperties():
            Call<String>

}

// expose service to the app through 'singleton' object
// ... as instantiation of retrofit is 'expensive' --> done once here to be used by all modules
// ... calling MarsApi.retrofitService will initialize retrofit with its i/f 'MarsApiService'
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}