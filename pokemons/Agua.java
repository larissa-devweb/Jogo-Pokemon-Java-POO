/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Agua extends Pokemon {
    public Agua(String nome) {
        super(nome, 8, 0, 1, 100, true);
    }

    @Override
    public int calcularDano() {
        Random rand = new Random();
        int danoBase = (forca + rand.nextInt(nivel + 1));
        return danoBase; // redução de dano será tratada na defesa
    }
}
