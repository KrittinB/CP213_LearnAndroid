package com.example.a157lablearnandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// --- Data Models ---
data class PokemonResponse(
    val pokemon_entries: List<PokemonEntry>
)

data class PokemonEntry(
    val entry_number: Int,
    val pokemon_species: PokemonSpecies
)

data class PokemonSpecies(
    val name: String
)

// --- Retrofit Service ---
interface PokeApiService {
    @GET("pokedex/2/") // Kanto Pokedex
    suspend fun getPokedex(): PokemonResponse

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        fun create(): PokeApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
        }
    }
}

// --- ViewModel ---
class PokemonViewModel : ViewModel() {

    private val pokeApiService = PokeApiService.create()

    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList: StateFlow<List<PokemonEntry>> = _pokemonList.asStateFlow()

    init {
        fetchPokemon()
    }

    private fun fetchPokemon() {
        viewModelScope.launch {
            try {
                val response = pokeApiService.getPokedex()
                _pokemonList.value = response.pokemon_entries
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
