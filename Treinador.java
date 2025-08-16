package jogopokemon;

import java.util.ArrayList;

public class Treinador {
    private String nome;
    private ArrayList<Pokemon> pokemons;
    private int pontuacao;
    private int linha, coluna;

    public Treinador(String nome) {
        this.nome = nome;
        this.pokemons = new ArrayList<>();
        this.pontuacao = 0;
        this.linha = 0;
        this.coluna = 0;
    }

    // Retorna o nome do treinador
    public String getNome() {
        return nome;
    }

    // Retorna a lista de Pokémons do treinador
    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    // Adiciona um Pokémon ao time
    public void adicionarPokemon(Pokemon p) {
        pokemons.add(p);
        p.setTreinador(this); // associa o Pokémon a este treinador
    }

    // Adiciona pontos à pontuação do treinador
    public void adicionarPontuacao(int pts) {
        pontuacao += pts;
    }

    // Retorna a pontuação atual
    public int getPontuacao() {
        return pontuacao;
    }

    // Posição do treinador no tabuleiro
    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setLinha(int l) {
        linha = l;
    }

    public void setColuna(int c) {
        coluna = c;
    }

    // Retorna os Pokémons como um array, representando a “mochila” do treinador
    public Pokemon[] getMochila() {
        return pokemons.toArray(new Pokemon[0]);
    }
}
