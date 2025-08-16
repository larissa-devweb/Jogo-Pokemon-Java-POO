package jogopokemon.pokemons;

import jogopokemon.Pokemon;

/**
 * Pokémon Água
 */
public class Agua extends Pokemon {

    // Construtor padrão (selvagem por padrão = true)
    public Agua(String nome) {
        // hp = 100 (padrão), forca = 8 (exemplo)
        super(nome, "Agua", 100, 8);
        setSelvagem(true);
    }

    // Construtor que permite controlar se é selvagem
    public Agua(String nome, boolean selvagem) {
        super(nome, "Agua", 100, 8);
        setSelvagem(selvagem);
    }

    @Override
    public int atacar(jogopokemon.Pokemon alvo) {
        // Dano base: força + bônus por nível
        int dano = getForca() + (int) (Math.random() * (getNivel() + 1));
        alvo.receberDano(dano);

        // Se estiver em ambiente adverso (ex.: fora da água), reduz um pouco
        if (isEmAmbienteAdverso()) {
            // aplica penalidade simbólica (10% a menos)
            int penalidade = (int) Math.round(dano * 0.10);
            alvo.receberDano(-penalidade); // retirar parte do dano aplicado (ajusta)
            dano -= penalidade;
        }

        return dano;
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }
}
