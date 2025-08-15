package jogopokemon.pokemons;

import jogopokemon.Treinador;

/**
 * Interface de ataque (PDF: “Pokémons podem batalhar”).
 * ✔ Mantida em arquivo próprio IAtaque.java (conserta o erro “public class/interface
 *   deve estar em arquivo com mesmo nome”).
 */
public interface IAtaque {

    // Cada Pokémon sabe calcular seu dano (as subclasses implementam).
    int calcularDano();

    // Usamos para imprimir “Treinador: X” na batalha.
    Treinador getTreinador();
}

