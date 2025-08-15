package jogopokemon.pokemons;

import java.util.Random;

/**
 * Tipo Água
 * Construtores sobrecarregados (1 arg e 2 args) para casar com usos diferentes.
 * Redução de dano recebido (PDF: “redução de dano em ambientes adversos”).
 */
public class Agua extends Pokemon {

    // ✔ construtor básico (selvagem por padrão)
    public Agua(String nome) {
        super(nome, 30, 5, 1, 0, true, "Água");
    }

    // ✔ construtor opcional usado por loaders que querem definir selvagem/dono
    public Agua(String nome, boolean selvagem) {
        super(nome, 30, 5, 1, 0, selvagem, "Água");
    }

    @Override
    public int calcularDano() {
        // Exemplo do PDF: random(força) * nível + xp
        int xp = 0;
        return new Random().nextInt(forca + 1) * nivel + xp;
    }

    // NOVO: Água → reduz dano recebido (simulação: -20%)
    @Override
    public void receberDano(int dano) {
        super.receberDano((int)(dano * 0.8));
    }
}

