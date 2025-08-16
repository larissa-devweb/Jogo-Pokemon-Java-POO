package jogopokemon.janelas;

import jogopokemon.*;
import jogopokemon.pokemons.*;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Jogo extends JFrame {
    private final int tamanho = 6; // tamanho do tabuleiro
    private final Tabuleiro tabuleiro;
    private final Treinador jogador;
    private final Treinador computador;
    private final JButton[][] botoes;
    private final JLabel status;

    public Jogo(Tabuleiro tabuleiro, Treinador ash) {
        super("Jogo Pokémon - POO 2025/1");
        this.tabuleiro = new Tabuleiro(tamanho);
        this.jogador = new Treinador("Ash");
        this.computador = new Treinador("Computador");
        this.botoes = new JButton[tamanho][tamanho];
        this.status = new JLabel("Sua vez!");

        inicializarPokemons();
        inicializarGUI();
        iniciarMovimentoComputador();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarPokemons() {
        // Pokémon inicial do jogador
        Pokemon p1 = new Agua("Squirtle", false);
        jogador.adicionarPokemon(p1);
        tabuleiro.posicionarPokemonAleatoriamente(p1);
        p1.setTreinador(jogador);

        // Pokémon do computador
        Pokemon pc1 = new Terra("Sandshrew", false);
        computador.adicionarPokemon(pc1);
        tabuleiro.posicionarPokemonAleatoriamente(pc1);
        pc1.setTreinador(computador);

        // Selvagens
        Random rnd = new Random();
        String[] tipos = {"agua", "floresta", "terra", "eletrico"};
        for (int i = 0; i < 8; i++) {
            String tipo = tipos[rnd.nextInt(tipos.length)];
            Object JogoPokemon = null;
            //Pokemon ps = JogoPokemon.criarPokemon(tipo, tipo + i, true);
          //  tabuleiro.posicionarPokemonAleatoriamente(ps);
        }
    }

    private void inicializarGUI() {
        setLayout(new BorderLayout());
        JPanel panelTabuleiro = new JPanel(new GridLayout(tamanho, tamanho));
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                JButton btn = new JButton("");
                final int linha = i;
                final int coluna = j;
                btn.addActionListener(e -> clicarCelula(linha, coluna));
                botoes[i][j] = btn;
                panelTabuleiro.add(btn);
            }
        }

        add(panelTabuleiro, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        atualizarTabuleiro();
    }

    private void clicarCelula(int linha, int coluna) {
        Pokemon p = tabuleiro.getPokemon(linha, coluna);
        if (p == null) {
            status.setText("Nenhum Pokémon aqui!");
            botoes[linha][coluna].setText("-");
            return;
        }

        if (p.isSelvagem()) {
            // Captura Pokémon
            p.setSelvagem(false);
            jogador.adicionarPokemon(p);
            p.setTreinador(jogador);
            jogador.adicionarPontuacao(10);
            status.setText("Você capturou " + p.getNome() + "! +10 pontos");
        } else {
            // Batalha
            Pokemon meu = jogador.getPokemons().get(0);
            Batalha2Pokemons.iniciarBatalha(meu, p);
            status.setText("Batalha realizada!");
        }

        atualizarTabuleiro();
        verificarFimJogo();
    }

    public void atualizarTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = tabuleiro.getPokemon(i, j);
                if (p == null) botoes[i][j].setText("");
                else if (!p.isSelvagem()) botoes[i][j].setText(p.getNome().substring(0,1));
                else botoes[i][j].setText("?");
            }
        }
    }

    private void iniciarMovimentoComputador() {
        new Thread(() -> {
            Random rnd = new Random();
            while (tabuleiro.temPokemonsSelvagens()) {
                try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
                int l = rnd.nextInt(tamanho);
                int c = rnd.nextInt(tamanho);
                Pokemon p = tabuleiro.getPokemon(l, c);
                if (p != null && p.isSelvagem()) {
                    p.setSelvagem(false);
                    computador.adicionarPokemon(p);
                    computador.adicionarPontuacao(10);
                    atualizarTabuleiro();
                }
            }
        }).start();
    }

    private void verificarFimJogo() {
        if (!tabuleiro.temPokemonsSelvagens()) {
            String vencedor = (jogador.getPontuacao() >= computador.getPontuacao()) ? jogador.getNome() : computador.getNome();
            JOptionPane.showMessageDialog(this, "Fim de jogo! Vencedor: " + vencedor);
        }
    }
}
