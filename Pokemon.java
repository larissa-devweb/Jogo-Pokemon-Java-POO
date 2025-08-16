package jogopokemon;


public abstract class Pokemon {
    private final String nome;
    private final String tipo;
    private int energia;
    private int forca;
    private int experiencia;
    private int nivel;

    private boolean selvagem; // 🔹 se o Pokémon é selvagem ou do treinador

    // construtor
    public Pokemon(String nome, String tipo, int energia, int forca) {
        this.nome = nome;
        this.tipo = tipo;
        this.energia = energia;
        this.forca = forca;
        this.experiencia = 0;
        this.nivel = 1;
        this.selvagem = true; // padrão: nasce selvagem
    }

    // 🔹 Getter e Setter de "selvagem"
    public boolean isSelvagem() {
        return selvagem;
    }

    public void setSelvagem(boolean selvagem) {
        this.selvagem = selvagem;
    }

    // --- resto dos getters/setters normais ---
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getEnergia() { return energia; }
    public int getForca() { return forca; }
    public int getExperiencia() { return experiencia; }
    public int getNivel() { return nivel; }

    public void setTreinador(Treinador treinador) {

    }
}
