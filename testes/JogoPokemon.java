package jogopokemon.testes;

import jogopokemon.*;
import jogopokemon.janelas.JanelaJogo;
import jogopokemon.pokemons.Pokemon;
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
        JanelaJogo janelaJogo = new JanelaJogo(tabuleiro, treinadorJogador);

        int dicasRestantes = 3; // máximo de dicas
        boolean jogoAtivo = true;

        // Posiciona alguns Pokémons selvagens no início
        tabuleiro.posicionarPokemon(1, 1, new Agua("Squirtle"));
        tabuleiro.posicionarPokemon(2, 3, new Eletrico("Pikachu"));
        tabuleiro.posicionarPokemon(4, 2, new Floresta("Chikorita"));

        // Inicia thread para movimentar Pokémon automaticamente
        MovimentoAutomatico threadMovimento = new MovimentoAutomatico(tabuleiro, janelaJogo);
        threadMovimento.start();

        // Loop principal do jogo
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
                    Pokemon pManual = new Floresta("Bulbasaur");
                    tabuleiro.posicionarPokemon(3, 2, pManual);
                    System.out.println("Bulbasaur posicionado em [3,2].");
                    break;

                case 2:
                    tabuleiro.posicionarPokemon(0, 0, new Agua("Totodile"));
                    tabuleiro.posicionarPokemon(0, 5, new Eletrico("Magnemite"));
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
                    Pokemon p1 = new Agua(nome1);
                    boolean batalhando = true;
                    while (batalhando) {
                        System.out.println("1 - Atacar");
                        System.out.println("2 - Fugir");
                        int acao = scanner.nextInt();
                        if (acao == 1) {
                            int dano = p1.calcularDano();
                            System.out.println(p1.getNome() + " causou " + dano + " de dano!");
                            batalhando = false;
                        } else if (acao == 2) {
                            System.out.println(p1.getNome() + " fugiu da batalha!");
                            batalhando = false;
                        } else {
                            System.out.println("Opção inválida.");
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

        scanner.close();
    }
}
