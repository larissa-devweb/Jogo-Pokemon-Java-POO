package jogopokemon;

import jogopokemon.pokemons.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Treinador {
    private String nome;
    private List<Pokemon> time;
    private List<Pokemon> mochila;
    private int pontuacao;
    private int linha;
    private int coluna;

    public Treinador(String nome) {
        this.nome = nome;
        this.time = new ArrayList<>();
        this.mochila = new ArrayList<>();
        this.pontuacao = 0;
    }

    public String getNome() { return nome; }
    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }
    public void setLinha(int l) { linha = l; }
    public void setColuna(int c) { coluna = c; }

    // NOVO: capturar Pokémon selvagem
    public boolean capturarPokemon(Pokemon p) {
        if (p.isSelvagem()) {
            p.setTreinador(this);
            mochila.add(p);
            time.add(p);
            return true;
        }
        return false;
    }

    // NOVO: batalha entre dois pokémons
    public Pokemon batalhar(Pokemon p1, Pokemon p2) {
        while (!p1.estaDerrotado() && !p2.estaDerrotado()) {
            int dano1 = p1.calcularDano();
            p2.receberDano(dano1);

            if (!p2.estaDerrotado()) {
                int dano2 = p2.calcularDano();
                p1.receberDano(dano2);
            }
        }

        if (p1.estaDerrotado()) {
            p2.ganharExperiencia(50);
            pontuacao += 50;
            return p2;
        } else {
            p1.ganharExperiencia(50);
            pontuacao += 50;
            return p1;
        }
    }

    // NOVO: computador escolhe célula aleatória
    public int[] escolherCelula(int max) {
        Random r = new Random();
        return new int[]{r.nextInt(max), r.nextInt(max)};
    }
}
