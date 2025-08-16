package jogopokemon;

import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final Pokemon[][] grid;
    private final Random rnd = new Random();

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() { return tamanho; }

    public Pokemon getPokemon(int linha, int coluna) { return grid[linha][coluna]; }

    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean setTreinador)
            throws RegiaoInvalidaException {
        if (!regiaoValida(p.getTipo(), linha, coluna))
            throw new RegiaoInvalidaException("Pokémon do tipo " + p.getTipo() + " não pode ser colocado nessa região!");
        grid[linha][coluna] = p;
        if (setTreinador) p.setTreinador(null);
        p.setEmAmbienteAdverso(false);
    }

    private boolean regiaoValida(String tipo, int linha, int coluna) {
        int n = tamanho;
        switch (tipo.toLowerCase()) {
            case "agua":       return linha >= 0 && linha < n/2 && coluna >= 0 && coluna < n/2;
            case "floresta":   return linha >= 0 && linha < n/2 && coluna >= n/2 && coluna < n;
            case "terra":      return linha >= n/2 && linha < n && coluna >= 0 && coluna < n/2;
            case "eletrico":   return linha >= n/2 && linha < n && coluna >= n/2 && coluna < n;
            default: return true;
        }
    }

    public void removerPokemon(int linha, int coluna) { grid[linha][coluna] = null; }

    // ========== Novo método ==========
    public boolean posicionarPokemonAleatoriamente(Pokemon p) {
        ArrayList<int[]> possiveis = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null && regiaoValida(p.getTipo(), i, j)) {
                    possiveis.add(new int[]{i, j});
                }
            }
        }

        if (possiveis.isEmpty()) return false; // nenhuma posição disponível

        int[] pos = possiveis.get(rnd.nextInt(possiveis.size()));
        grid[pos[0]][pos[1]] = p;
        p.setEmAmbienteAdverso(false);
        return true;
    }

    // Método de debug: imprime tabuleiro
    public void exibir() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null) System.out.print("[   ] ");
                else System.out.print("[" + grid[i][j].getTipo().charAt(0) + "] ");
            }
            System.out.println();
        }
    }

    // ========= Verifica se há algum Pokémon selvagem =========
    public boolean temPokemonsSelvagens() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) return true;
            }
        }
        return false;
    }
    // Retorna um Pokémon selvagem aleatório do tabuleiro
    public Pokemon pegarPokemonSelvagemAleatorio() {
        ArrayList<Pokemon> selvagens = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) {
                    selvagens.add(p);
                }
            }
        }
        if (selvagens.isEmpty()) return null;
        return selvagens.get(new Random().nextInt(selvagens.size()));
    }



}
