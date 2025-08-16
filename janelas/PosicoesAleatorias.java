package jogopokemon.janelas;
import jogopokemon.Tabuleiro;
import jogopokemon.RegiaoInvalidaException;
import jogopokemon.pokemons.*;

public class PosicoesAleatorias {
    public static void inicializar(Tabuleiro tabuleiro) {
        try {
            tabuleiro.posicionarPokemon(0,0,new Agua("Squirtle", true), false);
            tabuleiro.posicionarPokemon(1,2,new Eletrico("Raichu", true), false);
            tabuleiro.posicionarPokemon(2,1,new Floresta("Bulbasaur", true), false);
        } catch (RegiaoInvalidaException e) {
            e.printStackTrace();
        }
    }
}
