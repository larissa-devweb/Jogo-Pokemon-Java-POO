package jogopokemon;

import java.util.Random;

/**
 * Captura simples de Pokémon selvagem:
 * ✔ Ao capturar, move para o time do treinador e marca como não-selvagem.
 * ✔ Remove o Pokémon do tabuleiro.
 */
public class CapturaSelvagem {

    /**
     * Tenta capturar um Pokémon selvagem.
     *
     * @param pokemon   O Pokémon selvagem a ser capturado
     * @param tabuleiro O tabuleiro onde o Pokémon está
     * @param linha     Linha do Pokémon no tabuleiro
     * @param coluna    Coluna do Pokémon no tabuleiro
     * @param treinador O treinador que tenta capturar
     * @return true se capturado, false se escapou
     */
    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro,
                                        int linha, int coluna, Treinador treinador) {
        double chance = new Random().nextDouble();

        if (chance < 0.5) { // captura com 50% de chance
            treinador.adicionarPokemon(pokemon); // adiciona ao time do treinador
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
