package jogopokemon;

import java.util.ArrayList;

public class Treinador {
    private String nome;
    private ArrayList<Pokemon> pokemons;
    private int pontuacao;

    public Treinador(String nome) {
        this.nome = nome;
        this.pokemons = new ArrayList<>();
        this.pontuacao = 0;
    }

    public String getNome() { return nome; }
    public ArrayList<Pokemon> getPokemons() { return pokemons; }
    public void adicionarPokemon(Pokemon p) { pokemons.add(p); }
    public void adicionarPontuacao(int pts) { pontuacao += pts; }
    public int getPontuacao() { return pontuacao; }

    public Pokemon[] getMochila() {
        return pokemons.toArray(new Pokemon[0]);
    }
}
