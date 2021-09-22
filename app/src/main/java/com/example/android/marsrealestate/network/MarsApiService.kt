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

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/"

// define enum class to allow filter parameters to be sent when making a GET request
// (... the URL ends on '?filter=<filter.value>' )
enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all"),
}
// Moshi builder
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit builder
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// service interface (exposed by retrofit library)
interface MarsApiService {

    // HTTP GET for endpoint "realestate" --> returns "Mars properties data" (JSON)
    // ... @Query defines a query parameter (keyword "filter") of type String
    //     --> calling getProperties with a string type argument produces a GET request with a URL
    //         ending on on '?filter=<argument>'
    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String): List<MarsProperty>

}

// expose service to the app through 'singleton' object
// ... as instantiation of retrofit is 'expensive' --> done once here to be used by all modules
// ... calling MarsApi.retrofitService will initialize retrofit with its i/f 'MarsApiService'
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}