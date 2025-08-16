package jogopokemon.pokemons;

import jogopokemon.Pokemon;

import java.util.Random;

public class AtaqueNormal implements IAtaque {
    private final Random random = new Random();

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        int dano = atacante.getForca() + random.nextInt(atacante.getNivel() + 1);
        return dano;
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }
}

