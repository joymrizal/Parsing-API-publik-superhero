package com.joymr.tugas4_parsingapipubliksuperhero

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.joymr.tugas4_parsingapipubliksuperhero.model.Superhero
import com.joymr.tugas4_parsingapipubliksuperhero.network.ApiService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var superheroImageView: ImageView
    private lateinit var superheroNameTextView: TextView
    private lateinit var superheroDetailsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        superheroImageView = findViewById(R.id.superheroImageView)
        superheroNameTextView = findViewById(R.id.superheroNameTextView)
        superheroDetailsTextView = findViewById(R.id.superheroDetailsTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getSuperhero()

        call.enqueue(object : Callback<Superhero> {
            override fun onResponse(call: Call<Superhero>, response: Response<Superhero>) {
                if (response.isSuccessful) {
                    val superhero = response.body()
                    superhero?.let {
                        Log.d("Superhero", "Data: $it") // Debug log

                        superheroNameTextView.text = it.name
                        superheroDetailsTextView.text = """
                Full Name: ${it.biography.fullName ?: "N/A"}
                Alter Egos: ${it.biography.alterEgos ?: "N/A"}
                Aliases: ${it.biography.aliases?.joinToString(", ") ?: "N/A"}
                Place of Birth: ${it.biography.placeOfBirth ?: "N/A"}
                First Appearance: ${it.biography.firstAppearance ?: "N/A"}
                Publisher: ${it.biography.publisher ?: "N/A"}
                Alignment: ${it.biography.alignment ?: "N/A"}
                
                Intelligence: ${it.powerstats.intelligence}
                Strength: ${it.powerstats.strength}
                Speed: ${it.powerstats.speed}
                Durability: ${it.powerstats.durability}
                Power: ${it.powerstats.power}
                Combat: ${it.powerstats.combat}
                
                Gender: ${it.appearance.gender ?: "N/A"}
                Race: ${it.appearance.race ?: "N/A"}
                Height: ${it.appearance.height?.joinToString(", ") ?: "N/A"}
                Weight: ${it.appearance.weight?.joinToString(", ") ?: "N/A"}
                Eye Color: ${it.appearance.eyeColor ?: "N/A"}
                Hair Color: ${it.appearance.hairColor ?: "N/A"}
                
                Occupation: ${it.work.occupation ?: "N/A"}
                Base: ${it.work.base ?: "N/A"}
                
                Group Affiliation: ${it.connections.groupAffiliation ?: "N/A"}
                Relatives: ${it.connections.relatives ?: "N/A"}
            """.trimIndent()
                        Picasso.get().load(it.image.url).into(superheroImageView)
                    }
                }
            }

            override fun onFailure(call: Call<Superhero>, t: Throwable) {
                // Handle error
            }
        })
    }
}