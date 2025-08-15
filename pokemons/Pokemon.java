/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.pokemons;

// Classe abstrata para representar qualquer Pokémon (do PDF: “Pokémons têm tipos e características”)
public abstract class Pokemon implements IAtaque {

    // Nome do Pokémon (identificação na Pokédex)
    protected String nome;

    // Pontos de ataque base (usado no cálculo de dano)
    protected int ataque;

    // Pontos de defesa base (reduzem o dano recebido)
    protected int defesa;

    // Nível do Pokémon (do PDF: “treinar seu time de Pokémon” → nível pode aumentar com XP)
    protected int nivel;

    // Pontos de vida
    protected int vida;

    // Experiência acumulada (do PDF: “O Pokémon vencedor ganha pontos de experiência”)
    protected int experiencia;

    // Define se é selvagem ou de treinador (do PDF: “Quando um Pokémon não pertence a algum Treinador...”)
    protected boolean selvagem;

    // Tipo do Pokémon (ex.: Água, Terra, Elétrico...)
    protected String tipo;

    // Construtor para inicializar o Pokémon
    public Pokemon(String nome, int ataque, int defesa, int nivel, int vida, boolean selvagem, String tipo) {
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.vida = vida;
        this.experiencia = 0; // Começa com 0 XP
        this.selvagem = selvagem;
        this.tipo = tipo;
    }

    public Pokemon(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Getters e Setters para acessar atributos (boas práticas e encapsulamento)
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public boolean isSelvagem() { return selvagem; }
    public int getVida() { return vida; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    // Método para aumentar experiência (PDF: “O Pokémon vencedor ganha pontos de experiência”)
    public void ganharExperiencia(int pontos) {
        experiencia += pontos;
        // Regra: a cada 100 XP, sobe 1 nível
        if (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
            System.out.println(nome + " subiu para o nível " + nivel + "!");
        }
    }

    // Método para receber dano durante batalhas
    public void receberDano(int dano) {
        // Dano efetivo é reduzido pela defesa
        int danoEfetivo = Math.max(0, dano - defesa);
        vida -= danoEfetivo;
        if (vida < 0) vida = 0; // Não deixar vida negativa
    }

    // Verifica se o Pokémon está vivo
    public boolean estaVivo() {
        return vida > 0;
    }

    // Método abstrato para cálculo de dano (polimorfismo: cada tipo implementa de forma diferente)
    @Override
    public abstract int calcularDano();
}
