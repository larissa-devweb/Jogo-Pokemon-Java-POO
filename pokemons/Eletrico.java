package jogopokemon.pokemons;

import java.util.Random;

/**
 * Tipo Elétrico
 * Chance de paralisar o adversário 1 turno será aplicada na Batalha2Pokemons
 *   (evita acoplamento com o “alvo” dentro de calcularDano()).
 */
public class Eletrico extends Pokemon {

    public Eletrico(String nome) {
        super(nome, 25, 7, 1, 0, true, "Elétrico");
    }

    public Eletrico(String nome, boolean selvagem) {
        super(nome, 25, 7, 1, 0, selvagem, "Elétrico");
    }

    @Override
    public int calcularDano() {
        return new Random().nextInt(forca + 1) * nivel + xp;
    }
}
