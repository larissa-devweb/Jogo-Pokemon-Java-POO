package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.Random;

public class CapturaSelvagem {

    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro, int linha, int coluna, Treinador treinador) {
        Random rand = new Random();
        double chance = rand.nextDouble();

        if (chance < 0.5) { // captura bem-sucedida
            treinador.adicionarPokemon(pokemon); //  Adicionado: coloca Pokémon na mochila
            pokemon.setSelvagem(false);
            pokemon.setTreinador(treinador);
            tabuleiro.removerPokemon(linha, coluna);
            return true;
        } else {
            System.out.println(pokemon.getNome() + " escapou!");
            return false;
        }
    }
}
