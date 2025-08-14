package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.Random;

/**
 * Classe responsável por tentar capturar Pokémon selvagens.
 */
public class CapturaSelvagem {

    /**
     * Método estático que tenta capturar um Pokémon selvagem.
     *
     * @param pokemon   Pokémon alvo da captura
     * @param tabuleiro Tabuleiro do jogo
     * @param linha     Linha onde o Pokémon está
     * @param coluna    Coluna onde o Pokémon está
     * @param treinador Treinador que está tentando a captura
     * @return true se a captura for bem-sucedida, false caso contrário
     */
    public static boolean tentarCaptura(Pokemon pokemon, Tabuleiro tabuleiro, int linha, int coluna, Treinador treinador) {
        // Criamos um gerador de números aleatórios
        Random random = new Random();

        // Simulamos uma chance de captura (50% neste caso)
        double chance = random.nextDouble(); // retorna valor entre 0.0 e 1.0

        // Se a chance for menor que 0.5, a captura é bem-sucedida
        if (chance < 0.5) {
            // Adiciona o Pokémon à mochila do treinador
            treinador.adicionarPokemon(pokemon);

            // Remove o Pokémon do tabuleiro
            tabuleiro.removerPokemon(linha, coluna);

            // Retorna sucesso
            return true;
        } else {
            // Caso falhe, o Pokémon tenta fugir para uma célula vizinha
            tabuleiro.posicionarPokemonEmVizinhoLivre(linha, coluna, pokemon);

            // Retorna falha
            return false;
        }
    }
}
