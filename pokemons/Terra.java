package jogopokemon.pokemons;

import jogopokemon.Pokemon;

// Pokémon Terra
public class Terra extends Pokemon {
    public Terra(String nome) {
        super(nome, "Terra", 9); // Força padrão 9
    }

    public Terra(String nome, boolean selvagem) {
        super(nome, "Terra", 9);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        int dano = getForca() + getNivel();
        alvo.receberDano(dano);
        return dano;
    }
}
