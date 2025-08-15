/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;


import java.util.Random;

public class Agua extends Pokemon {
    public Agua(String nome, boolean selvagem) {
        // super() chama o construtor da classe mãe
        // nome, ataque=8, defesa=5, nivel=1, vida=100, selvagem, tipo="Água"
        super(nome, 8, 5, 1, 100, selvagem, "Água");
    }

    public Agua(String nome) {
        // super() chama o construtor da classe mãe
        // nome, ataque=8, defesa=5, nivel=1, vida=100, selvagem=true, tipo="Água"
        super(nome, 8, 5, 1, 100, true, "Água");
    }

    @Override
    public int calcularDano() {
        // Polimorfismo: cálculo diferente para tipo Água
        // Tipo água tem ataque mais constante
        Random rand = new Random();
        int variacao = rand.nextInt(5); // até +4
        int ataque;
        return ataque + nivel + variacao;
    }
}