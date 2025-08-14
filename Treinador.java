package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.ArrayList;

// Classe que representa o treinador
public class Treinador {
    private final String nome;                 // Nome do treinador
    private final ArrayList<Pokemon> mochila;  // Lista de Pokémon capturados
    private final Pokedex pokedex;             // Registro de espécies
    private int linha;
    private int coluna;

    public Treinador(String nome) {
        this.nome = nome;                // Define nome
        this.mochila = new ArrayList<>();// Inicializa mochila
        this.pokedex = new Pokedex();    // Inicializa Pokédex
    }

    // Adiciona Pokémon à mochila e registra na Pokédex
    public void adicionarPokemon(Pokemon p) {
        p.setSelvagem(false);        // Agora pertence ao treinador
        mochila.add(p);              // Guarda na mochila
        pokedex.registrar(p);        // Registra na Pokédex
        System.out.println(p.getNome() + " foi adicionado à mochila!");
    }

    // Mostra todos os Pokémon na mochila
    public void mostrarMochila() {
        if (mochila.isEmpty()) {
            System.out.println("Mochila vazia.");
        } else {
            System.out.println("=== MOCHILA ===");
            for (Pokemon p : mochila) {
                System.out.println(p.getNome() + " - Tipo: " + p.getTipo() +
                                   " - Nível: " + p.getNivel() +
                                   " - Exp: " + p.getExperiencia());
            }
        }
    }

    // Mostra Pokédex
    public void mostrarPokedex() {
        pokedex.mostrar();
    }

    Iterable<Pokemon> getPokemons() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Retorna o nome do treinador
    public String getNome() {
        return nome;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
