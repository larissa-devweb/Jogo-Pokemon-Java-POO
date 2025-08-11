/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Eletrico extends Pokemon {
    private boolean paralisou = false;

    public Eletrico(String nome) {
        super(nome, 9, 0, 1, 100, true);
    }

    @Override
    public int calcularDano() {
        Random rand = new Random();
        paralisou = rand.nextInt(100) < 25; // 25% de chance de paralisar
        return (forca + rand.nextInt(nivel + 1));
    }

    public boolean paralisouAdversario() {
        return paralisou;
    }
}
