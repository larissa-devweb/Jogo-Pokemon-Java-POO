package jogopokemon.pokemons;

import jogopokemon.Pokemon;

// Pokémon Floresta
public class Floresta extends Pokemon {
    public Floresta(String nome) {
        super(nome, "Floresta", 7); // Força padrão 7
    }

    public Floresta(String nome, boolean selvagem) {
        super(nome, "Floresta", 7);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        int dano = getForca() + getNivel();
        alvo.receberDano(dano);
        return dano;
    }
}
