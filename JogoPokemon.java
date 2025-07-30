/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jogopokemon;

/**
 *
MAIN PRINCIPAL
*   // Mostrar tela de boas-vindas
        // Perguntar se quer carregar, posicionar ou iniciar aleatório
        // Criar o tabuleiro
        // Lidar com jogadas, interface gráfica, etc
        //Tera: 
        //a GUI (tela de entrada, tabuleiro clicável)

//o fluxo de jogo (posicionamento, jogadas, batalhas, pontuação)

//controle entre jogador e computador (com thread, talvez)

//e depois: salvar/carregar jogo
 */
import java.util.ArrayList;
import java.util.Scanner;

public class JogoPokemon {
    public static void main(String[] args) throws RegiaoInvalidaException {
        try (Scanner scanner = new Scanner(System.in)) {
            Tabuleiro tabuleiro = new Tabuleiro(6); // Tabuleiro 6x6
            Treinador treinadorJogador = new Treinador("Ash"); // Treinador inicial
            int dicasRestantes = 3; // Dicas disponíveis por jogo
            boolean jogoAtivo = true;
            
            // Posiciona alguns Pokémon selvagens no início (modo debug)
            tabuleiro.posicionarPokemon(1, 1, new PokemonAgua("Squirtle"), true);
            tabuleiro.posicionarPokemon(2, 3, new PokemonEletrico("Pikachu"), true);
            tabuleiro.posicionarPokemon(4, 2, new PokemonFloresta("Chikorita"), true);
            
            while (jogoAtivo) {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1 - Posicionar Pokémon manualmente");
                System.out.println("2 - Posicionar Pokémon aleatoriamente (modo debug)");
                System.out.println("3 - Escolher Pokémon selvagem para capturar");
                System.out.println("4 - Usar dica (restantes: " + dicasRestantes + ")");
                System.out.println("5 - Iniciar batalha entre dois Pokémons");
                System.out.println("6 - Ver mochila do treinador");
                System.out.println("7 - Salvar mochila");
                System.out.println("8 - Carregar mochila");
                System.out.println("9 - Salvar tabuleiro");
                System.out.println("10 - Sair");
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                
                switch (opcao) {
                    case 1:
                        Pokemon pManual = new PokemonFloresta("Bulbasaur");
                        tabuleiro.posicionarPokemon(3, 2, pManual, true);
                        System.out.println("Bulbasaur posicionado em [3,2].");
                        break;
                        
                    case 2:
                        tabuleiro.posicionarPokemon(0, 0, new PokemonAgua("Totodile"), true);
                        tabuleiro.posicionarPokemon(0, 5, new PokemonEletrico("Magnemite"), true);
                        System.out.println("Pokémons posicionados aleatoriamente.");
                        break;
                        
                    case 3:
                        ArrayList<Pokemon> selvagens = tabuleiro.listarPokemonsSelvagens();
                        if (selvagens.isEmpty()) {
                            System.out.println("Nenhum Pokémon selvagem disponível.");
                        } else {
                            System.out.println("Escolha um Pokémon para capturar:");
                            for (int i = 0; i < selvagens.size(); i++) {
                                Pokemon p = selvagens.get(i);
                                int[] pos = tabuleiro.getPosicaoDoPokemon(p);
                                System.out.println(i + " - " + p.getNome() + " (" + p.getTipo() + ") em [" + pos[0] + "," + pos[1] + "]");
                            }
                            System.out.print("Número do Pokémon: ");
                            int escolha = scanner.nextInt();
                            if (escolha >= 0 && escolha < selvagens.size()) {
                                Pokemon escolhido = selvagens.get(escolha);
                                int[] pos = tabuleiro.getPosicaoDoPokemon(escolhido);
                                CapturaSelvagem.tentarCaptura(escolhido, tabuleiro, pos[0], pos[1], treinadorJogador);
                            } else {
                                System.out.println("Escolha inválida.");
                            }
                        }
                        break;
                        
                    case 4:
                        if (dicasRestantes > 0) {
                            System.out.print("Deseja dica da linha (L) ou coluna (C)? ");
                            String tipo = scanner.next().toUpperCase();
                            switch (tipo) {
                                case "L":
                                {
                                    System.out.print("Digite a linha (0 a 5): ");
                                    int linha = scanner.nextInt();
                                    boolean achou = false;
                                    for (int j = 0; j < tabuleiro.tamanho; j++) {
                                        Pokemon p = tabuleiro.getPokemon(linha, j);
                                        if (p != null && p.isSelvagem()) {
                                            achou = true;
                                            break;
                                        }
                                    }
                                    System.out.println(achou ? "Há Pokémon nessa linha!" : "Não há Pokémon nessa linha.");
                                    break;
                                }
                                case "C":
                                {
                                    System.out.print("Digite a coluna (0 a 5): ");
                                    int coluna = scanner.nextInt();
                                    boolean achou = false;
                                    for (int i = 0; i < tabuleiro.tamanho; i++) {
                                        Pokemon p = tabuleiro.getPokemon(i, coluna);
                                        if (p != null && p.isSelvagem()) {
                                            achou = true;
                                            break;
                                        }
                                    }
                                    System.out.println(achou ? "Há Pokémon nessa coluna!" : "Não há Pokémon nessa coluna.");
                                    break;
                                }
                                default:
                                    System.out.println("Opção inválida.");
                                    break;
                            }
                            dicasRestantes--;
                        } else {
                            System.out.println("Você já usou suas 3 dicas.");
                        }
                        break;
                        
                    case 5:
                        System.out.print("Nome do Pokémon 1: ");
                        String nome1 = scanner.next();
                        System.out.print("Nome do Pokémon 2: ");
                        Pokemon p1 = new PokemonAgua(nome1);
                        boolean batalhando = true;
                        while (batalhando) {
                            System.out.println("1 - Atacar");
                            System.out.println("2 - Fugir");
                            int acao = scanner.nextInt();
                            switch (acao) {
                                case 1:
                                    int dano = p1.calcularDano();
                                    System.out.println(p1.getNome() + " causou " + dano + " de dano!");
                                    batalhando = false;
                                    break;
                                case 2:
                                    System.out.println(p1.getNome() + " fugiu da batalha!");
                                    batalhando = false;
                                    break;
                                default:
                                    System.out.println("Opção inválida.");
                                    break;
                            }
                        }
                        break;
                        
                    case 6:
                        treinadorJogador.mostrarMochila();
                        break;
                        
                    case 7:
                        GerenciadorArquivos.salvarMochila(treinadorJogador);
                        break;
                        
                    case 8:
                        GerenciadorArquivos.carregarMochila(treinadorJogador);
                        break;
                        
                    case 9:
                        GerenciadorArquivos.salvarTabuleiro(tabuleiro);
                        break;
                        
                    case 10:
                        System.out.println("Encerrando o jogo...");
                        jogoAtivo = false;
                        break;
                        
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
                
                // Mostra tabuleiro após cada ação
                tabuleiro.exibir();
            }
        } // Tabuleiro 6x6
    }
}
