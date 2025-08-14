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
        Random rand = new Random();
        int forca = 0;
        int dano = (forca + rand.nextInt(nivel + 1));
        this.energia += 5; // regenera vida ao atacar
        if (this.energia > 100) this.energia = 100;
        return dano;
    }
}
