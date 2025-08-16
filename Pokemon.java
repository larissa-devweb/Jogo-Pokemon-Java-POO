package jogopokemon;

public abstract class Pokemon {
    private String nome;
    private String tipo;
    private int forca;
    private int nivel;
    private boolean selvagem;
    private boolean emAmbienteAdverso;
    private Treinador treinador;

    public Pokemon(String nome, String tipo, boolean selvagem) {
        this.nome = nome;
        this.tipo = tipo;
        this.selvagem = selvagem;
        this.forca = 10;
        this.nivel = 1;
        this.emAmbienteAdverso = false;
        this.treinador = null;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getForca() { return forca; }
    public int getNivel() { return nivel; }
    public boolean isSelvagem() { return selvagem; }
    public boolean isEmAmbienteAdverso() { return emAmbienteAdverso; }
    public void setEmAmbienteAdverso(boolean b) { emAmbienteAdverso = b; }
    public void setTreinador(Treinador t) { treinador = t; }

    public Treinador getTreinador() {
    }
}
