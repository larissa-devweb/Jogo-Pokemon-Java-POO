package jogopokemon.pokemons;

// Interface de ataque (do PDF: “Pokémons podem batalhar”, precisamos de um contrato para o ataque)
public interface IAtaque {
    int calcularDano(); // Qualquer Pokémon deve saber calcular seu dano
}
