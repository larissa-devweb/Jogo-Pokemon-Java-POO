package jogopokemon.testes;

import jogopokemon.*;
import jogopokemon.pokemons.*;
import java.util.ArrayList;

public class JogoPokemon {

    public static void main(String[] args) {
        try {
            // Cria tabuleiro
            Tabuleiro tabuleiro = new Tabuleiro(5);

            // Cria treinadores
            Treinador jogador = new Treinador("Ash");
            Treinador computador = new Treinador("Gary");

            // Pokémon inicial do jogador
            Pokemon inicial = new Agua("Squirtle", false);
            jogador.adicionarPokemon(inicial);
            tabuleiro.posicionarPokemon(0, 0, inicial, true);

            // Pokémons selvagens
            Pokemon p1 = new Agua("Psyduck", true);
            Pokemon p2 = new Eletrico("Raichu", true);
            Pokemon p3 = new Floresta("Bulbasaur", true);

            tabuleiro.posicionarPokemon(1, 1, p1, false);
            tabuleiro.posicionarPokemon(2, 2, p2, false);
            tabuleiro.posicionarPokemon(3, 3, p3, false);

            // Mostra tabuleiro inicial
            System.out.println("Tabuleiro Inicial:");
            tabuleiro.exibir();

            // Inicia Thread do computador
            JogadorComputador cpu = new JogadorComputador(tabuleiro, computador, (IAtaque) jogador);
            cpu.start();

            // Loop principal do jogador no console
            while (tabuleiro.temPokemonsSelvagens()) {
                System.out.println("\nTurno do jogador " + jogador.getNome());
                // Aqui você poderia implementar lógica de captura via Scanner ou GUI
                // Por enquanto apenas exibe pontuação
                System.out.println("Pontuação Ash: " + jogador.getPontuacao());
                System.out.println("Pontuação Gary: " + computador.getPontuacao());

                Thread.sleep(3000); // espera entre turnos
            }

            // Fim de jogo
            System.out.println("\n=== Fim de Jogo ===");
            if (jogador.getPontuacao() > computador.getPontuacao()) {
                System.out.println("Vencedor: " + jogador.getNome());
            } else if (jogador.getPontuacao() < computador.getPontuacao()) {
                System.out.println("Vencedor: " + computador.getNome());
            } else {
                System.out.println("Empate!");
            }

            System.out.println("Pontuação final - Ash: " + jogador.getPontuacao() +
                    ", Gary: " + computador.getPontuacao());

            tabuleiro.exibir();

        } catch (RegiaoInvalidaException e) {
            System.out.println("Erro ao posicionar Pokémon: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
