package jogopokemon.janelas;

import jogopokemon.Batalha2Pokemons;
import jogopokemon.Pokemon;

import javax.swing.*;
import java.awt.*;

public class Batalha extends JFrame {
    private final JLabel status;
    private final JLabel hpJogador;
    private final JLabel hpInimigo;
    private final JButton botaoAtacar;
    private final JButton botaoFugir;
    private final Pokemon jogador;
    private final Pokemon inimigo;
    private final Jogo jogo;
    private int turno = 1;

    public Batalha(Pokemon jogador, Pokemon inimigo, Jogo jogo) {
        super("Batalha Pokémon");
        this.jogador = jogador;
        this.inimigo = inimigo;
        this.jogo = jogo;

        JPanel painelInformacoes = new JPanel(new BorderLayout());
        JPanel gridPainelInformacoes = new JPanel(new GridBagLayout());
        GridBagConstraints constraintsPainel = new GridBagConstraints();
        gridPainelInformacoes.add(new Label(jogador.getTreinador().getNome() + " iniciou uma batalha com "
            + inimigo.getTreinador().getNome() + "!", Label.CENTER),
            constraintsPainel);

        constraintsPainel.gridy = 1;
        hpJogador = new JLabel("HP " + jogador.getNome() + ": " + jogador.getHp(), SwingConstants.CENTER);
        gridPainelInformacoes.add(hpJogador, constraintsPainel);

        constraintsPainel.gridy = 2;
        hpInimigo = new JLabel("HP " + inimigo.getNome() + ": " + inimigo.getHp(), SwingConstants.CENTER);
        gridPainelInformacoes.add(hpInimigo, constraintsPainel);

        painelInformacoes.add(gridPainelInformacoes, BorderLayout.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 10, 3, 10);
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        add(painelInformacoes, constraints);

        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        botaoAtacar = new JButton("Atacar");
        botaoAtacar.setPreferredSize(new Dimension(250, 30));
        botaoAtacar.addActionListener(e -> atacar());
        constraints.gridy = 1;
        add(botaoAtacar, constraints);

        botaoFugir = new JButton("Fugir");
        botaoFugir.setPreferredSize(new Dimension(250, 30));
        botaoFugir.addActionListener(e -> fugir());
        constraints.gridy = 2;
        add(botaoFugir, constraints);

        status = new JLabel("Sua vez!");
        constraints.insets = new Insets(3, 10, 10, 10);
        constraints.gridy = 3;
        add(status, constraints);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atacar() {
        botaoAtacar.setEnabled(false);
        botaoFugir.setEnabled(false);

        int dano = Batalha2Pokemons.atacarComTipo(jogador, inimigo, turno);
        status.setText(jogador.getNome() + " causou " + dano + " de dano em " + inimigo.getNome() + "!");
        atualizarHp();

        new Thread(() -> {
            turno++;
            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}

            if (inimigo.getHp() <= 0) {
                status.setText(inimigo.getNome() + " foi derrotado!");
                jogo.batalhaRealizada();
                setDefaultCloseOperation(HIDE_ON_CLOSE);
                return;
            }

            status.setText("Turno do inimigo!");
            int danoInimigo = Batalha2Pokemons.atacarComTipo(inimigo, jogador, turno);
            status.setText(inimigo.getNome() + " causou " + danoInimigo + " de dano em " + jogador.getNome() + "!");
            atualizarHp();

            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
            if (jogador.getHp() <= 0) {
                status.setText(jogador.getNome() + " foi derrotado!");
                jogo.batalhaRealizada();
                setDefaultCloseOperation(HIDE_ON_CLOSE);
            } else {
                status.setText("Sua vez!");
                botaoAtacar.setEnabled(true);
                botaoFugir.setEnabled(true);
            }
        }).start();
    }

    private void fugir() {
        botaoAtacar.setEnabled(false);
        botaoFugir.setEnabled(false);

        status.setText(jogador.getTreinador().getNome() + " fugiu!");
        jogo.batalhaRealizada();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void atualizarHp() {
        hpJogador.setText("HP " + jogador.getNome() + ": " + jogador.getHp());
        hpInimigo.setText("HP " + inimigo.getNome() + ": " + inimigo.getHp());
    }
}
