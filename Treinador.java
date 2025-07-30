/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

import java.util.ArrayList;

// Classe que representa o treinador (jogador)
public class Treinador {
    private final String nome;
    private final ArrayList<Pokemon> mochila; // Lista de Pokémon capturados

    // Construtor do treinador, já inicializa a mochila vazia
    public Treinador(String nome) {
        this.nome = nome;
        this.mochila = new ArrayList<>();
    }

    // Retorna o nome do treinador
    public String getNome() {
        return nome;
    }

    // Adiciona um Pokémon capturado à mochila
    public void adicionarPokemon(Pokemon p) {
        mochila.add(p);
        System.out.println(p.getNome() + " foi adicionado à mochila de " + nome);
    }

    // Exibe os Pokémons que estão na mochila
    public void mostrarMochila() {
        System.out.println("Mochila do treinador " + nome + ":");

        if (mochila.isEmpty()) {
            System.out.println("  [nenhum Pokémon capturado ainda]");
        } else {
            for (Pokemon p : mochila) {
                System.out.println("  - " + p.getNome() + " (Tipo: " + p.getTipo() + ")");
            }
        }
    }

    // Retorna a quantidade de Pokémons capturados
    public int getQuantidadePokemons() {
        return mochila.size();
    }

    // Retorna a lista completa de Pokémons (para salvar em arquivo, por exemplo)
    public ArrayList<Pokemon> getPokemons() {
        return mochila;
    }

    Iterable<Pokemon> getPokemon() {
        throw new UnsupportedOperationException("Nao suportado.");
    }
}
