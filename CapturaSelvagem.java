package jogopokemon;

import java.util.Random;

/**
 * Captura simples:
 * ✔ Ao capturar, move para mochila/time do treinador e marca como não-selvagem.
 * ✔ Remove o Pokémon do tabuleiro.
 */
public class CapturaSelvagem {

    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro,
                                        int linha, int coluna, Treinador treinador) {
        double chance = new Random().nextDouble();

        if (chance < 0.5) { // capturou
            treinador.adicionarPokemon(pokemon); // adiciona ao time/mochila
            pokemon.setSelvagem(false);
            pokemon.setTreinador(treinador);

            // remove do tabuleiro
            tabuleiro.removerPokemon(linha, coluna);

            System.out.println(pokemon.getNome() + " foi capturado!");
            return true;
        } else {
            System.out.println(pokemon.getNome() + " escapou!");
            return false;
        }
    }
}
