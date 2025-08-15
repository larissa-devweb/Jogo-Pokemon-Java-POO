package jogopokemon;
//package jogopokemon.pokemons;

// Interface de ataque: contrato mínimo para qualquer Pokémon batalhar.
// Mantém a assinatura citada na sua versão (inclui getTreinador()).
public interface IAtaque {
    // Cada Pokémon deve saber calcular seu dano (polimorfismo).
    int calcularDano();

    // Permite saber a quem pertence o Pokémon (ou null se selvagem).
    Treinador getTreinador();
}
