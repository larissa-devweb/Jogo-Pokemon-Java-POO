package jogopokemon.pokemons;

import jogopokemon.Treinador;

/**
 * Classe base abstrata dos Pokémons.
 * ✔ Agora 'abstract' e com 'abstract int calcularDano();' (conserta “missing return”).
 * ✔ Adicionados hpMax, getTipo(), XP, nível, força etc (para salvar/carregar e batalhar).
 */
public abstract class Pokemon implements IAtaque {

    // ====== Atributos comuns exigidos pelo PDF ======
    protected String nome;        // nome da espécie
    protected int hp;             // energia atual
    protected int hpMax;          // energia máxima (usado p/ Floresta regenerar)
    protected int forca;          // força base
    protected int nivel;          // nível
    protected int xp;             // experiência
    protected boolean selvagem;   // se está selvagem no tabuleiro
    protected String tipo;        // "Água", "Terra", "Floresta", "Elétrico"
    protected Treinador treinador; // dono (nulo se selvagem)

    // ✔ Construtor padronizado (usado pelas subclasses)
    public Pokemon(String nome, int hp, int forca, int nivel, int xp, boolean selvagem, String tipo) {
        this.nome = nome;
        this.hp = hp;
        this.hpMax = hp;   // ✔ hpMax inicial = hp de criação (conserta uso em Floresta)
        this.forca = forca;
        this.nivel = nivel;
        this.xp = xp;
        this.selvagem = selvagem;
        this.tipo = tipo;
        this.treinador = null;
    }

    // ====== Métodos básicos ======
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }             // ✔ necessário no GerenciadorArquivos
    public int getHp() { return hp; }                    // ✔ usado na batalha
    public int getNivel() { return nivel; }
    public int getXp() { return xp; }
    public int getForca() { return forca; }
    public boolean isSelvagem() { return selvagem; }

    public void setSelvagem(boolean selvagem) { this.selvagem = selvagem; }
    public void setTreinador(Treinador t) { this.treinador = t; }

    @Override
    public Treinador getTreinador() { return treinador; }

    // Recebe dano (Água vai sobrescrever pra reduzir dano)
    public void receberDano(int dano) {
        this.hp -= dano;
        if (this.hp < 0) this.hp = 0;
    }

    // ✔ Experiência e subida de nível (PDF: “vencedor ganha XP e pode subir nível”)
    public void aumentarExperiencia(int ganho) {
        this.xp += ganho;
        // Ex.: sobe de nível a cada 10 * nível pontos
        if (this.xp >= this.nivel * 10) {
            this.nivel++;
            this.forca += 2;               // fica mais forte
            this.hpMax += 5;               // máxima sobe um pouco
            this.hp = Math.min(this.hp + 5, this.hpMax); // pequena recompensa de HP
            System.out.println(nome + " subiu para o nível " + nivel + "!");
        }
    }

    // Status simples de paralisia (para Elétrico via batalha)
    private int turnosParalisado = 0;

    public boolean estaParalisado() { return turnosParalisado > 0; }
    public void paralisarPor(int turnos) { this.turnosParalisado = Math.max(this.turnosParalisado, turnos); }
    public void tickParalisia() { if (turnosParalisado > 0) turnosParalisado--; }

    // ✔ Cada subtipo define sua própria forma de calcular dano (PDF dá liberdade)
    public abstract int calcularDano();
}
