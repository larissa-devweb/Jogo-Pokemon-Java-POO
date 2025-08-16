package jogopokemon;

public abstract class Pokemon {
    private String nome;
    private String tipo;
    private int forca;
    private int nivel;
    private int hp;
    private int experiencia;
    private boolean selvagem;
    private boolean emAmbienteAdverso;
    private Treinador treinador;
    private boolean paralisado;

    public Pokemon(String nome, String tipo, boolean selvagem) {
        this(nome, tipo, 100, 10);
    }

    public Pokemon(String nome, String tipo, int hp, int forca) {
        this.nome = nome;
        this.tipo = tipo;
        this.hp = hp;
        this.forca = forca;
        this.nivel = 1;
        this.experiencia = 0;
        this.selvagem = selvagem;
        this.emAmbienteAdverso = false;
        this.treinador = null;
        this.paralisado = false;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getForca() { return forca; }
    public int getNivel() { return nivel; }
    public int getHp() { return hp; }
    public int getExperiencia() { return experiencia; }
    public boolean isSelvagem() { return selvagem; }
    public boolean isEmAmbienteAdverso() { return emAmbienteAdverso; }
    public boolean isParalisado() { return paralisado; }
    public Treinador getTreinador() { return treinador; }

    public void setHp(int hp) { this.hp = hp; }
    public void setSelvagem(boolean b) { this.selvagem = b; }
    public void setEmAmbienteAdverso(boolean b) { this.emAmbienteAdverso = b; }
    public void setTreinador(Treinador t) { this.treinador = t; }

    // Atualiza experiência e sobe de nível a cada 20 XP
    public void aumentarExperiencia(int xp) {
        this.experiencia += xp;
        if (this.experiencia >= this.nivel * 20) {
            this.nivel++;
            System.out.println(this.nome + " subiu para o nível " + this.nivel + "!");
        }
    }

    public void receberDano(int dano) {
        hp -= dano;
        if (hp < 0) hp = 0;
    }

    public void paralisar() {
        paralisado = true;
    }

    public void recuperarParalisia() {
        paralisado = false;
    }

    public abstract int atacar(Pokemon alvo);

    // Método de ataque abstrato recebe turno
    public abstract int atacar(Pokemon alvo, int turno);

    public void curar(int regen) {
        if (regen < 0) return; // não permitir cura negativa
        hp += regen;
        if (hp > 100) hp = 100; // assume que 100 é o HP máximo
        System.out.println(nome + " recuperou " + regen + " de HP. HP atual: " + hp);
    }

}
