/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

import java.util.Random;

public class Agua extends Pokemon {
    public Agua(String nome) {
     // Chama o construtor (nome, tipo) da classe base
        super(nome, "Agua");
    }
    @Override
    public int calcularDano() {
        Random rand = new Random();
        int forca = 0;
        int danoBase = (forca + rand.nextInt(nivel + 1));
        return danoBase; // redução de dano será tratada na defesa
    }
}
