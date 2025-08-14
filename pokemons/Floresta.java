/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Floresta extends Pokemon {

    private int energia;
    public Floresta(String nome) {
        super(nome,"Floresta");
    }

    @Override
    public int calcularDano() {
        // Polimorfismo: ataque com chance de golpe crítico
        Random rand = new Random();
        boolean critico = rand.nextInt(10) < 2; // 20% de chance
        int base = ataque + nivel;
        return critico ? base * 2 : base;
    }
}
