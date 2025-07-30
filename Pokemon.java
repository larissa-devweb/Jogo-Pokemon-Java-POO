/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

public abstract class Pokemon implements IAtaque {
    protected String nome;
    protected int forca;
    protected int experiencia;
    protected int nivel;
    protected int energia;
    protected boolean selvagem;

    public Pokemon(String nome, int forca, int experiencia, int nivel, int energia, boolean selvagem) {
        this.nome = nome;
        this.forca = forca;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.energia = energia;
        this.selvagem = selvagem;
    }

    public void ganharExperiencia(int pontos) {
        this.experiencia += pontos;
        if (this.experiencia >= nivel * 10) {
            this.nivel++;
        }
    }

    public void restaurarEnergia() {
        this.energia = 100;
    }

    public String getNome() {
        return nome;
    }

    public int getEnergia() {
        return energia;
    }

    public void receberDano(int dano) {
        this.energia -= dano;
        if (this.energia < 0) this.energia = 0;
    }

    public boolean isSelvagem() {
        return selvagem;
    }

    public void setSelvagem(boolean selvagem) {
        this.selvagem = selvagem;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getForca() {
        return forca;
    }

    String getTipo() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
