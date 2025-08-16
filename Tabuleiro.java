package jogopokemon;

import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final Pokemon[][] grid;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() { return tamanho; }

    public Pokemon getPokemon(int linha, int coluna) { return grid[linha][coluna]; }

    // Posiciona Pokémon manualmente em posição específica
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean setTreinador)
            throws RegiaoInvalidaException {
        if (!regiaoValida(p.getTipo(), linha, coluna))
            throw new RegiaoInvalidaException("Pokémon do tipo " + p.getTipo() + " não pode ser colocado nessa região!");
        grid[linha][coluna] = p;
        if (setTreinador) p.setTreinador(null);
        p.setEmAmbienteAdverso(false);
    }

    // Posiciona Pokémon aleatoriamente em posição válida
    public void posicionarPokemonAleatoriamente(Pokemon p) {
        Random r = new Random();
        boolean colocado = false;
        while (!colocado) {
            int linha = r.nextInt(tamanho);
            int coluna = r.nextInt(tamanho);
            if (grid[linha][coluna] == null && regiaoValida(p.getTipo(), linha, coluna)) {
                grid[linha][coluna] = p;
                p.setEmAmbienteAdverso(false);
                colocado = true;
            }
        }
    }

    private boolean regiaoValida(String tipo, int linha, int coluna) {
        if (tipo == null) return false;
        int N = tamanho;
        switch (tipo.toLowerCase()) {
            case "agua": return linha <= (N / 2 - 1) && coluna <= (N / 2 - 1);
            case "floresta": return linha <= (N / 2 - 1) && coluna >= (N / 2);
            case "terra": return linha >= (N / 2) && coluna <= (N / 2 - 1);
            case "eletrico": return linha >= (N / 2) && coluna >= (N / 2);
            default: return true;
        }
    }

    public void removerPokemon(int linha, int coluna) { grid[linha][coluna] = null; }

    public boolean temPokemonsSelvagens() {
        for (int i = 0; i < tamanho; i++)
            for (int j = 0; j < tamanho; j++)
                if (grid[i][j] != null && grid[i][j].isSelvagem()) return true;
        return false;
    }

    public Pokemon pegarPokemonSelvagemAleatorio() {
        ArrayList<Pokemon> selvagens = listarPokemonsSelvagens();
        if (selvagens.isEmpty()) return null;
        return selvagens.get(new Random().nextInt(selvagens.size()));
    }

    public ArrayList<Pokemon> listarPokemonsSelvagens() {
        ArrayList<Pokemon> selvagens = new ArrayList<>();
        for (int i = 0; i < tamanho; i++)
            for (int j = 0; j < tamanho; j++)
                if (grid[i][j] != null && grid[i][j].isSelvagem()) selvagens.add(grid[i][j]);
        return selvagens;
    }

    // Apenas para debug no console
    public void exibir() {
        System.out.println("=== Tabuleiro ===");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] != null) System.out.print(grid[i][j].getNome() + "\t");
                else System.out.print("Vazio\t");
            }
            System.out.println();
        }
    }
}
