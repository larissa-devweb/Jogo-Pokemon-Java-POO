package jogopokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private List<Pokemon> capturados;

    public Pokedex() {
        this.capturados = new ArrayList<>();
    }

    // NOVO: adiciona um Pokémon capturado
    public void adicionarPokemon(Pokemon p) {
        if (!capturados.contains(p)) {
            capturados.add(p);
        }
    }

    // NOVO: retorna lista formatada dos Pokémon
    public String listarPokemons() {
        StringBuilder sb = new StringBuilder();
        sb.append("📖 Pokédex:\n");
        if (capturados.isEmpty()) {
            sb.append("Nenhum Pokémon capturado ainda.\n");
        } else {
            for (Pokemon p : capturados) {
                sb.append("- ").append(p.getNome())
                        .append(" [").append(p.getTipo()).append("] ")
                        .append(" | Nível: ").append(p.getNivel())
                        .append(" | Exp: ").append(p.getExperiencia())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    // NOVO: quantidade de Pokémon registrados
    public int totalCapturados() {
        return capturados.size();
    }
}
