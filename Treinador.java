package jogopokemon;

import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private final String nome;
    private final List<Pokemon> pokemons;
    private int pontuacao;
    private final Pokedex pokedex;

    public Treinador(String nome) {
        this.nome = nome;
        this.pokemons = new ArrayList<>();
        this.pontuacao = 0;
        this.pokedex = new Pokedex();
    }

    public String getNome() { return nome; }
    public List<Pokemon> getPokemons() { return pokemons; }
    public int getPontuacao() { return pontuacao; }
    public Pokedex getPokedex() { return pokedex; }

    public void adicionarPokemon(Pokemon p) {
        pokemons.add(p);
        pontuacao += 10; // cada captura dá 10 pontos
        pokedex.adicionarPokemon(p);
    }

    public void removerPokemon(Pokemon p) {
        pokemons.remove(p);
    }

    public void mostrarPontuacao() {
        System.out.println(nome + " tem " + pontuacao + " pontos.");
    }

    // Adiciona pontos à pontuação do treinador
    public void adicionarPontuacao(int pts) {
        pontuacao += pts;
    }
}
