package jogopokemon.pokemons;

import java.util.Random;

/**
 * Tipo Floresta
 * ✔ Regeneração: cura 20% do dano que causar (implementado na Batalha2Pokemons
 *   logo após aplicar o dano, usando hpMax que agora existe em Pokemon).
 */
public class Floresta extends Pokemon {

    public Floresta(String nome) {
        super(nome, 28, 6, 1, 0, true, "Floresta");
    }

    public Floresta(String nome, boolean selvagem) {
        super(nome, 28, 6, 1, 0, selvagem, "Floresta");
    }
    // NOVO: Floresta → regenera 10% da energia ao atacar
    @Override
    public int calcularDano() {
        int dano = super.calcularDano();
        this.energia += (int)(dano * 0.1);
        return dano;
    }
}
