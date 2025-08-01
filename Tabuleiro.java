/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;
import java.util.ArrayList;

public class Tabuleiro {
    private Pokemon[][] tabuleiro;
    public final int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        tabuleiro = new Pokemon[tamanho][tamanho];
    }

    // Posiciona um Pokémon se a posição estiver livre e válida
    public void posicionarPokemon(int linha, int coluna, Pokemon pokemon, boolean selvagem) throws RegiaoInvalidaException {
        if (!posicaoValida(linha, coluna)) {
            throw new RegiaoInvalidaException("Posição fora dos limites do tabuleiro.");
        }

        if (tabuleiro[linha][coluna] != null) {
            System.out.println("Já existe um Pokémon na posição [" + linha + "," + coluna + "].");
            return;
        }

        pokemon.setSelvagem(selvagem);
        tabuleiro[linha][coluna] = pokemon;
    }

    public void removerPokemon(int linha, int coluna) {
        if (posicaoValida(linha, coluna)) {
            tabuleiro[linha][coluna] = null;
        }
    }

    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho;
    }

    public boolean estaVazio(int linha, int coluna) {
        return tabuleiro[linha][coluna] == null;
    }

    public void exibir() {
        System.out.println("\n=== TABULEIRO ===");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == null) {
                    System.out.print("[   ] ");
                } else {
                    System.out.print("[" + tabuleiro[i][j].getNome().charAt(0) + "] ");
                }
            }
            System.out.println();
        }
    }

    public ArrayList<Pokemon> listarPokemonsSelvagens() {
        ArrayList<Pokemon> lista = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = tabuleiro[i][j];
                if (p != null && p.isSelvagem()) {
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    public int[] getPosicaoDoPokemon(Pokemon alvo) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == alvo) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    void mostrarTabuleiro(boolean debug) {
        throw new UnsupportedOperationException("Nao suportado."); 
    }

public Pokemon getPokemon(int linha, int coluna) {
    if (posicaoValida(linha, coluna)) {
        return tabuleiro[linha][coluna];
    }
    return null;
}
}

