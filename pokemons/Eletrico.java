package jogopokemon.pokemons;

import jogopokemon.Pokemon;

/**
 * Pokémon Elétrico
 */
public class Eletrico extends Pokemon {

    public Eletrico(String nome) {
        super(nome, "Eletrico", 100, 10); // hp=100, forca=10
        setSelvagem(true);
    }

    public Eletrico(String nome, boolean selvagem) {
        super(nome, "Eletrico", 100, 10);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(jogopokemon.Pokemon alvo) {
        // dano = força + random até nível
        int dano = getForca() + (int) (Math.random() * (getNivel() + 1));
        alvo.receberDano(dano);

        // 30% de chance de paralisar o alvo por 1 turno
        if (Math.random() < 0.30) {
            // usa o método de paralisação da superclasse
            alvo.paralisar();
        }

        return dano;
    }
}
