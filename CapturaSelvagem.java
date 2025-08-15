package jogopokemon;

import java.util.Random;
import jogopokemon.pokemons.Pokemon;

/**
 * Captura simples:
 * ✔ Ao capturar, move para mochila/time do treinador e marca como não-selvagem.
 */
public class CapturaSelvagem {

    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro,
                                        int linha, int coluna, Treinador treinador) {
        double chance = new Random().nextDouble();

        if (chance < 0.5) { // capturou
            treinador.adicionarPokemon(pokemon); // ✔ método agora existe
            pokemon.setSelvagem(false);
            pokemon.setTreinador(treinador);
            tabuleiro.removerPokemon(linha, coluna);
            System.out.println(pokemon.getNome() + " foi capturado!");
            return true;
        } else {
            System.out.println(pokemon.getNome() + " escapou!");
            return false;
        }
    }
}
