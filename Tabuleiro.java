package jogopokemon;

import jogopokemon.excecoes.NenhumaPosicaoDisponivelException;
import jogopokemon.excecoes.RegiaoInvalidaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final Pokemon[][] grid;
    private final Random rnd = new Random();

    // Listas separadas por região
    private final List<Pokemon> regiaoAgua = new ArrayList<>();
    private final List<Pokemon> regiaoFloresta = new ArrayList<>();
    private final List<Pokemon> regiaoTerra = new ArrayList<>();
    private final List<Pokemon> regiaoEletrico = new ArrayList<>();

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new Pokemon[tamanho][tamanho];
    }

    public int getTamanho() {
        return tamanho;
    }

    public Pokemon getPokemon(int linha, int coluna) {
        if (!posicaoValida(linha, coluna)) return null;
        return grid[linha][coluna];
    }

    // Remove Pokémon do grid e da lista de região
    public void removerPokemon(int linha, int coluna) {
        if (!posicaoValida(linha, coluna)) return;
        Pokemon p = grid[linha][coluna];
        if (p == null) return;
        grid[linha][coluna] = null;
        removerDaRegiao(p);
    }

    private void removerDaRegiao(Pokemon p) {
        switch (p.getTipo().toLowerCase()) {
            case "agua", "água" -> regiaoAgua.remove(p);
            case "floresta" -> regiaoFloresta.remove(p);
            case "terra" -> regiaoTerra.remove(p);
            case "eletrico", "elétrico" -> regiaoEletrico.remove(p);
        }
    }

    // Posiciona Pokémon no grid e na lista de região
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean setTreinadorNull)
            throws RegiaoInvalidaException {

        if (!posicaoValida(linha, coluna))
            throw new IllegalArgumentException("Posição fora do tabuleiro.");

        if (grid[linha][coluna] != null)
            throw new IllegalArgumentException("Já existe um Pokémon nessa posição.");

        if (!regiaoValida(p.getTipo(), linha, coluna))
            throw new RegiaoInvalidaException("Pokémon do tipo " + p.getTipo() + " não pode ser colocado nessa região!");

        grid[linha][coluna] = p;
        if (setTreinadorNull) p.setTreinador(null);
        p.setEmAmbienteAdverso(false);

        // Adiciona à lista de região
        adicionarNaRegiao(p);
    }

    private void adicionarNaRegiao(Pokemon p) {
        switch (p.getTipo().toLowerCase()) {
            case "agua", "água" -> regiaoAgua.add(p);
            case "floresta" -> regiaoFloresta.add(p);
            case "terra" -> regiaoTerra.add(p);
            case "eletrico", "elétrico" -> regiaoEletrico.add(p);
        }
    }

    // Posiciona aleatoriamente considerando região
    public void posicionarPokemonAleatoriamente(Pokemon p) {
        List<int[]> livresValidas = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null && regiaoValida(p.getTipo(), i, j)) {
                    livresValidas.add(new int[]{i, j});
                }
            }
        }

        if (livresValidas.isEmpty()) {
            throw new NenhumaPosicaoDisponivelException("Nenhuma posição disponível para o Pokémon " + p.getNome());
        }

        int[] pos = livresValidas.get(rnd.nextInt(livresValidas.size()));
        grid[pos[0]][pos[1]] = p;
        p.setEmAmbienteAdverso(false);
        adicionarNaRegiao(p);
    }

    // Retorna algum Pokémon selvagem aleatório
    public Pokemon pegarPokemonSelvagemAleatorio() {
        List<Pokemon> selvagens = new ArrayList<>();
        for (Pokemon p : regiaoAgua) if (p.isSelvagem()) selvagens.add(p);
        for (Pokemon p : regiaoFloresta) if (p.isSelvagem()) selvagens.add(p);
        for (Pokemon p : regiaoTerra) if (p.isSelvagem()) selvagens.add(p);
        for (Pokemon p : regiaoEletrico) if (p.isSelvagem()) selvagens.add(p);
        if (selvagens.isEmpty())return null;
        return selvagens.get(rnd.nextInt(selvagens.size()));
    }

    // Verifica se há algum Pokémon selvagem
    public boolean temPokemonsSelvagens() {
        for (Pokemon p : regiaoAgua) if (p.isSelvagem()) return true;
        for (Pokemon p : regiaoFloresta) if (p.isSelvagem()) return true;
        for (Pokemon p : regiaoTerra) if (p.isSelvagem()) return true;
        for (Pokemon p : regiaoEletrico) if (p.isSelvagem()) return true;
        return false;
    }

    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && coluna >= 0 && linha < tamanho && coluna < tamanho;
    }

    public boolean estaLivre(int linha, int coluna) {
        return posicaoValida(linha, coluna) && grid[linha][coluna] == null;
    }

    public int[] localizar(Pokemon alvo) {
        if (alvo == null) return null;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == alvo) return new int[]{i, j};
            }
        }
        return null;
    }

    public boolean moverPokemon(Pokemon p, int novaLinha, int novaColuna) {
        int[] pos = localizar(p);
        if (pos == null) return false;
        if (!estaLivre(novaLinha, novaColuna)) return false;
        if (!regiaoValida(p.getTipo(), novaLinha, novaColuna)) return false;
        grid[pos[0]][pos[1]] = null;
        grid[novaLinha][novaColuna] = p;
        return true;
    }

    public boolean podeFicar(Pokemon p, int linha, int coluna) {
        return regiaoValida(p.getTipo(), linha, coluna);
    }

    private boolean regiaoValida(String tipo, int linha, int coluna) {
        String t = tipo == null ? "" : tipo.toLowerCase();
        int n = tamanho;

        if (t.equals("agua") || t.equals("água"))
            return linha < n / 2 && coluna < n / 2;
        if (t.equals("floresta"))
            return linha < n / 2 && coluna >= n / 2;
        if (t.equals("terra"))
            return linha >= n / 2 && coluna < n / 2;
        if (t.equals("eletrico") || t.equals("elétrico"))
            return linha >= n / 2 && coluna >= n / 2;

        return true;
    }

    // DEBUG
    public void exibir() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null) System.out.print("[   ] ");
                else System.out.print("[" + grid[i][j].getTipo().charAt(0) + "] ");
            }
            System.out.println();
        }
    }
}
