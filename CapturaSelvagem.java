/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.Random;

// Classe que cuida da lógica de capturar um Pokémon selvagem
public class CapturaSelvagem {

    // Tenta capturar um Pokémon, informando a sua posição atual no tabuleiro
    public static boolean tentarCaptura(Pokemon pokemonAlvo, Tabuleiro tabuleiro, int linhaAtual,
                                        int colunaAtual, Treinador treinador) {
        
        // Se o Pokémon já foi capturado antes, não faz sentido tentar capturar de novo
        if (!pokemonAlvo.isSelvagem()) {
            System.out.println(pokemonAlvo.getNome() + " já pertence a um treinador! Não pode ser capturado.");
            return false;
        }

        // Gera aleatoriamente se a captura será bem-sucedida (50% de chance)
        Random random = new Random();
        boolean capturado = random.nextBoolean();

        if (capturado) {
            System.out.println("Sucesso! Você capturou " + pokemonAlvo.getNome() + "!");
            pokemonAlvo.setSelvagem(false);           // marca que ele não é mais selvagem
            treinador.adicionarPokemon(pokemonAlvo);  // coloca na mochila do treinador
        } else {
            System.out.println(pokemonAlvo.getNome() + " fugiu! Tentando se mover para outra posição...");

            // Lista de direções possíveis: cima, baixo, esquerda, direita
            int[][] direcoes = {
                {-1, 0}, // cima
                {1, 0},  // baixo
                {0, -1}, // esquerda
                {0, 1}   // direita
            };

            boolean conseguiuMover = false;

            // Tenta mover para uma célula vizinha livre
            for (int[] direcao : direcoes) {
                int novaLinha = linhaAtual + direcao[0];
                int novaColuna = colunaAtual + direcao[1];

                if (tabuleiro.posicaoValida(novaLinha, novaColuna) &&
                    tabuleiro.estaVazio(novaLinha, novaColuna)) {

                    tabuleiro.removerPokemon(linhaAtual, colunaAtual); // limpa a célula antiga
                    tabuleiro.posicionarPokemon(novaLinha, novaColuna, pokemonAlvo, true); // reposiciona

                    System.out.println(pokemonAlvo.getNome() + " fugiu para [" + novaLinha + "," + novaColuna + "].");
                    conseguiuMover = true;
                    break; // para de tentar depois da primeira posição válida
                }
            }

            if (!conseguiuMover) {
                System.out.println(pokemonAlvo.getNome() + " tentou fugir, mas está cercado. Ficou parado.");
            }
        }

        return capturado;
    }
}
