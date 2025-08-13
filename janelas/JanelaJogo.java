package jogopokemon.janelas;

import jogopokemon.CapturaSelvagem;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Pokemon;
import javax.swing.*;
import java.awt.*;

public class JanelaJogo extends JFrame {
    private final JButton[][] botoes;
    private final Tabuleiro tabuleiro;
    private final Treinador treinador;

    public JanelaJogo(Tabuleiro tabuleiro, Treinador treinador) {
        super("Pokémon GUI Básico");

        this.tabuleiro = tabuleiro;
        this.treinador = treinador;

        int tamanho = tabuleiro.getTamanho();
        botoes = new JButton[tamanho][tamanho];

        setLayout(new GridLayout(tamanho, tamanho));

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

    private void moverTreinador(int novaLinha, int novaColuna) {
        treinador.setLinha(novaLinha);
        treinador.setColuna(novaColuna);

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

    public void atualizarTabuleiro() {
        for (int linha = 0; linha < tabuleiro.getTamanho(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getTamanho(); coluna++) {
                if (linha == treinador.getLinha() && coluna == treinador.getColuna()) {
                    botoes[linha][coluna].setText(treinador.getNome());
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
}
