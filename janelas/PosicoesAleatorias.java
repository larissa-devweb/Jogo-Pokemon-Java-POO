package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;
import jogopokemon.pokemons.Pokemon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PosicoesAleatorias extends JFrame {
    private final Tabuleiro tabuleiro;
    private final JButton[][] botoesTabuleiro;

    public PosicoesAleatorias() {
        super("Pokémons distribuídos");

        Tabuleiro tabuleiro = new Tabuleiro(5);
        this.tabuleiro = tabuleiro;

        int tamanho = tabuleiro.getTamanho();
        botoesTabuleiro = new JButton[tamanho][tamanho];

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            for (int coluna = 0; coluna < tamanho; coluna++) {
                JButton botao = new JButton();
                botoesTabuleiro[linha][coluna] = botao;
                add(botao, constraints);
            }

            constraints.gridy += 1;
        }

        constraints.insets = new Insets(10,3,10,3);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridy += 1;

        constraints.gridx += tamanho;
        JButton botao = new JButton("Jogar");
        botao.addActionListener(e -> abrirJogo());
        add(botao, constraints);

        // TODO: Adicionar um na mochila do jogador e outro na do computador
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Pikachu"));
        tabuleiro.posicionarPokemonAleatoriamente(new Floresta("Bulbasaur", false));

        // Selvagens
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Squirtle"));
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck"));
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Raichu"));

        atualizarTabuleiro();

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void atualizarTabuleiro() {
        for (int linha = 0; linha < tabuleiro.getTamanho(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getTamanho(); coluna++) {
                Pokemon p = tabuleiro.getPokemon(linha, coluna);
                if (p != null) {
                    botoesTabuleiro[linha][coluna].setText(p.getNome());
                } else {
                    botoesTabuleiro[linha][coluna].setText("Vazio");
                }
            }
        }
    }

    private void abrirJogo() {
        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abrir jogo
        Jogo jogo = new Jogo(tabuleiro, new Treinador("Ash"));

        // Inicia thread de movimento automático
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, jogo);
        movimento.start();
    }
}
