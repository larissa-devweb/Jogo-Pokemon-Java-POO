package jogopokemon;

import jogopokemon.pokemons.Agua;
import jogopokemon.pokemons.Eletrico;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(5);
        Treinador ash = new Treinador("Ash");

        try {
            tabuleiro.posicionarPokemon(2, 2, new Agua("Squirtle"), true);
            tabuleiro.posicionarPokemon(4, 1, new Eletrico("Pikachu"), true);
        } catch (RegiaoInvalidaException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao posicionar Pokémon: " + e.getMessage());
        }

        // Cria GUI
        PokemonGui gui = new PokemonGui(tabuleiro, ash);

        // Inicia thread de movimento automático
        MovimentoAutomatico movimento = new MovimentoAutomatico(tabuleiro, gui);
        movimento.start();
    }
}
