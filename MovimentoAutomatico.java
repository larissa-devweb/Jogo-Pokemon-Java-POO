package jogopokemon;

import jogopokemon.janelas.Jogo;

public class MovimentoAutomatico extends Thread {
    private final Tabuleiro tabuleiro;
    private final Jogo gui; // referência GUI opcional

    public MovimentoAutomatico(Tabuleiro tabuleiro, Jogo gui) {
        this.tabuleiro = tabuleiro;
        this.gui = gui;
    }

    @Override
    public void run() {
        while (tabuleiro.temPokemonsSelvagens()) {
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

            int linha = (int)(Math.random()*tabuleiro.getTamanho());
            int coluna = (int)(Math.random()*tabuleiro.getTamanho());
            Pokemon p = tabuleiro.getPokemon(linha, coluna);

            if (p != null && p.isSelvagem()) {
                int novaLinha = linha + (int)(Math.random()*3)-1;
                int novaColuna = coluna + (int)(Math.random()*3)-1;
                if (novaLinha>=0 && novaLinha<tabuleiro.getTamanho() && novaColuna>=0 && novaColuna<tabuleiro.getTamanho()
                        && tabuleiro.getPokemon(novaLinha,novaColuna)==null) {
                    tabuleiro.removerPokemon(linha, coluna);
                    try { tabuleiro.posicionarPokemon(novaLinha, novaColuna, p, false); }
                    catch (RegiaoInvalidaException ignored) {}
                    if (gui != null) gui.atualizarTabuleiro();
                }
            }
        }
    }
}
