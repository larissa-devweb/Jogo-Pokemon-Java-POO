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

    // NOVO: Elétrico → chance de paralisar adversário
    @Override
    public int calcularDano() {
        Random rand = new Random();
        if (rand.nextInt(100) < 30) {
            boolean adversarioParalisado = true;
        }
        return super.calcularDano();
    }
}
