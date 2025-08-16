package jogopokemon.testes;

import jogopokemon.*;
import jogopokemon.pokemons.*;

import java.util.Scanner;

/**
 * MainPosicionamento.java
 *
 * Testa o posicionamento de Pokémons no tabuleiro:
 * - Verifica se o Pokémon vai para a região correta
 * - Testa o modo debug
 * - Lança RegiaoInvalidaException quando necessário
 */
public class MainPosicionamento {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tamanho = 6; // Tamanho do tabuleiro (6x6)
        Tabuleiro tabuleiro = new Tabuleiro(tamanho);

        // Pergunta ao usuário se quer ativar modo debug
        System.out.print("Modo debug (true/false)? ");
        boolean debug = scanner.nextBoolean();

        try {
            // Cria Pokémons de cada tipo
            Pokemon p1 = new Agua("Squirtle", false);
            Pokemon p2 = new Floresta("Bulbasaur", false);
            Pokemon p3 = new Terra("Diglett", false);
            Pokemon p4 = new Eletrico("Pikachu", false);

            if (debug) {
                // Posicionamento manual pelo usuário
                System.out.println("Digite linha e coluna para posicionar Squirtle:");
                int l = scanner.nextInt();
                int c = scanner.nextInt();
                tabuleiro.posicionarPokemon(l, c, p1, true);

                System.out.println("Digite linha e coluna para posicionar Bulbasaur:");
                l = scanner.nextInt();
                c = scanner.nextInt();
                tabuleiro.posicionarPokemon(l, c, p2, true);

                System.out.println("Digite linha e coluna para posicionar Diglett:");
                l = scanner.nextInt();
                c = scanner.nextInt();
                tabuleiro.posicionarPokemon(l, c, p3, true);

                System.out.println("Digite linha e coluna para posicionar Pikachu:");
                l = scanner.nextInt();
                c = scanner.nextInt();
                tabuleiro.posicionarPokemon(l, c, p4, true);

            } else {
                // Posicionamento automático
                tabuleiro.posicionarPokemon(1, 1, p1, true); // Água
                tabuleiro.posicionarPokemon(0, 5, p2, true); // Floresta
                tabuleiro.posicionarPokemon(4, 0, p3, true); // Terra
                tabuleiro.posicionarPokemon(5, 5, p4, true); // Elétrico
            }

        } catch (RegiaoInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // Mostra o tabuleiro
        System.out.println("Tabuleiro atual:");
        tabuleiro.exibir(); // método que já existe no Tabuleiro
        scanner.close();
    }
}
