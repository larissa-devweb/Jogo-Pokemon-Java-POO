package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.Pokemon;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;
import jogopokemon.CapturaSelvagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EscolherPosicoes extends JFrame {
    private final Tabuleiro tabuleiro;
    private final Treinador jogador;
    private final JButton[][] botoesTabuleiro;
    private final ArrayList<JButton> botoesPokemons;
    private Pokemon pokemonEscolhido;
    private boolean modoDebug;
    private final JLabel painelPontuacao;

    public EscolherPosicoes() {
        super("Distribuir Pokémons");

        this.jogador = new Treinador("Ash");
        this.tabuleiro = new Tabuleiro(5);
        int tamanho = tabuleiro.getTamanho();

        // Painel do tabuleiro
        JPanel painelGrid = new JPanel(new GridLayout(tamanho, tamanho, 4, 4));
        botoesTabuleiro = new JButton[tamanho][tamanho];

        for (int linha = 0; linha < tamanho; linha++) {
            for (int coluna = 0; coluna < tamanho; coluna++) {
                JButton botao = new JButton("Vazio");
                botoesTabuleiro[linha][coluna] = botao;
                final int l = linha;
                final int c = coluna;
                botao.addActionListener(e -> clicarTabuleiro(l, c));
                painelGrid.add(botao);
            }
        }

        // Painel de controle e Pokémon
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        botoesPokemons = new ArrayList<>();

        // Botão Debug
        JButton btnDebug = new JButton("Debug");
        btnDebug.addActionListener(e -> {
            modoDebug = !modoDebug;
            btnDebug.setText(modoDebug ? "Debug ON" : "Debug");
        });
        painelBotoes.add(btnDebug);

        // Botões de Pokémon
        adicionarBotaoPokemon(painelBotoes, new Eletrico("Pikachu", false));
        adicionarBotaoPokemon(painelBotoes, new Floresta("Bulbasaur", false));
        adicionarBotaoPokemon(painelBotoes, new Agua("Squirtle", false));

        // Botão Jogar
        JButton btnJogar = new JButton("Jogar");
        btnJogar.addActionListener(e -> abrirJogo());
        painelBotoes.add(btnJogar);

        // Painel lateral com pontuação e Pokédex
        painelPontuacao = new JLabel();
        atualizarPainelPontuacao();

        JPanel painelLateral = new JPanel(new BorderLayout());
        painelLateral.add(painelPontuacao, BorderLayout.NORTH);

        // Monta a janela
        setLayout(new BorderLayout(8, 8));
        add(painelGrid, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        add(painelLateral, BorderLayout.EAST);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarBotaoPokemon(JPanel painel, Pokemon p) {
        JButton btn = new JButton(p.getNome());
        btn.addActionListener(e -> escolherPokemon(p));
        botoesPokemons.add(btn);
        painel.add(btn);
    }

    private void clicarTabuleiro(int linha, int coluna) {
        Pokemon p = tabuleiro.getPokemon(linha, coluna);
        if (p != null && p.isSelvagem()) {
            tentarCapturaPokemonSelvagem(p, linha, coluna);
        } else {
            posicionarPokemon(linha, coluna);
        }
    }

    private void escolherPokemon(Pokemon pokemon) {
        for (JButton b : botoesPokemons) {
            if (!b.getText().equals(pokemon.getNome()))
                b.setEnabled(false);
        }

        pokemonEscolhido = pokemon;
    }

    private void posicionarPokemon(int linha, int coluna) {
        try {
            if (pokemonEscolhido == null) {
                JOptionPane.showMessageDialog(this, "Nenhum Pokémon selecionado");
                return;
            }

            tabuleiro.posicionarPokemon(linha, coluna, pokemonEscolhido, true);

            for (JButton botao : botoesPokemons) {
                if (!modoDebug)
                    botao.setEnabled(false);
                else
                    botao.setEnabled(!pokemonEscolhido.getNome().equals(botao.getText()));
            }

            botoesPokemons.removeIf(botao -> pokemonEscolhido.getNome().equals(botao.getText()));

            // Adiciona Pokémon à Pokédex e pontuação
            jogador.adicionarPokemon(pokemonEscolhido);
            jogador.getPokedex().adicionarPokemon(pokemonEscolhido);
            jogador.adicionarPontuacao(10);
            atualizarPainelPontuacao();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao posicionar: " + e.getMessage());
        } finally {
            pokemonEscolhido = null;
            if (!modoDebug) {
                for (JButton b : botoesPokemons)
                    b.setEnabled(true);
            }
            atualizarTabuleiro();
        }
    }

    private void tentarCapturaPokemonSelvagem(Pokemon p, int linha, int coluna) {
        boolean capturou = CapturaSelvagem.tentarCaptura(p, tabuleiro, linha, coluna, jogador);
        if (capturou) {
            tabuleiro.removerPokemon(linha, coluna);
            jogador.adicionarPontuacao(10);
            jogador.getPokedex().adicionarPokemon(p);
            JOptionPane.showMessageDialog(this, "Você capturou " + p.getNome() + "!");
        } else {
            JOptionPane.showMessageDialog(this, p.getNome() + " escapou!");
        }

        atualizarPainelPontuacao();
        atualizarTabuleiro();
    }

    public void atualizarTabuleiro() {
        for (int linha = 0; linha < tabuleiro.getTamanho(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getTamanho(); coluna++) {
                Pokemon p = tabuleiro.getPokemon(linha, coluna);
                botoesTabuleiro[linha][coluna].setText(p != null ? p.getNome() : "Vazio");
            }
        }
    }

    private void atualizarPainelPontuacao() {
        String mestre = (jogador.getPokedex().totalCapturados() >= 8) ? " - MESTRE POKÉMON!" : "";
        painelPontuacao.setText("<html>Pontuação: " + jogador.getPontuacao() +
                "<br>Pokédex: " + jogador.getPokedex().totalCapturados() + "/8" + mestre + "</html>");
    }

    private void abrirJogo() {
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Raichu", true));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        Jogo jogo = new Jogo(tabuleiro, jogador);
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, jogo);
        movimento.start();
    }
}
