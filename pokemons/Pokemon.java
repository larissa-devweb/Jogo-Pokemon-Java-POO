package jogopokemon.pokemons;

import jogopokemon.Treinador;
import java.util.Random;

public abstract class Pokemon implements IAtaque {
    protected String nome;
    protected int energia;
    protected int forca;
    protected int experiencia;
    protected int nivel;
    protected boolean selvagem;
    protected String tipo;
    protected Treinador treinador;

    public Pokemon(String nome, int energia, int forca, int experiencia, int nivel, boolean selvagem, String tipo) {
        this.nome = nome;
        this.energia = energia;
        this.forca = forca;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.selvagem = selvagem;
        this.tipo = tipo;
        this.treinador = null;
    }

    public String getNome() { return nome; }
    public int getEnergia() { return energia; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public boolean isSelvagem() { return selvagem; }
    public String getTipo() { return tipo; }
    public Treinador getTreinador() { return treinador; }
    public void setTreinador(Treinador t) { this.treinador = t; this.selvagem = false; }

    // NOVO: ataque genérico da regra do PDF
    @Override
    public int calcularDano() {
        Random rand = new Random();
        return (rand.nextInt(forca + 1)) * nivel + experiencia;
    }

    // NOVO: Pokémon perde energia até 0 → "desmaia"
    public void receberDano(int dano) {
        energia -= dano;
        if (energia < 0) energia = 0;
    }

    public boolean estaDerrotado() {
        return energia <= 0;
    }

    // NOVO: ganhar experiência e possivelmente subir de nível
    public void ganharExperiencia(int pontos) {
        experiencia += pontos;
        if (experiencia >= 100) {
            nivel++;
            experiencia -= 100; // reseta o excedente
        }
    }
}
