/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

import java.util.Random;

public class PokemonFloresta extends Pokemon {
    public PokemonFloresta(String nome) {
        super(nome, 7, 0, 1, 100, true);
    }

    @Override
    public int calcularDano() {
        Random rand = new Random();
        int dano = (forca + rand.nextInt(nivel + 1));
        this.energia += 5; // regenera vida ao atacar
        if (this.energia > 100) this.energia = 100;
        return dano;
    }
}
