package jogopokemon;

public abstract class Pokemon {
    private String nome;
    private String tipo;
    private int nivel;
    private int experiencia;
    private int hp;
    private boolean selvagem;
    private Treinador treinador;
    private boolean paralisado;
    private boolean emAmbienteAdverso;
    private int forca; // Força do Pokémon, usada no ataque

    public Pokemon(String nome, String tipo, int forca) {
        this.nome = nome;
        this.tipo = tipo;
        this.hp = 100; // HP inicial padrão
        this.nivel = 1;
        this.experiencia = 0;
        this.selvagem = true;
        this.forca = forca;
        this.paralisado = false;
        this.emAmbienteAdverso = false;
    }

    // Getters e Setters básicos
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getHp() { return hp; }
    public boolean isSelvagem() { return selvagem; }
    public Treinador getTreinador() { return treinador; }
    public boolean isEmAmbienteAdverso() { return emAmbienteAdverso; }
    public boolean estaParalisado() { return paralisado; }

    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public void setSelvagem(boolean selvagem) { this.selvagem = selvagem; }
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }
    public void setEmAmbienteAdverso(boolean emAmbienteAdverso) { this.emAmbienteAdverso = emAmbienteAdverso; }

    // Métodos para paralisia
    public void paralisar() { this.paralisado = true; }
    public void tickParalisia() { this.paralisado = false; }
    public void setParalisado(boolean paralisado) { this.paralisado = paralisado; }

    // Força usada no ataque
    public int getForca() { return forca; }
    public void setForca(int forca) { this.forca = forca; }

    // Experiência e nível
    public void aumentarExperiencia(int xp) {
        this.experiencia += xp;
        while (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia -= 100;
        }
    }

    // Receber dano
    public void receberDano(int dano) {
        setHp(getHp() - dano);
    }

    // Ataque padrão (pode ser sobrescrito nas subclasses)
    public int atacar(Pokemon alvo, int turno) {
        int dano = this.forca + this.nivel;
        alvo.receberDano(dano);
        return dano;
    }
}
