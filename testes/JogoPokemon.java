package jogopokemon.testes;

import jogopokemon.*;
import jogopokemon.janelas.Jogo;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;

import java.util.ArrayList;
import java.util.Scanner;

public class JogoPokemon {

    public static void main(String[] args) throws RegiaoInvalidaException {
        Scanner scanner = new Scanner(System.in);

        // Cria tabuleiro e treinador
        Tabuleiro tabuleiro = new Tabuleiro(6);
        Treinador treinadorJogador = new Treinador("Ash");
        Jogo jogo = new Jogo(tabuleiro, treinadorJogador);

        int dicasRestantes = 3;
        boolean jogoAtivo = true;

        // Posiciona Pokémons selvagens em regiões válidas
        try {
            tabuleiro.posicionarPokemon(0, 0, new Agua("Squirtle", true), true);
            tabuleiro.posicionarPokemon(0, 5, new Eletrico("Pikachu", true), true);
            tabuleiro.posicionarPokemon(5, 2, new Floresta("Chikorita", true), true);
        } catch (RegiaoInvalidaException e) {
            System.out.println("Erro ao posicionar Pokémon: " + e.getMessage());
        }

        // Inicia thread de movimento automático
        MovimentoAutomatico threadMovimento = new MovimentoAutomatico(tabuleiro, jogo);
        threadMovimento.start();

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
                    Pokemon pManual = new Floresta("Bulbasaur", false);
                    try {
                        tabuleiro.posicionarPokemon(1, 1, pManual, true);
                        System.out.println("Bulbasaur posicionado em [1,1].");
                    } catch (RegiaoInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        tabuleiro.posicionarPokemon(2, 2, new Agua("Totodile", false), true);
                        tabuleiro.posicionarPokemon(3, 3, new Eletrico("Magnemite", false), true);
                        System.out.println("Pokémons posicionados aleatoriamente.");
                    } catch (RegiaoInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    ArrayList<Pokemon> selvagens = tabuleiro.listarPokemonsSelvagens();
                    if (selvagens.isEmpty()) {
                        System.out.println("Nenhum Pokémon selvagem disponível.");
                    } else {
                        System.out.println("Escolha um Pokémon para capturar:");
                        for (int i = 0; i < selvagens.size(); i++) {
                            Pokemon p = selvagens.get(i);
                            int[] pos = tabuleiro.getPosicao(p);
                            System.out.println(i + " - " + p.getNome() + " (" + p.getTipo() + ") em [" + pos[0] + "," + pos[1] + "]");
                        }
                        System.out.print("Número do Pokémon: ");
                        int escolha = scanner.nextInt();
                        if (escolha >= 0 && escolha < selvagens.size()) {
                            Pokemon escolhido = selvagens.get(escolha);
                            int[] pos = tabuleiro.getPosicao(escolhido);
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
                        if (tipo.equals("L")) {
                            System.out.print("Digite a linha (0 a 5): ");
                            int linha = scanner.nextInt();
                            boolean achou = false;
                            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                                Pokemon p = tabuleiro.getPokemon(linha, j);
                                if (p != null && p.isSelvagem()) {
                                    achou = true;
                                    break;
                                }
                            }
                            System.out.println(achou ? "Há Pokémon nessa linha!" : "Não há Pokémon nessa linha.");
                        } else if (tipo.equals("C")) {
                            System.out.print("Digite a coluna (0 a 5): ");
                            int coluna = scanner.nextInt();
                            boolean achou = false;
                            for (int i = 0; i < tabuleiro.getTamanho(); i++) {
                                Pokemon p = tabuleiro.getPokemon(i, coluna);
                                if (p != null && p.isSelvagem()) {
                                    achou = true;
                                    break;
                                }
                            }
                            System.out.println(achou ? "Há Pokémon nessa coluna!" : "Não há Pokémon nessa coluna.");
                        } else {
                            System.out.println("Opção inválida.");
                        }
                        dicasRestantes--;
                    } else {
                        System.out.println("Você já usou suas 3 dicas.");
                    }
                    break;

                case 5:
                    System.out.print("Nome do Pokémon 1: ");
                    String nome1 = scanner.next();
                    Pokemon p1 = new Agua(nome1, false);

                    System.out.print("Nome do Pokémon 2: ");
                    String nome2 = scanner.next();
                    Pokemon p2 = new Eletrico(nome2, false);

                    treinadorJogador.batalhar(p1, p2);
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
            }

            tabuleiro.exibir();
        }

        scanner.close();
    }
}
