package jogopokemon;

import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import java.util.ArrayList;

public class Tabuleiro {
    private final Pokemon[][] grid;
    private int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() {
        return tamanho;
    }

    public Pokemon getPokemon(int linha, int coluna) {
        if (linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            return null;
        }
        return grid[linha][coluna];
    }

    public int[] getPosicao(Pokemon p) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == p) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public void removerPokemon(int linha, int coluna) {
        if (linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho) {
            grid[linha][coluna] = null;
        }
    }

    public void posicionarPokemon(int linha, int coluna, Pokemon pokemon, boolean selvagem) throws RegiaoInvalidaException {
        if (linha < 0 || linha >= tamanho || coluna < 0 || coluna >= tamanho) {
            throw new IllegalArgumentException("Posição inválida.");
        }

        int n2 = tamanho / 2;
        boolean regiaoCorreta;
        String tipo = pokemon.getTipo();

        if (tipo.equalsIgnoreCase("Agua")) {
            regiaoCorreta = (linha < n2 && coluna < n2);
        } else if (tipo.equalsIgnoreCase("Floresta")) {
            regiaoCorreta = (linha < n2 && coluna >= n2);
        } else if (tipo.equalsIgnoreCase("Terra")) {
            regiaoCorreta = (linha >= n2 && coluna < n2);
        } else if (tipo.equalsIgnoreCase("Eletrico")) {
            regiaoCorreta = (linha >= n2 && coluna >= n2);
        } else {
            regiaoCorreta = true;
        }

        if (!regiaoCorreta) {
            throw new RegiaoInvalidaException("Pokémon do tipo " + tipo + " não pode ser colocado nessa região!");
        }

        pokemon.setSelvagem(selvagem); //  define se é selvagem
        grid[linha][coluna] = pokemon;

        // Define ambiente adverso se necessário
        pokemon.setEmAmbienteAdverso(!regiaoCorreta);
    }

    public ArrayList<Pokemon> listarPokemonsSelvagens() {
        ArrayList<Pokemon> selvagens = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) {
                    selvagens.add(p);
                }
            }
        }
        return selvagens;
    }

    public void exibir() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p == null) {
                    System.out.print("[   ]");
                } else {
                    System.out.print("[" + p.getNome().charAt(0) + "]");
                }
            }
            System.out.println();
        }
    }

    // Posicionamento aleatório simplificado (exemplo)
    public void posicionarPokemonAleatoriamente(Pokemon p) {
        boolean colocado = false;
        while (!colocado) {
            int linha = (int) (Math.random() * tamanho);
            int coluna = (int) (Math.random() * tamanho);
            if (grid[linha][coluna] == null) {
                try {
                    posicionarPokemon(linha, coluna, p, true);
                    colocado = true;
                } catch (RegiaoInvalidaException ignored) {
                }
            }
        }
    }
    public boolean todosSelvagensCapturados() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = getPokemon(i, j);
                if (p != null && p.isSelvagem()) {
                    return false;
                }
            }
        }
        return true;
    }

}
