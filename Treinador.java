package jogopokemon;

import java.util.ArrayList;
import jogopokemon.pokemons.Pokemon;

/**
 * ✔ Adicionados mochila, time e pontuação, get/set de posição.
 * ✔ Criados 'adicionarPokemon' e também 'adicionarPokemonNaMochila' (alias)
 *   para compatibilizar com chamadas antigas.
 */
public class Treinador {
    private String nome;
    private int linha;
    private int coluna;

    private int pontuacao;                 // pontuação do time
    private final ArrayList<Pokemon> time; // time ativo
    private final ArrayList<Pokemon> mochila; // capturados

    public Treinador(String nome) {
        this.nome = nome;
        this.linha = 0;
        this.coluna = 0;
        this.pontuacao = 0;
        this.time = new ArrayList<>();
        this.mochila = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }
    public void setLinha(int linha) { this.linha = linha; }
    public void setColuna(int coluna) { this.coluna = coluna; }

    public int getPontuacao() { return pontuacao; }
    public void adicionarPontuacao(int xp) { this.pontuacao += xp; }

    public ArrayList<Pokemon> getTime() { return time; }
    public ArrayList<Pokemon> getMochila() { return mochila; }

    // ✔ método esperado pela captura
    public void adicionarPokemon(Pokemon p) {
        mochila.add(p);
        time.add(p);
        System.out.println(nome + " adicionou " + p.getNome() + " à mochila/time.");
    }

    // ✔ alias para compatibilizar código legado
    public void adicionarPokemonNaMochila(Pokemon p) {
        adicionarPokemon(p);
    }

    public void mostrarMochila() {
        if (mochila.isEmpty()) {
            System.out.println("Mochila vazia.");
            return;
        }
        System.out.println("Mochila de " + nome + ":");
        for (Pokemon p : mochila) {
            System.out.println("- " + p.getNome() + " (Tipo " + p.getTipo()
                    + ", Nível " + p.getNivel() + ", HP " + p.getHp() + ")");
        }
    }
}
