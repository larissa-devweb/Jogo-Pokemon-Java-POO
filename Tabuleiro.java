package jogopokemon;

import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final Pokemon[][] grid;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() {
        return tamanho;
    }

    public Pokemon getPokemon(int linha, int coluna) {
        return grid[linha][coluna];
    }

    // Posiciona Pokémon em uma célula específica
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean setTreinador)
            throws RegiaoInvalidaException {
        if (!regiaoValida(p.getTipo(), linha, coluna)) {
            throw new RegiaoInvalidaException(
                    "Pokémon do tipo " + p.getTipo() + " não pode ser colocado nessa região!"
            );
        }

        grid[linha][coluna] = p;
        if (setTreinador) p.setTreinador(null);
        p.setEmAmbienteAdverso(false);
    }

    // Posiciona Pokémon em uma posição aleatória válida
    public void posicionarPokemonAleatoriamente(Pokemon p) {
        Random r = new Random();
        int linha, coluna;
        int tentativas = 0;

        do {
            linha = r.nextInt(tamanho);
            coluna = r.nextInt(tamanho);
            tentativas++;
        } while ((!regiaoValida(p.getTipo(), linha, coluna) || grid[linha][coluna] != null) && tentativas < 100);

        if (tentativas < 100) {
            grid[linha][coluna] = p;
            p.setEmAmbienteAdverso(false);
        } else {
            System.out.println("Não foi possível posicionar " + p.getNome());
        }
    }

    // Verifica se tipo de Pokémon pode estar nessa região
    private boolean regiaoValida(String tipo, int linha, int coluna) {
        if (tipo == null) return false;
        switch (tipo.toLowerCase()) {
            case "agua":      return linha <= tamanho / 2 - 1 && coluna <= tamanho / 2 - 1;
            case "floresta":  return linha <= tamanho / 2 - 1 && coluna >= tamanho / 2;
            case "terra":     return linha >= tamanho / 2 && coluna <= tamanho / 2 - 1;
            case "eletrico":  return linha >= tamanho / 2 && coluna >= tamanho / 2;
            default: return true;
        }
    }

    public void removerPokemon(int linha, int coluna) {
        grid[linha][coluna] = null;
    }

    public boolean temPokemonsSelvagens() {
        for (int i = 0; i < tamanho; i++)
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) return true;
            }
        return false;
    }

    public Pokemon pegarPokemonSelvagemAleatorio() {
        ArrayList<Pokemon> selvagens = listarPokemonsSelvagens();
        if (selvagens.isEmpty()) return null;
        Random r = new Random();
        return selvagens.get(r.nextInt(selvagens.size()));
    }

    public ArrayList<Pokemon> listarPokemonsSelvagens() {
        ArrayList<Pokemon> selvagens = new ArrayList<>();
        for (int i = 0; i < tamanho; i++)
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) selvagens.add(p);
            }
        return selvagens;
    }

    // Apenas para debug: imprime o tabuleiro no console
    public void exibir() {
        System.out.println("=== TABULEIRO ===");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] != null) System.out.print(grid[i][j].getNome() + "\t");
                else System.out.print("Vazio\t");
            }
            System.out.println();
        }
        System.out.println("================");
    }
}
