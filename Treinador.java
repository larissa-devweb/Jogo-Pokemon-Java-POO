package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.ArrayList;

/**
 * Treinador com mochila e Pokédex. Mantém posição para a GUI (linha/coluna).
 */
public class Treinador {

    private String nome;
    private ArrayList<Pokemon> mochila;
    private Pokedex pokedex;

    // Posição usada pela JanelaJogo
    private int linha;
    private int coluna;

    public Treinador(String nome) {
        this.nome = nome;
        this.mochila = new ArrayList<>();
        this.pokedex = new Pokedex();
        this.linha = 0;
        this.coluna = 0;
    }

    // ===== Posição para a GUI =====
    public int getLinha() { return linha; }
    public void setLinha(int linha) { this.linha = linha; }
    public int getColuna() { return coluna; }
    public void setColuna(int coluna) { this.coluna = coluna; }

    public String getNome() { return nome; }

    public ArrayList<Pokemon> getMochila() { return mochila; }
    public Pokedex getPokedex() { return pokedex; }

    // Adiciona na mochila e registra na Pokédex.
    public void adicionarPokemonNaMochila(Pokemon pokemon) {
        if (!mochila.contains(pokemon)) {
            mochila.add(pokemon);
        }
        pokedex.registrar(pokemon);
        System.out.println(pokemon.getNome() + " adicionado à mochila de " + nome + ".");
    }

    // Soma a XP de todos os Pokémon, para o critério de vencedor do PDF.
    public int getTotalExperiencia() {
        int total = 0;
        for (Pokemon p : mochila) {
            total += p.getExperiencia();
        }
        return total;
    }

    public void mostrarMochila() {
        if (mochila.isEmpty()) {
            System.out.println("Mochila vazia.");
            return;
        }
        System.out.println("=== Mochila de " + nome + " ===");
        for (Pokemon p : mochila) {
            System.out.println("- " + p.getNome() + " | Nível " + p.getNivel() + " | XP " + p.getExperiencia() + " | HP " + p.getHp());
        }
    }
}
