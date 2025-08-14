package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.RegiaoInvalidaException;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;
import jogopokemon.pokemons.Pokemon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class JanelaPosicoes extends JFrame {
    private final Tabuleiro tabuleiro;
    private final JButton[][] botoesTabuleiro;
    private final ArrayList<JButton> botoesPokemons;
    private Pokemon pokemonEscolhido;
    private boolean modoDebug;

    public JanelaPosicoes() {
        super("Pokémon GUI Básico");

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

                final int linhaSelecionada = linha;
                final int colunaSelecionada = coluna;

                botao.addActionListener(e -> posicionarPokemon(linhaSelecionada, colunaSelecionada));
                add(botao, constraints);
            }

            constraints.gridy += 1;
        }

        constraints.insets = new Insets(10,3,10,3);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridy += 1;

        int inicioMeio = tamanho / 2 - 2;
        // inicioMeio se for maior ou igual a zero, se não 0
        constraints.gridx = Math.max(inicioMeio, 0);

        botoesPokemons = new ArrayList<>();

        JButton botao = new JButton("Debug");
        botao.addActionListener(e -> modoDebug = !modoDebug);
        add(botao, constraints);

        constraints.gridx += 1;
        botao = new JButton("Pikachu");

        {
            JButton finalBotao = botao;
            botao.addActionListener(e -> {
                escolherPokemon(new Eletrico("Pikachu"));
                finalBotao.setEnabled(true);
            });
        }

        botoesPokemons.add(botao);
        add(botao, constraints);

        constraints.gridx += 1;
        botao = new JButton("Bulbasaur");

        {
            JButton finalBotao = botao;
            botao.addActionListener(e -> {
                escolherPokemon(new Floresta("Bulbasaur"));
                finalBotao.setEnabled(true);
            });
        }

        botoesPokemons.add(botao);
        add(botao, constraints);

        constraints.gridx += 1;
        botao = new JButton("Squirtle");

        {
            JButton finalBotao = botao;
            botao.addActionListener(e -> {
                escolherPokemon(new Agua("Squirtle"));
                finalBotao.setEnabled(true);
            });
        }

        botoesPokemons.add(botao);
        add(botao, constraints);

        constraints.gridx += 1;
        botao = new JButton("Jogar");
        botao.addActionListener(e -> abrirJogo());
        add(botao, constraints);

        atualizarTabuleiro();

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void escolherPokemon(Pokemon pokemon) {
        for (JButton botao : botoesPokemons) {
            botao.setEnabled(false);
        }

        pokemonEscolhido = pokemon;
    }

    private void posicionarPokemon(int linha, int coluna) {
        try {
            tabuleiro.posicionarPokemon(linha, coluna, pokemonEscolhido, false);
            for (JButton botao : botoesPokemons) {
                if (!modoDebug)
                    botao.setEnabled(false);
                else
                    botao.setEnabled(!pokemonEscolhido.getNome().equals(botao.getText()));
            }

            botoesPokemons.removeIf(botao -> pokemonEscolhido.getNome().equals(botao.getText()));
        } catch (RegiaoInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Nenhum Pokemón selecionado");
        }

        pokemonEscolhido = null;
        atualizarTabuleiro();
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
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck"), true);
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Raichu"), true);

        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abrir jogo
        JanelaJogo jogo = new JanelaJogo(tabuleiro, new Treinador("Ash"));

        // Inicia thread de movimento automático
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, jogo);
        movimento.start();
    }
}
