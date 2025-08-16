package jogopokemon;

import java.util.ArrayList;

public class Treinador {
    private String nome;
    private ArrayList<Pokemon> mochila;
    private int linha;       // posição no tabuleiro
    private int coluna;      // posição no tabuleiro
    private int pontuacao;   // pontuação do treinador

    // Construtor
    public Treinador(String nome) {
        this.nome = nome;
        this.mochila = new ArrayList<>();
        this.linha = 0;
        this.coluna = 0;
        this.pontuacao = 0;
    }

    // Getters e Setters básicos
    public String getNome() {
        return nome;
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

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontuacao(int pontos) {
        this.pontuacao += pontos;
    }

    public void zerarPontuacao() {
        this.pontuacao = 0;
    }

    // ===== Mochila =====
    public ArrayList<Pokemon> getMochila() {
        return mochila;
    }

    public void adicionarPokemon(Pokemon p) {
        if (!mochila.contains(p)) {
            mochila.add(p);
            p.setTreinador(this);
            p.setSelvagem(false);
        }
    }

    public void removerPokemon(Pokemon p) {
        mochila.remove(p);
        p.setTreinador(null);
    }

    public void mostrarMochila() {
        if (mochila.isEmpty()) {
            System.out.println("Mochila vazia.");
        } else {
            System.out.println("=== Mochila de " + nome + " ===");
            for (int i = 0; i < mochila.size(); i++) {
                Pokemon p = mochila.get(i);
                System.out.println(i + " - " + p.getNome() + " (" + p.getTipo() + ") Nível: " + p.getNivel() + " XP: " + p.getExperiencia());
            }
        }
    }

    // Método de batalha entre dois Pokémons (simplificado)
    public void batalhar(Pokemon p1, Pokemon p2) {
        System.out.println("Batalha: " + p1.getNome() + " x " + p2.getNome());
        while (p1.getHp() > 0 && p2.getHp() > 0) {
            p1.atacar(p2, 1);
            if (p2.getHp() <= 0) break;
            p2.atacar(p1, 1);
        }
        Pokemon vencedor = p1.getHp() > 0 ? p1 : p2;
        System.out.println("Vencedor: " + vencedor.getNome());
        adicionarPontuacao(10);
    }

    public void aumentarPontuacao(int i) {
    }

}
