package jogopokemon.janelas;

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
        botao.addActionListener(e -> posicoesAleatorias());

        constraints.gridy = 3;
        add(botao, constraints);

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void definirPosicoes() {
        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abrir janela de definir posições
        new EscolherPosicoes();
    }

    private void posicoesAleatorias() {
        // Fechar janela atual sem sair do programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        // Abrir janela de definir posições
        PosicoesAleatorias posicoesAleatorias = new PosicoesAleatorias();
    }
}
