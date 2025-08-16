package jogopokemon;

import java.util.ArrayList;

public class Tabuleiro {
    private Pokemon[][] grid;
    private int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new Pokemon[tamanho][tamanho];
    }

    // Método existente para posicionar manualmente
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean selvagem) throws RegiaoInvalidaException {
        if (linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            throw new RegiaoInvalidaException("Posição inválida!");
        }
        p.setSelvagem(selvagem);
        grid[linha][coluna] = p;
    }

    // NOVO: retorna posição do Pokémon (para listagem no menu 3)
    public int[] posicionarPokemon(Pokemon p) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == p) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // NOVO: retorna pokémon de uma célula
    public Pokemon getPokemon(int linha, int coluna) {
        return grid[linha][coluna];
    }

    // NOVO: lista todos os pokémon selvagens
    public ArrayList<Pokemon> listarPokemonsSelvagens() {
        ArrayList<Pokemon> lista = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] != null && grid[i][j].isSelvagem()) {
                    lista.add(grid[i][j]);
                }
            }
        }
        return lista;
    }

    // NOVO: imprime o tabuleiro simplificado
    public void exibir() {
        System.out.println("\n=== TABULEIRO ===");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null) {
                    System.out.print("[ ] ");
                } else if (grid[i][j].isSelvagem()) {
                    System.out.print("[S] "); // Selvagem
                } else {
                    System.out.print("[T] "); // Do treinador
                }
            }
            System.out.println();
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}
