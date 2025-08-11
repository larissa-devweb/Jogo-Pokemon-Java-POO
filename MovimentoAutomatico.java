/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

/**
O que a Thread vai fazer

 A cada 3 segundos, um Pokémon selvagem do tabuleiro vai tentar se mover para uma célula vizinha livre.
Funciona paralelo ao que o jogador estiver fazendo
 */
public class MovimentoAutomatico extends Thread {
    private final Tabuleiro tabuleiro;

    public MovimentoAutomatico(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000); // espera 3 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // escolhe posição aleatória
            int linha = (int) (Math.random() * tabuleiro.tamanho);
            int coluna = (int) (Math.random() * tabuleiro.tamanho);

            Pokemon p = tabuleiro.getPokemon(linha, coluna);

            // só move se for Pokémon selvagem
            if (p != null && p.isSelvagem()) {
                int novaLinha = linha + (int) (Math.random() * 3) - 1; // -1, 0 ou +1
                int novaColuna = coluna + (int) (Math.random() * 3) - 1;

                // mantém dentro dos limites do tabuleiro
                if (novaLinha >= 0 && novaLinha < tabuleiro.tamanho &&
                    novaColuna >= 0 && novaColuna < tabuleiro.tamanho &&
                    tabuleiro.getPokemon(novaLinha, novaColuna) == null) {

                    tabuleiro.removerPokemon(linha, coluna);
                    try {
                        tabuleiro.posicionarPokemon(novaLinha, novaColuna, p, true);
                        System.out.println(p.getNome() + " se moveu para [" + novaLinha + "," + novaColuna + "]");
                    } catch (RegiaoInvalidaException e) {
                        // se for região errada, não move
                    }
                }
            }
        }
    }
}
