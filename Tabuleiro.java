package jogopokemon;

import jogopokemon.excecoes.NenhumaPosicaoDisponivelException;
import jogopokemon.excecoes.RegiaoInvalidaException;
import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import jogopokemon.pokemons.Floresta;
import jogopokemon.pokemons.Terra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final Pokemon[][] grid;
    private final Random rnd = new Random();

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

    public void removerPokemon(int linha, int coluna) {
        if (posicaoValida(linha, coluna)) grid[linha][coluna] = null;
    }

    /** Posiciona p em (linha,coluna) verificando a região por tipo. */
    public void posicionarPokemon(int linha, int coluna, Pokemon p, boolean setTreinadorNull)
            throws RegiaoInvalidaException {

        if (!posicaoValida(linha, coluna))
            throw new IllegalArgumentException("Posição fora do tabuleiro.");

        if (grid[linha][coluna] != null)
            throw new IllegalArgumentException("Já existe um Pokémon nessa posição.");

        if (!regiaoValida(p.getTipo(), linha, coluna))
            throw new RegiaoInvalidaException("Pokémon do tipo " + p.getTipo() + " não pode ser colocado nessa região!");

        grid[linha][coluna] = p;

        // Mantém compatibilidade com chamadas antigas
        if (setTreinadorNull) p.setTreinador(null);

        // Ao ser posicionado corretamente, não está em ambiente adverso
        p.setEmAmbienteAdverso(false);
    }

    /** Coloca p aleatoriamente numa casa válida para o tipo. */
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
    }

    /** DEBUG/console: imprime o tabuleiro em texto. */
    public void exibir() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == null) System.out.print("[   ] ");
                else System.out.print("[" + grid[i][j].getTipo().charAt(0) + "] ");
            }
            System.out.println();
        }
    }

    /** Há algum Pokémon selvagem? */
    public boolean temPokemonsSelvagens() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) return true;
            }
        }
        return false;
    }

    /** Retorna um Pokémon selvagem aleatório (ou null se não houver). */
    public Pokemon pegarPokemonSelvagemAleatorio() {
        List<Pokemon> selvagens = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = grid[i][j];
                if (p != null && p.isSelvagem()) selvagens.add(p);
            }
        }
        if (selvagens.isEmpty()) return null;
        return selvagens.get(rnd.nextInt(selvagens.size()));
    }

    // ---------- ALIASES p/ compatibilidade com chamadas antigas ----------
    public Pokemon pokemonAleatorioSelvagem() { return pegarPokemonSelvagemAleatorio(); }
    public Pokemon getPokemonSelvagemAleatorio() { return pegarPokemonSelvagemAleatorio(); }

    // ---------- Utilidades usadas pelo movimento automático ----------
    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && coluna >= 0 && linha < tamanho && coluna < tamanho;
    }

    public boolean estaLivre(int linha, int coluna) {
        return posicaoValida(linha, coluna) && grid[linha][coluna] == null;
    }

    /** Localiza coordenadas de um Pokémon no grid. Retorna {linha, coluna} ou null. */
    public int[] localizar(Pokemon alvo) {
        if (alvo == null) return null;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (grid[i][j] == alvo) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /** Move o Pokémon (se encontrado) p/ (novaLinha,novaColuna) quando a casa estiver livre e válida. */
    public boolean moverPokemon(Pokemon p, int novaLinha, int novaColuna) {
        int[] pos = localizar(p);
        if (pos == null) return false;
        if (!estaLivre(novaLinha, novaColuna)) return false;
        if (!regiaoValida(p.getTipo(), novaLinha, novaColuna)) return false; // respeita quadrantes

        grid[pos[0]][pos[1]] = null;
        grid[novaLinha][novaColuna] = p;
        return true;
    }

    /** Wrapper público para checagem de região por tipo (útil p/ outras classes). */
    public boolean podeFicar(Pokemon p, int linha, int coluna) {
        return regiaoValida(p.getTipo(), linha, coluna);
    }

    // ---------- Regras de quadrantes por tipo ----------
    private boolean regiaoValida(String tipo, int linha, int coluna) {
        String t = tipo == null ? "" : tipo.toLowerCase();
        int n = tamanho;

        // ÁGUA: quadrante superior-esquerdo
        if (t.equals("agua") || t.equals("água"))
            return linha >= 0 && linha < n / 2 && coluna >= 0 && coluna < n / 2;

        // FLORESTA: superior-direito
        if (t.equals("floresta"))
            return linha >= 0 && linha < n / 2 && coluna >= n / 2 && coluna < n;

        // TERRA: inferior-esquerdo
        if (t.equals("terra"))
            return linha >= n / 2 && linha < n && coluna >= 0 && coluna < n / 2;

        // ELÉTRICO: inferior-direito
        if (t.equals("eletrico") || t.equals("elétrico"))
            return linha >= n / 2 && linha < n && coluna >= n / 2 && coluna < n;

        // fallback (se vier tipo desconhecido, deixa colocar em qualquer lugar)
        return true;
    }
}
