package jogopokemon;

import jogopokemon.pokemons.*;

import java.util.ArrayList;

public class Tabuleiro {
    private final Pokemon[][] grid;
    private final int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() {
        return tamanho;
    }

    // NOVO: método que posiciona Pokémon somente na região correta
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean b) throws RegiaoInvalidaException {
        if (!pertenceRegiao(linha, coluna, p)) {
            throw new RegiaoInvalidaException("Pokémon " + p.getNome() + " não pode ser colocado nessa região!");
        }
        grid[linha][coluna] = p;
    }

    public Pokemon getPokemon(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public void removerPokemon(int linha, int coluna) {
        grid[linha][coluna] = null;
    }

    // NOVO: lógica das regiões do enunciado
    private boolean pertenceRegiao(int linha, int coluna, Pokemon p) {
        int meio = tamanho / 2;

        if (linha < meio && coluna < meio && p.getTipo().equals("Água")) return true;       // Região Água
        if (linha < meio && coluna >= meio && p.getTipo().equals("Floresta")) return true; // Região Floresta
        if (linha >= meio && coluna < meio && p.getTipo().equals("Terra")) return true;    // Região Terra
        if (linha >= meio && coluna >= meio && p.getTipo().equals("Elétrico")) return true;// Região Elétrico

        return false;
    }

    public void posicionarPokemon(int i, int i1, Agua squirtle, boolean b) {
    }

    public void posicionarPokemon(int i, int i1, Eletrico pikachu, boolean b) {
    }

    public ArrayList<Pokemon> listarPokemonsSelvagens() {
    }
}
