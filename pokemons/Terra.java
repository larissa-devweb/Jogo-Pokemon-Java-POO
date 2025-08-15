package jogopokemon.pokemons;

import java.util.Random;

/**
 * Tipo Terra
 * ✔ Bônus de “força dobrada em turno ímpar” será tratado na Batalha2Pokemons
 *   (evita dependência cruzada do pacote pokemons para a classe de batalha).
 */
public class Terra extends Pokemon {

    public Terra(String nome) {
        super(nome, 35, 4, 1, 0, true, "Terra");
    }

    public Terra(String nome, boolean selvagem) {
        super(nome, 35, 4, 1, 0, selvagem, "Terra");
    }

    // NOVO: Terra → dobra a força em turno ímpar
    @Override
    public int calcularDano() {
        int turno=0;
        turno++;
        int dano = super.calcularDano();
        if (turno % 2 == 1) {
            dano *= 2;
        }
        return dano;
    }
}
