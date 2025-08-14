/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;


// Classe abstrata representando qualquer Pokémon
public abstract class Pokemon {
    protected String nome;        // Nome do Pokémon
    protected String tipo;        // Tipo (Água, Fogo, etc.)
    protected boolean selvagem;   // Indica se é selvagem ou de treinador
    protected int nivel;          // Nível atual
    protected int experiencia;    // Pontos de experiência

    // Construtor
    public Pokemon(String nome, String tipo) {
        this.nome = nome;              // Define nome
        this.tipo = tipo;              // Define tipo
        this.selvagem = true;          // Por padrão nasce selvagem
        this.nivel = 1;                // Nível inicial
        this.experiencia = 0;          // Começa sem experiência
    }

    // Getters básicos
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public boolean isSelvagem() { return selvagem; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    // Define se o Pokémon é selvagem ou não
    public void setSelvagem(boolean selvagem) { this.selvagem = selvagem; }

    // Método para ganhar experiência
    public void ganharExperiencia(int pontos) {
        this.experiencia += pontos; // Soma pontos recebidos
        // Se atingiu 100 pontos, sobe de nível
        if (this.experiencia >= 100) {
            subirNivel();
            this.experiencia -= 100; // Reseta o excedente
        }
    }

    // Método para subir de nível
    private void subirNivel() {
        this.nivel++; // Aumenta nível
        System.out.println(this.nome + " subiu para o nível " + this.nivel + "!");
    }

    // Método abstrato para calcular dano (polimorfismo)
    public abstract int calcularDano();

    int getEnergia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void restaurarEnergia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void receberDano(int dano) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
