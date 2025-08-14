package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.HashSet;
import java.util.Set;

// Classe que armazena as espécies já capturadas
public class Pokedex {
    // Conjunto que armazena nomes sem repetição
    private final Set<String> especiesCapturadas;

    // Construtor: inicializa o conjunto
    public Pokedex() {
        especiesCapturadas = new HashSet<>();
    }

    // Adiciona uma espécie capturada
    public void registrar(Pokemon pokemon) {
        especiesCapturadas.add(pokemon.getNome());
    }

    // Mostra todas as espécies capturadas
    public void mostrar() {
        if (especiesCapturadas.isEmpty()) {
            System.out.println("Pokédex vazia.");
        } else {
            System.out.println("=== POKÉDEX ===");
            for (String especie : especiesCapturadas) {
                System.out.println("- " + especie);
            }
        }
    }

    // Retorna a quantidade de espécies registradas
    public int quantidade() {
        return especiesCapturadas.size();
    }
}
