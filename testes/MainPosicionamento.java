package jogopokemon.testes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
MainDebugPosicionamento.java não é a main principal do projeto todo, é só teste isolado que eu criei pra 
* simular a Etapa 2: Posicionamento dos Pokémon.
Ela serve só pra:
 Testar o funcionamento do Tabuleiro
 Ver se o Pokémon está indo pra região certa
 Testar o modo debug
Lançar a RegiaoInvalidaException quando deve
 */
import jogopokemon.*;
import jogopokemon.pokemons.*;

import java.util.Scanner;

// Classe principal para testar o posicionamento de Pokémons no tabuleiro
public class MainPosicionamento {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Leitor de entrada do usuário

        int tamanho = 6; // Tamanho do tabuleiro (exemplo 6x6)
        Tabuleiro tabuleiro = new Tabuleiro(tamanho); // Cria o tabuleiro

        // Pergunta ao usuário se ele quer ativar o modo debug
        System.out.print("Modo debug (true/false)? ");
        boolean debug = scanner.nextBoolean();

        try {
            // Cria pokémons de cada tipo
            Pokemon p1 = new Agua("Squirtle");
            Pokemon p2 = new Floresta("Bulbasaur");
            Pokemon p3 = new Terra("Diglett");
            Pokemon p4 = new Eletrico("Pikachu");

            if (debug) {
                // Se for modo debug, o usuário escolhe as posições manualmente
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
                // Posiciona automaticamente em regiões válidas se não for debug
                tabuleiro.posicionarPokemon(1, 1, p1, true); // Região de água
                tabuleiro.posicionarPokemon(0, 5, p2, true); // Região de floresta
                tabuleiro.posicionarPokemon(4, 0, p3, true); // Região de terra
                tabuleiro.posicionarPokemon(5, 5, p4, true); // Região de elétrico
            }

        } catch (RegiaoInvalidaException e) {
            // Caso o Pokémon seja colocado em lugar inválido, mostra o erro
            System.out.println("Erro: " + e.getMessage());
        }

        // Mostra o tabuleiro no estado atual
        System.out.println("Tabuleiro atual:");
        tabuleiro.mostrarTabuleiro(debug);
    }
}
