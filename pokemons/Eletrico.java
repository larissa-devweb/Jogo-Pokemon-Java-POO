package jogopokemon.pokemons;

import jogopokemon.Pokemon;

// Pokémon Elétrico
public class Eletrico extends Pokemon {
    public Eletrico(String nome) {
        super(nome, "Eletrico", 10); // Força padrão 10
    }

    public Eletrico(String nome, boolean selvagem) {
        super(nome, "Eletrico", 10);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        int dano = getForca() + getNivel();
        alvo.receberDano(dano);
        // Chance de paralisar 20% (exemplo)
        if (Math.random() < 0.2) {
            alvo.paralisar();
        }
        return dano;
    }
}
