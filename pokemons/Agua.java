package jogopokemon.pokemons;

import jogopokemon.Pokemon;

// Pokémon Água
public class Agua extends Pokemon {
    public Agua(String nome) {
        super(nome, "Agua", 8); // Força padrão 8
    }

    public Agua(String nome, boolean selvagem) {
        super(nome, "Agua", 8);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        // Pode ter efeito especial ou dano base diferente
        int dano = getForca() + getNivel();
        alvo.receberDano(dano);
        return dano;
    }
}
