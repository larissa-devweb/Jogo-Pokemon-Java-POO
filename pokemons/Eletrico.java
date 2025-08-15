/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Eletrico extends Pokemon {
    private boolean paralisou = false;

    public Eletrico(String nome, boolean selvagem) {
        super(nome, "eletrico", selvagem);
    }

    public Eletrico(String nome) {
        super(nome, "eletrico");
    }

    @Override
    public int calcularDano() {
        // Polimorfismo: elétrico tem mais variação de dano
        Random rand = new Random();
        int variacao = rand.nextInt(8); // até +7
        return ataque + nivel + variacao;
    }
}
