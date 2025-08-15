package jogopokemon;

import java.util.ArrayList;
import jogopokemon.pokemons.Pokemon;

public class Treinador {
    private String nome;
    private ArrayList<Pokemon> mochila; //  Adicionado: lista de Pokémons capturados
    private ArrayList<Pokemon> time;    //  Adicionado: time ativo de batalha
    private int pontuacao;              //  Adicionado: pontuação total do time
    private int linha;                  // posição no tabuleiro
    private int coluna;

    public Treinador(String nome) {
        this.nome = nome;
        this.mochila = new ArrayList<>();
        this.time = new ArrayList<>();
        this.pontuacao = 0; // começa com zero
    }

    // ✅ Adicionado: método para capturar e guardar Pokémon
    public void adicionarPokemon(Pokemon p) {
        mochila.add(p);
        time.add(p); // simplificação: todo Pokémon capturado entra no time
    }

    // ✅ Adicionado: aumenta pontuação total do treinador
    public void adicionarPontuacao(int xp) {
        pontuacao += xp;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getMochila() {
        return mochila;
    }

    public ArrayList<Pokemon> getTime() {
        return time;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
