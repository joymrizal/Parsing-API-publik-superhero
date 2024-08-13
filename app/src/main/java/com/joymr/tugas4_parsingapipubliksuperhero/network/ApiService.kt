package com.joymr.tugas4_parsingapipubliksuperhero.network

import com.joymr.tugas4_parsingapipubliksuperhero.model.Superhero
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api.php/7f7f513230a41910ed7be37e562fde1f/502")
    fun getSuperhero(): Call<Superhero>
}