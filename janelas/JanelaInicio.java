package jogopokemon.janelas;

import javax.swing.*;
import java.awt.*;

public class JanelaInicio extends JFrame {

    public JanelaInicio() {
        super("Pokémon - Início");
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bem-vindo ao Jogo Pokémon!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton botaoNovoJogo = new JButton("Novo Jogo");
        botaoNovoJogo.addActionListener(e -> {
            new JogoPokemon(); // inicia novo jogo
            dispose();
        });
        add(botaoNovoJogo, BorderLayout.SOUTH);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class JogoPokemon {
    }
}

