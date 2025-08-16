package jogopokemon;

import jogopokemon.janelas.Jogo;

/**
 * Thread que move automaticamente Pokémons selvagens a cada 3 segundos.
 * Funciona paralelo ao que o jogador estiver fazendo.
 */
public class MovimentoAutomatico extends Thread {
    private final Tabuleiro tabuleiro;
    private final Jogo gui; // referência para atualizar a interface

    public MovimentoAutomatico(Tabuleiro tabuleiro, Jogo gui) {
        this.tabuleiro = tabuleiro;
        this.gui = gui;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000); // espera 3 segundos
            } catch (InterruptedException ignored) {
            }

            // Escolhe uma posição aleatória
            int linha = (int) (Math.random() * tabuleiro.getTamanho());
            int coluna = (int) (Math.random() * tabuleiro.getTamanho());

            Pokemon p = tabuleiro.getPokemon(linha, coluna);

            if (p != null && p.isSelvagem()) {
                int novaLinha = linha + (int) (Math.random() * 3) - 1; // -1,0,1
                int novaColuna = coluna + (int) (Math.random() * 3) - 1; // -1,0,1

                // Verifica limites e se célula está livre
                if (novaLinha >= 0 && novaLinha < tabuleiro.getTamanho() &&
                        novaColuna >= 0 && novaColuna < tabuleiro.getTamanho() &&
                        tabuleiro.getPokemon(novaLinha, novaColuna) == null) {

                    tabuleiro.removerPokemon(linha, coluna); // remove da posição antiga
                    try {
                        // Posiciona na nova posição (false = não em ambiente adverso)
                        tabuleiro.posicionarPokemon(novaLinha, novaColuna, p, false);
                        System.out.println(p.getNome() + " se moveu para [" + novaLinha + "," + novaColuna + "]");
                        gui.atualizarTabuleiro(); // atualiza interface
                    } catch (RegiaoInvalidaException e) {
                        // se for região errada, ignora o movimento
                    }
                }
            }
        }
    }
}
