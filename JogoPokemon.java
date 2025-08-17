package jogopokemon;

import java.util.Scanner;
import jogopokemon.pokemons.*;

public class JogoPokemon {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Criando tabuleiro e treinador
        Tabuleiro tabuleiro = new Tabuleiro(5);
        Treinador jogador = new Treinador("Ash");

        // Pokémon inicial do jogador
        Pokemon inicial = new Agua("Squirtle", false);
        jogador.adicionarPokemon(inicial);
        inicial.setTreinador(jogador);
        tabuleiro.posicionarPokemonAleatoriamente(inicial);

        // Pokémon selvagens iniciais
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Pikachu", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Floresta("Bulbasaur", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck", true));

        boolean sair = false;

        while (!sair) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Explorar tabuleiro");
            System.out.println("2 - Ver mochila");
            System.out.println("3 - Salvar jogo");
            System.out.println("4 - Carregar jogo");
            System.out.println("5 - Batalha entre 2 Pokémons");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opc = sc.nextLine();

            switch (opc) {
                case "1":
                    explorarTabuleiro(sc, tabuleiro, jogador);
                    break;
                case "2":
                    mostrarMochila(jogador);
                    aguardarEnter(sc);
                    break;
                case "3":
                    GerenciadorArquivos.salvarMochila(jogador);
                    GerenciadorArquivos.salvarTabuleiro(tabuleiro);
                    System.out.println("Jogo salvo!");
                    aguardarEnter(sc);
                    break;
                case "4":
                    GerenciadorArquivos.carregarMochila(jogador);
                    GerenciadorArquivos.carregarTabuleiro(tabuleiro);
                    System.out.println("Jogo carregado!");
                    aguardarEnter(sc);
                    break;
                case "5":
                    batalha2PokemonsMenu(sc);
                    break;
                case "0":
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    aguardarEnter(sc);
            }
        }
        System.out.println("Fim do jogo!");
    }

    // -------------------- MÉTODOS AUXILIARES --------------------

    private static void explorarTabuleiro(Scanner sc, Tabuleiro tabuleiro, Treinador jogador) {
        try {
            System.out.print("Digite a linha (0-" + (tabuleiro.getTamanho() - 1) + "): ");
            int linha = Integer.parseInt(sc.nextLine());
            System.out.print("Digite a coluna (0-" + (tabuleiro.getTamanho() - 1) + "): ");
            int coluna = Integer.parseInt(sc.nextLine());

            Pokemon p = tabuleiro.getPokemon(linha, coluna);

            if (p == null) {
                System.out.println("Não há Pokémon nesta posição.");
                aguardarEnter(sc);
                return;
            }

            boolean sairExplorar = false;
            while (!sairExplorar) {
                // Caso seja Pokémon selvagem
                if (p.isSelvagem()) {
                    System.out.println("Encontrou Pokémon selvagem: " + p.getNome());
                    System.out.println("1 - Tentar capturar");
                    System.out.println("0 - Voltar");
                    int escolha = Integer.parseInt(sc.nextLine());
                    switch (escolha) {
                        case 1:
                            boolean capturou = CapturaSelvagem.tentarCaptura(p, tabuleiro, linha, coluna, jogador);
                            if (capturou) {
                                tabuleiro.removerPokemon(linha, coluna);
                                System.out.println("Você capturou " + p.getNome() + " com sucesso!");
                            } else {
                                System.out.println(p.getNome() + " escapou!");
                            }
                            sairExplorar = true;
                            break;
                        case 0:
                            sairExplorar = true;
                            break;
                        default:
                            System.out.println("Escolha inválida!");
                    }
                    aguardarEnter(sc);

                    // Caso seja Pokémon de outro treinador
                } else {
                    System.out.println("Encontrou Pokémon de outro treinador: " + p.getNome());
                    if (jogador.getPokemons().isEmpty()) {
                        System.out.println("Você não possui Pokémon para batalhar!");
                        sairExplorar = true;
                        aguardarEnter(sc);
                    } else {
                        System.out.println("1 - Batalhar");
                        System.out.println("0 - Voltar");
                        int escolha = Integer.parseInt(sc.nextLine());
                        switch (escolha) {
                            case 1:
                                mostrarMochila(jogador);
                                System.out.print("Escolha seu Pokémon pelo número: ");
                                int idx = Integer.parseInt(sc.nextLine());
                                if (idx >= 0 && idx < jogador.getPokemons().size()) {
                                    Pokemon meu = jogador.getPokemons().get(idx);
                                    Batalha2Pokemons.iniciarBatalha(meu, p, sc);
                                    if (p.getHp() <= 0) tabuleiro.removerPokemon(linha, coluna);
                                } else {
                                    System.out.println("Escolha inválida!");
                                }
                                sairExplorar = true;
                                aguardarEnter(sc);
                                break;
                            case 0:
                                sairExplorar = true;
                                break;
                            default:
                                System.out.println("Escolha inválida!");
                                aguardarEnter(sc);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida!");
            aguardarEnter(sc);
        }
    }


    private static void mostrarMochila(Treinador jogador) {
        System.out.println("\n--- Mochila de " + jogador.getNome() + " ---");
        if (jogador.getPokemons().isEmpty()) {
            System.out.println("Mochila vazia.");
            return;
        }
        for (int i = 0; i < jogador.getPokemons().size(); i++) {
            Pokemon p = jogador.getPokemons().get(i);
            System.out.println(i + " - " + p.getNome() + " | Tipo: " + p.getTipo() + " | HP: " + p.getHp());
        }
    }

    // Pausa para pressionar ENTER
    private static void aguardarEnter(Scanner sc) {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    // Menu específico para batalha entre 2 Pokémons
    private static void batalha2PokemonsMenu(Scanner sc) {
        System.out.println("=== Batalha entre 2 Pokémons ===");

        // Escolha do seu Pokémon
        System.out.println("Escolha o tipo do seu Pokémon:");
        System.out.println("1 - Água");
        System.out.println("2 - Terra");
        System.out.println("3 - Elétrico");
        System.out.println("4 - Floresta");
        System.out.print("Opção: ");
        int tipoMeu = Integer.parseInt(sc.nextLine());

        Pokemon meuPokemon;
        switch (tipoMeu) {
            case 1: meuPokemon = new Agua("Meu Água"); break;
            case 2: meuPokemon = new Terra("Meu Terra"); break;
            case 3: meuPokemon = new Eletrico("Meu Elétrico"); break;
            case 4: meuPokemon = new Floresta("Meu Floresta"); break;
            default:
                System.out.println("Opção inválida, usando Água por padrão.");
                meuPokemon = new Agua("Meu Água");
        }

        // Escolha do Pokémon adversário
        System.out.println("Escolha o tipo do Pokémon adversário:");
        System.out.println("1 - Água");
        System.out.println("2 - Terra");
        System.out.println("3 - Elétrico");
        System.out.println("4 - Floresta");
        System.out.print("Opção: ");
        int tipoAdversario = Integer.parseInt(sc.nextLine());

        Pokemon inimigo;
        switch (tipoAdversario) {
            case 1: inimigo = new Agua("Inimigo Água"); break;
            case 2: inimigo = new Terra("Inimigo Terra"); break;
            case 3: inimigo = new Eletrico("Inimigo Elétrico"); break;
            case 4: inimigo = new Floresta("Inimigo Floresta"); break;
            default:
                System.out.println("Opção inválida, usando Água por padrão.");
                inimigo = new Agua("Inimigo Água");
        }

        // Inicia a batalha
        Batalha2Pokemons.iniciarBatalha(meuPokemon, inimigo, sc);
        aguardarEnter(sc);
    }
}
