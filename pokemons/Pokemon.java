package jogopokemon.pokemons;

import jogopokemon.IAtaque;
import jogopokemon.Treinador;

// Classe base abstrata para todos os Pokémon.
// Aqui ficam vida (HP), ataque, defesa, nível, experiência, tipo, vínculo com treinador etc.
public abstract class Pokemon implements IAtaque {

    protected String nome;        // Nome visível
    protected int ataque;         // Ataque base
    protected int defesa;         // Defesa base
    protected int nivel;          // Nível (pode subir com XP)
    protected int vida;           // HP atual
    protected int experiencia;    // XP acumulada
    protected boolean selvagem;   // true se não tem treinador
    protected String tipo;        // Água, Terra, Floresta, Elétrico...
    protected Treinador treinador; // Referência ao dono (ou null se selvagem)

    // Construtor base usado pelas subclasses.
    public Pokemon(String nome, int ataque, int defesa, int nivel, int vida, boolean selvagem, String tipo) {
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.vida = vida;
        this.experiencia = 0;
        this.selvagem = selvagem;
        this.tipo = tipo;
        this.treinador = null; // começa sem dono (selvagem) a menos que seja setado
    }

    public Pokemon(String nome, String eletrico) {
    }

    // ========= Getters/Setters essenciais para outras classes =========

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }

    public boolean isSelvagem() { return selvagem; }
    public void setSelvagem(boolean selvagem) { this.selvagem = selvagem; }

    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    // A Batalha usa getHp(); então fornecemos esse nome.
    public int getHp() { return vida; }
    public void setHp(int novoHp) { this.vida = Math.max(0, novoHp); }

    // Vincula o Pokémon a um treinador (usado ao capturar).
    @Override
    public Treinador getTreinador() { return treinador; }
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }

    // ========= Regras de jogo (XP, dano, status) =========

    // Ganha XP; a cada 100 XP sobe 1 nível (simples para o trabalho).
    public void ganharExperiencia(int pontos) {
        this.experiencia += pontos;
        if (this.experiencia >= 100) {
            this.experiencia -= 100;
            this.nivel++;
            System.out.println(nome + " subiu para o nível " + nivel + ".");
        }
    }

    // Aplica dano levando em conta a defesa; vida nunca fica negativa.
    public void receberDano(int dano) {
        int danoEfetivo = Math.max(0, dano - defesa);
        this.vida -= danoEfetivo;
        if (this.vida < 0) this.vida = 0;
    }

    // Útil se quiser checar em loops.
    public boolean estaVivo() { return this.vida > 0; }

    // Cada tipo de Pokémon implementa seu cálculo de dano.
    @Override
    public abstract int calcularDano();
}
