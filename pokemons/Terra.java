/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Terra extends Pokemon {
    public Terra(String nome) {
        super(nome, 10, 0, 1, 100, true);
    }

    
    @Override
    public int calcularDano() {
        Random rand = new Random();
        int dano = (forca + rand.nextInt(nivel + 1));
        if ((nivel + experiencia) % 2 != 0) {
            dano *= 2; // turno ímpar = força dobrada
        }
        return dano;
    }
}
