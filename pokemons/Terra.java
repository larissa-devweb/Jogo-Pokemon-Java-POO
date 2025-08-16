package jogopokemon.pokemons;

import jogopokemon.Pokemon;

/**
 * Pokémon do tipo Terra
 */
public class Terra extends Pokemon {

    public Terra(String nome) {
        super(nome, "Terra", 110, 9); // HP = 110, Força = 9
        setSelvagem(true);
    }

    public Terra(String nome, boolean selvagem) {
        super(nome, "Terra", 110, 9);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(jogopokemon.Pokemon alvo) {
        int dano = getForca() + (int)(Math.random() * (getNivel() + 1));
        alvo.receberDano(dano);

        // Terra pode reduzir o dano recebido no próximo turno
        if (Math.random() < 0.25) { // 25% de chance
            setEmAmbienteAdverso(false); // “blindagem” temporária
        }

        return dano;
    }
}
