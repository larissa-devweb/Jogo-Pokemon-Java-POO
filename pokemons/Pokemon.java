package jogopokemon.pokemons;

import java.util.Random;
import jogopokemon.IAtaque;
import jogopokemon.Treinador;

public abstract class Pokemon implements IAtaque {
    protected String nome;
    protected int hp;        // Adicionado: energia
    protected int forca;     //  Adicionado: força
    protected int nivel;     //  Adicionado: nível
    protected int xp;        //  Adicionado: pontos de experiência
    protected boolean selvagem;
    protected String tipo;
    protected Treinador treinador; //  Adicionado: referência ao dono

    public Pokemon(String nome, int hp, int forca, int nivel, int xp, boolean selvagem, String tipo) {
        this.nome = nome;
        this.hp = hp;
        this.forca = forca;
        this.nivel = nivel;
        this.xp = xp;
        this.selvagem = selvagem;
        this.tipo = tipo;
        this.treinador = null;
    }

    public String getNome() {
        return nome;
    }

    public int getHp() {
        return hp;
    }

    public void receberDano(int dano) {
        hp -= dano;
        if (hp < 0) hp = 0;
    }

    // ✅ Adicionado: aumentar XP e subir nível se necessário
    public void aumentarExperiencia(int ganho) {
        xp += ganho;
        if (xp >= nivel * 10) { // exemplo: sobe de nível a cada 10*nivel pontos
            nivel++;
            forca += 2; // aumenta a força ao subir de nível
            hp += 5;    // recupera/ganha mais energia
            System.out.println(nome + " subiu para o nível " + nivel + "!");
        }
    }

    public boolean isSelvagem() {
        return selvagem;
    }

    public void setSelvagem(boolean selvagem) {
        this.selvagem = selvagem;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    @Override
    public Treinador getTreinador() {
        return treinador;
    }

    public int calcularDano() {
    }
}
