package jogopokemon;

import jogopokemon.janelas.Jogo;

import java.util.Random;

/**
 * Thread responsável por movimentar automaticamente Pokémons selvagens.
 */
public class MovimentoAutomatico extends Thread {
    private final Tabuleiro tabuleiro;
    private final Random random = new Random();

    public MovimentoAutomatico(Tabuleiro tabuleiro, Jogo jogo) {
        this.tabuleiro = tabuleiro;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000); // espera 3 segundos

                Pokemon selvagem = tabuleiro.getPokemonSelvagemAleatorio();
                if (selvagem != null) {
                    moverPokemon(selvagem);

                    // Mostra o tabuleiro no console após movimento
                    System.out.println("\n[Movimento automático]");
                    tabuleiro.exibir();
                }

            } catch (InterruptedException e) {
                System.out.println("Movimento automático interrompido.");
                break;
            }
        }
    }

    private void moverPokemon(Pokemon pokemon) {
        int[] pos = tabuleiro.localizar(pokemon);
        if (pos == null) return;
        int linha = pos[0];
        int coluna = pos[1];

        int direcao = random.nextInt(4); // 0=cima,1=baixo,2=esq,3=dir
        int novaLinha = linha;
        int novaColuna = coluna;

        switch (direcao) {
            case 0 -> novaLinha--;
            case 1 -> novaLinha++;
            case 2 -> novaColuna--;
            case 3 -> novaColuna++;
        }

        if (tabuleiro.posicaoValida(novaLinha, novaColuna) &&
                tabuleiro.estaLivre(novaLinha, novaColuna) &&
                tabuleiro.podeFicar(pokemon, novaLinha, novaColuna)) {

            tabuleiro.moverPokemon(pokemon, novaLinha, novaColuna);
            System.out.println(pokemon.getNome() + " se moveu para (" + novaLinha + "," + novaColuna + ")");
        }
    }
}
