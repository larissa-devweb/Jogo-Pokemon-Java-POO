package jogopokemon;

import javax.swing.*;
import java.awt.*;

public class PokemonGUI extends JFrame {

    private final JButton[][] botoes;
    private final Tabuleiro tabuleiro;
    private final Treinador treinador;
    private int treinadorLinha = 0;
    private int treinadorColuna = 0;

    public PokemonGUI(Tabuleiro tabuleiro, Treinador treinador) {
        super("Pokémon GUI Básico");

        this.tabuleiro = tabuleiro;
        this.treinador = treinador;

        int tamanho = tabuleiro.tamanho;
        botoes = new JButton[tamanho][tamanho];

        setLayout(new GridLayout(tamanho, tamanho));

        // cria botões para cada célula
        for (int linha = 0; linha < tamanho; linha++) {
            for (int coluna = 0; coluna < tamanho; coluna++) {
                JButton botao = new JButton();
                botoes[linha][coluna] = botao;

                final int linhaSelecionada = linha;
                final int colunaSelecionada = coluna;

                botao.addActionListener(e -> moverTreinador(linhaSelecionada, colunaSelecionada));

                add(botao);
            }
        }

        atualizarTabuleiro();

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void moverTreinador(int novaLinha, int novaColuna) throws RegiaoInvalidaException {
        treinadorLinha = novaLinha;
        treinadorColuna = novaColuna;

        Pokemon p = tabuleiro.getPokemon(novaLinha, novaColuna);

        if (p != null && p.isSelvagem()) {
            boolean capturado = CapturaSelvagem.tentarCaptura(
                    p, tabuleiro, novaLinha, novaColuna, treinador
            );

            if (capturado) {
                try {
                    tabuleiro.removerPokemon(novaLinha, novaColuna);
                    JOptionPane.showMessageDialog(this,
                            p.getNome() + " foi capturado!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao remover Pokémon: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        p.getNome() + " escapou!");
            }
        }

        atualizarTabuleiro();
    }

    private void atualizarTabuleiro() {
        for (int linha = 0; linha < tabuleiro.tamanho; linha++) {
            for (int coluna = 0; coluna < tabuleiro.tamanho; coluna++) {
                if (linha == treinadorLinha && coluna == treinadorColuna) {
                    botoes[linha][coluna].setText("Treinador");
                } else {
                    Pokemon p = tabuleiro.getPokemon(linha, coluna);
                    if (p != null) {
                        botoes[linha][coluna].setText(p.getNome());
                    } else {
                        botoes[linha][coluna].setText("Vazio");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Tabuleiro tab = new Tabuleiro(5);
        Treinador ash = new Treinador("Ash");

        try {
            tab.posicionarPokemon(2, 2, new PokemonAgua("Squirtle"), true);
            tab.posicionarPokemon(4, 1, new PokemonEletrico("Pikachu"), true);
        } catch (RegiaoInvalidaException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao posicionar Pokémon: " + e.getMessage());
        }

        new PokemonGUI(tab, ash);
    }
}
