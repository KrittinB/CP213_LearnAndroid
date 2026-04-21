package org.example.a157lablearnandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PokedexScreen(viewModel: PokemonViewModel) {
    val pokemonList by viewModel.pokemonList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color.Red).padding(16.dp)) {
        Column(modifier = Modifier.fillMaxSize().background(Color.Gray).padding(16.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
                items(pokemonList) { item ->
                    Row(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.entry_number.toString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = item.pokemon_species.name)
                        
                        Spacer(modifier = Modifier.weight(1f))

                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.entry_number}.png"

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Sprite of ${item.pokemon_species.name}",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
        }
    }
}
