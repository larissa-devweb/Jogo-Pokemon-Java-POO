package jogopokemon.testes;


import java.util.Random;
import java.util.Scanner;


import jogopokemon.Pokemon;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Terra;
import jogopokemon.pokemons.Floresta;

import javax.swing.*;
import java.awt.*;

public class JogoPokemon extends JFrame {

    private final int tamanho = 4;
    private final Tabuleiro tabuleiro;
    private final JButton[][] botoes;
    private final Treinador jogador;
    private final Treinador computador;

    public JogoPokemon() {
        // Inicializa tabuleiro e treinadores
        tabuleiro = new Tabuleiro(tamanho);
        jogador = new Treinador("Ash");
        computador = new Treinador("NPC");

        botoes = new JButton[tamanho][tamanho];

        setTitle("Pokémon POO");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(tamanho, tamanho));

        // Inicializa botões
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                botoes[i][j] = btn;
                int li = i, co = j;

                btn.addActionListener(e -> {
                    Pokemon p = tabuleiro.getPokemon(li, co);
                    if (p == null) {
                        JOptionPane.showMessageDialog(this, "Não há Pokémon nessa célula!");
                    } else if (p.isSelvagem()) {
                        JOptionPane.showMessageDialog(this, "Você encontrou um " + p.getTipo() + " chamado " + p.getNome() + "!");
                        p.setSelvagem(false);
                        jogador.adicionarPokemon(p);
                        atualizarBotoes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Pokémon do treinador: " + p.getTreinador().getNome());
                    }
                });

                add(btn);
            }
        }

        // Inicializa Pokémons selvagens
        inicializarPokemons();
        atualizarBotoes();
        setVisible(true);

        // Inicia menu console em outro thread
        new Thread(this::menuConsole).start();
    }

    private void inicializarPokemons() {
        try {
            tabuleiro.posicionarPokemonAleatoriamente(new Agua("Squirtle"));
            tabuleiro.posicionarPokemonAleatoriamente(new Floresta("Bulbasaur"));
            tabuleiro.posicionarPokemonAleatoriamente(new Terra("Sandshrew"));
            tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Pikachu"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = tabuleiro.getPokemon(i, j);
                botoes[i][j].setText(p == null ? "" : p.getTipo().substring(0, 1));
            }
        }
    }

    private void menuConsole() {
        Scanner sc = new Scanner(System.in);
        boolean fimJogo = false;

        while (!fimJogo && tabuleiro.temPokemonsSelvagens()) {
            System.out.println("\nTabuleiro atual:");
            tabuleiro.exibir();

            System.out.println("\nEscolha uma opção:");
            System.out.println("1) Buscar Pokémon");
            System.out.println("2) Batalhar Pokémon adversário");
            System.out.println("3) Ver Pokédex");
            System.out.println("4) Ver seu time");
            System.out.println("5) Encerrar jogo");
            System.out.print("Opção: ");
            int opc = sc.nextInt();

            switch (opc) {
                case 1:
                    System.out.print("Digite linha e coluna (0-" + (tamanho - 1) + "): ");
                    int l = sc.nextInt();
                    int c = sc.nextInt();

                    Pokemon p = tabuleiro.getPokemon(l, c);
                    if (p == null) {
                        System.out.println("Célula vazia!");
                    } else if (p.isSelvagem()) {
                        System.out.println("Encontrou Pokémon selvagem: " + p.getNome());
                        p.setSelvagem(false);
                        p.setTreinador(jogador);
                        jogador.adicionarPokemon(p);
                        System.out.println(p.getNome() + " capturado e adicionado ao seu time!");
                        atualizarBotoes();
                    } else {
                        System.out.println("Pokémon já pertence a um treinador.");
                    }
                    break;

                case 2:
                    if (jogador.getPokemons().isEmpty()) {
                        System.out.println("Você não tem Pokémon para batalhar!");
                        break;
                    }

                    System.out.println("Escolha Pokémon do seu time para batalhar:");
                    for (int i = 0; i < jogador.getPokemons().size(); i++) {
                        Pokemon pUser = jogador.getPokemons().get(i);
                        System.out.println(i + ") " + pUser.getNome() + " | HP: " + pUser.getHp());
                    }
                    int idx = sc.nextInt();
                    if (idx < 0 || idx >= jogador.getPokemons().size()) {
                        System.out.println("Escolha inválida!");
                        break;
                    }
                    Pokemon atacante = jogador.getPokemons().get(idx);

                    // Primeiro Pokémon adversário selvagem
                    Pokemon alvo = null;
                    outer:
                    for (int i = 0; i < tamanho; i++) {
                        for (int j = 0; j < tamanho; j++) {
                            Pokemon pTab = tabuleiro.getPokemon(i, j);
                            if (pTab != null && pTab.isSelvagem()) {
                                alvo = pTab;
                                break outer;
                            }
                        }
                    }
                    if (alvo == null) {
                        System.out.println("Não há Pokémon adversário para batalhar!");
                        break;
                    }

                    System.out.println("Batalha: " + atacante.getNome() + " VS " + alvo.getNome());
                    while (atacante.getHp() > 0 && alvo.getHp() > 0) {
                        atacante.atacar(alvo);
                        if (alvo.getHp() <= 0) {
                            System.out.println(alvo.getNome() + " foi nocauteado!");
                            atacante.aumentarExperiencia(10);
                            jogador.adicionarPontuacao(10);
                            tabuleiro.removerPokemon(alvo.getHp(), alvo.getHp());
                            break;
                        }
                        alvo.atacar(atacante);
                        if (atacante.getHp() <= 0) {
                            System.out.println(atacante.getNome() + " foi nocauteado!");
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.println(jogador.getPokedex().listarPokemons());
                    break;

                case 4:
                    System.out.println("Seu time:");
                    for (Pokemon pTime : jogador.getPokemons()) {
                        System.out.println("- " + pTime.getNome() + " | Tipo: " + pTime.getTipo() + " | HP: " + pTime.getHp() + " | Nível: " + pTime.getNivel());
                    }
                    System.out.println("Pontuação: " + jogador.getPontuacao());
                    break;

                case 5:
                    fimJogo = true;
                    System.out.println("Encerrando o jogo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        if (!tabuleiro.temPokemonsSelvagens()) {
            System.out.println("\nTodos os Pokémon selvagens foram capturados! Você venceu!");
        }
        sc.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JogoPokemon::new);
    }
}
