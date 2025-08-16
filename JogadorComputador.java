package jogopokemon;

import jogopokemon.pokemons.IAtaque;

public class JogadorComputador extends Thread {
    private final Tabuleiro tabuleiro;
    private final Treinador computador;
    private boolean suaVez = false;
    private final IAtaque ataque;

    public JogadorComputador(Tabuleiro tabuleiro, Treinador computador, IAtaque ataque) {
        this.tabuleiro = tabuleiro;
        this.computador = computador;
        this.ataque = ataque;
    }

    @Override
    public void run() {
        while (!tabuleiro.todosSelvagensCapturados()) {
            if (suaVez) {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                jogar();
                suaVez = false;
            } else {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }
    }

    private void jogar() {
        // lógica simplificada: captura o primeiro Pokémon selvagem disponível
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                Pokemon p = tabuleiro.getPokemon(i, j);
                if (p != null && p.isSelvagem()) {
                    boolean capturado = CapturaSelvagem.tentarCaptura(p, tabuleiro, i, j, computador);
                    if (capturado) {
                        computador.aumentarPontuacao(10);
                        tabuleiro.removerPokemon(i, j);
                        System.out.println(computador.getNome() + " capturou " + p.getNome());
                    }
                    return;
                }
            }
        }
    }

    public void iniciarTurno() {
        suaVez = true;
    }
}

