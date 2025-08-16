package jogopokemon.janelas;

import jogopokemon.MovimentoAutomatico;
import jogopokemon.Tabuleiro;
import jogopokemon.Treinador;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class BoasVindas extends JFrame {
    public BoasVindas() {
        super("Pokémon GUI Básico");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3,10,3,10);

        add(new Label("Bem-vindo!", Label.CENTER), constraints);

        JButton botao = new JButton("Carregar jogo salvo");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> definirPosicoes());

        constraints.gridy = 1;
        add(botao, constraints);

        botao = new JButton("Posicionar time Pokémon");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> definirPosicoes());

        constraints.gridy = 2;
        add(botao, constraints);

        botao = new JButton("Distribuir Pokémons aleatoriamente");
        botao.setPreferredSize(new Dimension(250, 30));
        botao.addActionListener(e -> abrirJogoAleatorio());

        constraints.gridy = 3;
        add(botao, constraints);

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void definirPosicoes() {
        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abrir janela de definir posições
        new EscolherPosicoes();
    }

    private void abrirJogoAleatorio() {
        // Cria tabuleiro 5x5 (pode ajustar)
        Tabuleiro tabuleiro = new Tabuleiro(5);

        // TODO: Adicionar um na mochila do jogador e outro na do computador
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Pikachu", false));
        tabuleiro.posicionarPokemonAleatoriamente(new Floresta("Bulbasaur", false));

        // Selvagens
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Squirtle", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Agua("Psyduck", true));
        tabuleiro.posicionarPokemonAleatoriamente(new Eletrico("Raichu", true));

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
