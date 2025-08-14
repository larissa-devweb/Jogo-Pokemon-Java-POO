/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

import java.util.Random;

public class PokemonTerra extends Pokemon {
    public PokemonTerra(String nome) {
        super(nome,"Terra");
    }

    
    @Override
    public int calcularDano() {
        Random rand = new Random();
        int forca = 0;
        int dano = (forca + rand.nextInt(nivel + 1));
        if ((nivel + experiencia) % 2 != 0) {
            dano *= 2; // turno ímpar = força dobrada
        }
        return dano;
    }
}
