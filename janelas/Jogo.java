package jogopokemon.janelas;

import jogopokemon.*;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Jogo extends JFrame {
    private final Tabuleiro tabuleiro;
    private final Treinador jogador;
    private final Treinador computador;
    private final JButton[][] botoesTabuleiro;

    public Jogo(Tabuleiro tabuleiro, Treinador jogador) {
        super("Pokémon - Jogo");

        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.computador = new Treinador("Computador");

        int tamanho = tabuleiro.getTamanho();
        botoesTabuleiro = new JButton[tamanho][tamanho];

        setLayout(new GridLayout(tamanho, tamanho));

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                JButton botao = new JButton("Vazio");
                botoesTabuleiro[i][j] = botao;
                final int linha = i;
                final int coluna = j;

                botao.addActionListener(e -> clicarCelula(linha, coluna));

                add(botao);
            }
        }

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        atualizarTabuleiro();

        // Inicia Thread do computador
        JogadorComputador cpu = new JogadorComputador(tabuleiro, computador, (IAtaque) jogador);
        cpu.start();
    }

    private void clicarCelula(int linha, int coluna) {
        Pokemon p = tabuleiro.getPokemon(linha, coluna);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Nenhum Pokémon nesta célula.");
            return;
        }

        if (p.isSelvagem()) {
            JOptionPane.showMessageDialog(this, "Pokémon selvagem encontrado: " + p.getNome());
            jogador.adicionarPokemon(p);
            tabuleiro.removerPokemon(linha, coluna);
            checkFimJogo();
        } else {
            JOptionPane.showMessageDialog(this, "Este Pokémon pertence a outro treinador!");
            // Aqui você poderia implementar batalha
        }

        atualizarTabuleiro();
    }

    public void atualizarTabuleiro() {
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                Pokemon p = tabuleiro.getPokemon(i,j);
                if (p != null && !p.isSelvagem() && p.getTreinador() == jogador)
                    botoesTabuleiro[i][j].setText(p.getNome());
                else if (p != null)
                    botoesTabuleiro[i][j].setText("???");
                else
                    botoesTabuleiro[i][j].setText("Vazio");
            }
        }
    }

    private void checkFimJogo() {
        if (!tabuleiro.temPokemonsSelvagens()) {
            String vencedor;
            if (jogador.getPontuacao() >= computador.getPontuacao())
                vencedor = jogador.getNome();
            else
                vencedor = computador.getNome();

            JOptionPane.showMessageDialog(this, "Fim de jogo! Vencedor: " + vencedor);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    // Inicializa Pokémon de Ash + selvagens
    public static void inicializar(Tabuleiro tabuleiro, Treinador jogador) {
        // Ash começa com 1 Pokémon
        Pokemon inicial = new Eletrico("Pikachu", false);
        jogador.adicionarPokemon(inicial);
        tabuleiro.posicionarPokemonAleatoriamente(inicial);

        // Selvagens
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Squirtle", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Raichu", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Floresta("Bulbasaur", true));
    }

    // Main para teste
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(5);
        Treinador jogador = new Treinador("Ash");
        inicializar(tabuleiro, jogador);

        new Jogo(tabuleiro, jogador);
    }
}
