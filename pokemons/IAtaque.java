
package jogopokemon.pokemons;
import jogopokemon.Pokemon;

/**
 * Interface de ataque (PDF: “Pokémons podem batalhar”).
 *  Mantida em arquivo próprio IAtaque.java (conserta o erro “public class/interface
 *   deve estar em arquivo com mesmo nome”).
 */
// Interface de ataque (PDF: "Pokémons podem batalhar"; precisamos de um contrato de ataque)
public interface IAtaque { // define um "contrato" de ataque (abstração por interface)
    // Método de ataque: alvo + número do turno (PDF: efeitos por turno - Terra usa turno ímpar)
    int atacar(Pokemon alvo, int turno); // retorna o dano efetivo aplicado no alvo
    int calcularDano(Pokemon atacante, Pokemon alvo);
}

