package jogopokemon.pokemons;

import jogopokemon.Pokemon;

/**
 * Pokémon Floresta (tipo Planta)
 */
public class Floresta extends Pokemon {

    public Floresta(String nome) {
        super(nome, "Floresta", 100, 7); // hp=100, forca=7
        setSelvagem(true);
    }

    public Floresta(String nome, boolean selvagem) {
        super(nome, "Floresta", 100, 7);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(jogopokemon.Pokemon alvo) {
        int dano = getForca() + (int) (Math.random() * (getNivel() + 1));
        alvo.receberDano(dano);

        // Regenera 20% do dano causado para si (arredonda)
        int cura = (int) Math.round(dano * 0.20);
        setHp(getHp() + cura);

        return dano;
    }
}
