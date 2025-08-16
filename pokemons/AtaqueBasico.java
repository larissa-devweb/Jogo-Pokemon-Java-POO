package jogopokemon.pokemons;

import jogopokemon.Pokemon;

public class AtaqueBasico implements IAtaque {
    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        double fatorTipo = 1.0; // pode criar lógica de vantagem de tipo
        int bonusHabilidade = 0; // bônus especial
        return (atacante.getForca() + (int)(Math.random() * (atacante.getNivel() + 1)))
                * (int)fatorTipo + bonusHabilidade;
    }
}
