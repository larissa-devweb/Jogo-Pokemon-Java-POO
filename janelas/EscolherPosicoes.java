package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.RegiaoInvalidaException;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;
import jogopokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class EscolherPosicoes extends JFrame {
    private final Tabuleiro tabuleiro;
    private final JButton[][] botoesTabuleiro;
    private final ArrayList<JButton> botoesPokemons;
    private Pokemon pokemonEscolhido;
    private boolean modoDebug;

    public EscolherPosicoes() {
        super("Distribuir Pokémons");

        // Cria tabuleiro 5x5 (pode ajustar)
        this.tabuleiro = new Tabuleiro(5);
        int tamanho = tabuleiro.getTamanho();

        // Painel do tabuleiro (grade)
        JPanel painelGrid = new JPanel(new GridLayout(tamanho, tamanho, 4, 4));
        botoesTabuleiro = new JButton[tamanho][tamanho];
        for (int linha = 0; linha < tamanho; linha++) {
            for (int coluna = 0; coluna < tamanho; coluna++) {
                JButton botao = new JButton("Vazio");
                botoesTabuleiro[linha][coluna] = botao;
                final int l = linha;
                final int c = coluna;
                botao.addActionListener(e -> posicionarPokemon(l, c));
                painelGrid.add(botao);
            }
        }

        // Painel de controle (botões de pokémon e ações)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        botoesPokemons = new ArrayList<>();

        JButton btnDebug = new JButton("Debug");
        btnDebug.addActionListener(e -> {
            modoDebug = !modoDebug;
            btnDebug.setText(modoDebug ? "Debug ON" : "Debug");
        });
        painelBotoes.add(btnDebug);

        // Botão Pikachu (Elétrico)
        JButton btnPikachu = new JButton("Pikachu");
        btnPikachu.addActionListener(e -> escolherPokemon(new Eletrico("Pikachu", false)));
        botoesPokemons.add(btnPikachu);
        painelBotoes.add(btnPikachu);

        // Botão Bulbasaur (Floresta)
        JButton btnBulbasaur = new JButton("Bulbasaur");
        btnBulbasaur.addActionListener(e -> escolherPokemon(new Floresta("Bulbasaur", false)));
        botoesPokemons.add(btnBulbasaur);
        painelBotoes.add(btnBulbasaur);

        // Botão Squirtle (Água)
        JButton btnSquirtle = new JButton("Squirtle");
        btnSquirtle.addActionListener(e -> escolherPokemon(new Agua("Squirtle", false)));
        botoesPokemons.add(btnSquirtle);
        painelBotoes.add(btnSquirtle);

        // Botão Jogar
        JButton btnJogar = new JButton("Jogar");
        btnJogar.addActionListener(e -> abrirJogo());
        painelBotoes.add(btnJogar);

        // Monta a janela
        setLayout(new BorderLayout(8, 8));
        add(painelGrid, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabuleiro();

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void escolherPokemon(Pokemon pokemon) {
        // desabilita botões enquanto escolhe (modo simples)
        for (JButton b : botoesPokemons) b.setEnabled(false);
        pokemonEscolhido = pokemon;
        // habilita só o botão "Jogar" e mantem visual
        // (o clique no grid vai posicionar e depois reabilitar os botões)
    }

    private void posicionarPokemon(int linha, int coluna) {
        try {
            if (pokemonEscolhido == null) {
                JOptionPane.showMessageDialog(this, "Nenhum Pokémon selecionado");
                return;
            }

            // tenta posicionar no tabuleiro (passando true = selvagem/permite)
            tabuleiro.posicionarPokemon(linha, coluna, pokemonEscolhido, true);

            // atualiza estado dos botões: se não for modoDebug, remove a opção
            for (JButton botao : botoesPokemons) {
                if (!modoDebug) botao.setEnabled(false);
                else botao.setEnabled(!pokemonEscolhido.getNome().equals(botao.getText()));
            }

            // remove a opção da lista para não poder posicionar o mesmo duas vezes
            botoesPokemons.removeIf(botao -> pokemonEscolhido.getNome().equals(botao.getText()));

        } catch (RegiaoInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao posicionar: " + e.getMessage());
        } finally {
            pokemonEscolhido = null;
            // reabilita botões (modo debug mantém alguns habilitados acima)
            if (!modoDebug) {
                for (JButton b : botoesPokemons) b.setEnabled(true);
            }
            atualizarTabuleiro();
        }
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

    /**
     * Posiciona Pokémons aleatoriamente no tabuleiro, respeitando regiões.
     * Usa tentativa aleatória com limite de tentativas para evitar loop infinito.
     */
    private void posicionarAleatorio(Pokemon p) {
        Random rnd = new Random();
        int tamanho = tabuleiro.getTamanho();
        int tentativas = 0;
        boolean colocado = false;
        while (!colocado && tentativas < tamanho * tamanho * 4) {
            int l = rnd.nextInt(tamanho);
            int c = rnd.nextInt(tamanho);
            if (tabuleiro.getPokemon(l, c) == null) {
                try {
                    tabuleiro.posicionarPokemon(l, c, p, true);
                    colocado = true;
                } catch (RegiaoInvalidaException ignored) {
                    // região inválida, tenta outra posição
                }
            }
            tentativas++;
        }
        if (!colocado) {
            System.out.println("Não foi possível posicionar " + p.getNome() + " aleatoriamente.");
        }
        atualizarTabuleiro();
    }

    private void abrirJogo() {
        // Posiciona alguns pokémons aleatoriamente usando o helper local
        posicionarAleatorio(new Agua("Psyduck", true));
        posicionarAleatorio(new Eletrico("Raichu", true));

        // Fecha a janela atual (sem encerrar o programa)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abre a interface do jogo principal
        Jogo jogo = new Jogo(tabuleiro, new Treinador("Ash"));

        // Inicia thread de movimento automático (se a classe existir)
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, jogo);
        movimento.start();
    }
}
