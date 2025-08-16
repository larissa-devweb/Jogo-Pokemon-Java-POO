package jogopokemon;

import java.util.ArrayList;

public class Treinador {
    private final String nome;
    private final ArrayList<Pokemon> mochila;
    private final ArrayList<Pokemon> time;
    private final int pontuacao;

    public Treinador(String nome) {
        this.nome = nome;
        this.mochila = new ArrayList<>();
        this.time = new ArrayList<>();
        this.pontuacao = 0;
    }

    // Capturar Pokémon selvagem
    public boolean capturarPokemon(Pokemon p) {
        if (p.isSelvagem()) {
            mochila.add(p);
            time.add(p);
            p.setSelvagem(false);
            System.out.println(nome + " capturou " + p.getNome() + "!");
            return true;
        }
        return false;
    }

    // NOVO: mostrar mochila
    public void mostrarMochila() {
        System.out.println("\n=== Mochila de " + nome + " ===");
        if (mochila.isEmpty()) {
            System.out.println("Nenhum Pokémon capturado.");
        } else {
            for (Pokemon p : mochila) {
                System.out.println("- " + p.getNome() + " [Nv " + p.getNivel() + "] Exp: " + p.getExperiencia());
            }
        }
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPokemon(Pokemon pokemon) {

    }
}
