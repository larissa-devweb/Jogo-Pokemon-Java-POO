/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Eletrico extends Pokemon {
    private boolean paralisou = false;

    public Eletrico(String nome) {
        super(nome,"eletrico");
    }

    @Override
    public int calcularDano() {
        Random rand = new Random();
        paralisou = rand.nextInt(100) < 25; // 25% de chance de paralisar
        int forca = 0;
        return (forca + rand.nextInt(nivel + 1));
    }

    public boolean paralisouAdversario() {
        return paralisou;
    }
}
