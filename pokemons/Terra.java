/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Terra extends Pokemon {
    public Terra(String nome, boolean selvagem) {
        super(nome, "Terra", selvagem);
    }

    public Terra(String nome) {
        super(nome, "Terra");
    }

    @Override
    public int calcularDano() {
        // Polimorfismo: Terra foca mais em defesa, ataque menos explosivo
        Random rand = new Random();
        int variacao = rand.nextInt(3); // até +2
        return ataque + (nivel / 2) + variacao;
    }
}