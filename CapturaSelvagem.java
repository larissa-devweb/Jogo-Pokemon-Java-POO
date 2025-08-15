package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.Random;

/**
 * Captura Pokémon selvagem (50%) ou, se não for selvagem, deixa para batalha.
 * Ao capturar: define dono, marca como não-selvagem e remove do tabuleiro.
 */
public class CapturaSelvagem {

    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro, int linha, int coluna, Treinador treinador) {
        if (!pokemon.isSelvagem()) {
            // Não é selvagem → a regra do PDF manda batalhar (isso acontece fora daqui).
            System.out.println("Este Pokémon pertence a um treinador. Não é possível capturar.");
            return false;
        }

        System.out.println("Tentando capturar " + pokemon.getNome() + "...");
        boolean sucesso = new Random().nextBoolean(); // 50% de chance

        if (sucesso) {
            // Vincula ao treinador
            pokemon.setSelvagem(false);
            pokemon.setTreinador(treinador);

            // Coloca na mochila e registra na Pokédex
            treinador.adicionarPokemonNaMochila(pokemon);

            // Remove do tabuleiro
            tabuleiro.removerPokemon(linha, coluna);

            System.out.println(pokemon.getNome() + " foi capturado com sucesso.");
            return true;
        } else {
            System.out.println(pokemon.getNome() + " escapou.");
            // Tenta mover para célula vizinha livre, se seu Tabuleiro tiver esse método
            try {
                tabuleiro.posicionarPokemonEmVizinhoLivre(linha, coluna, pokemon);
            } catch (Exception e) {
                System.out.println("Não foi possível mover o Pokémon que fugiu: " + e.getMessage());
            }
            return false;
        }
    }
}
