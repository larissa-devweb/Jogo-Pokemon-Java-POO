package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.RegiaoInvalidaException;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class JanelaInicial extends JFrame {
    public JanelaInicial() {
        super("Pokémon GUI Básico");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3,10,3,10);

        add(new Label("Bem-vindo!", Label.CENTER), constraints);

        JButton botao = new JButton("Carregar jogo salvo");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> abrirJogo());

        constraints.gridy = 1;
        add(botao, constraints);

        botao = new JButton("Posicionar time Pokémon");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> abrirJogo());

        constraints.gridy = 2;
        add(botao, constraints);

        botao = new JButton("Distribuir Pokémons aleatoriamente");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> abrirJogo());

        constraints.gridy = 3;
        add(botao, constraints);

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void abrirJogo() {
        Tabuleiro tabuleiro = new Tabuleiro(5);
        Treinador ash = new Treinador("Ash");

        try {
            tabuleiro.posicionarPokemon(2, 2, new Agua("Squirtle"), true);
            tabuleiro.posicionarPokemon(4, 1, new Eletrico("Pikachu"), true);
        } catch (RegiaoInvalidaException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao posicionar Pokémon: " + e.getMessage());
        }

        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abre jogo
        JanelaJogo jogo = new JanelaJogo(tabuleiro, ash);

        // Inicia thread de movimento automático
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, jogo);
        movimento.start();
    }
}
