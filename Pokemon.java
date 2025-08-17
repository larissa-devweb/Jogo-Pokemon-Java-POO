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

    public Pokemon(String nome, String tipo, int hp, int forca) {
        this.nome = nome;
        this.tipo = tipo;
        this.hp = hp;
        this.forca = forca;
        this.nivel = 1;
        this.experiencia = 0;
        this.selvagem = true;
        this.emAmbienteAdverso = false;
        this.paralisado = false;
        this.treinador = null;
    }

    // -------- GETTERS e SETTERS --------
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getForca() { return forca; }
    public int getNivel() { return nivel; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(hp, 0); }
    public void setForca(int forca) { this.forca = forca; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public boolean isSelvagem() { return selvagem; }
    public void setSelvagem(boolean selvagem) { this.selvagem = selvagem; }

    public boolean isEmAmbienteAdverso() { return emAmbienteAdverso; }
    public void setEmAmbienteAdverso(boolean valor) { this.emAmbienteAdverso = valor; }

    public Treinador getTreinador() { return treinador; }
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }

    public boolean isParalisado() { return paralisado; }
    public void paralisar() { this.paralisado = !this.paralisado; } // alterna estado

    // -------- MÉTODOS DE COMBATE --------
    public void receberDano(int dano) {
        setHp(hp - dano);
    }

    public void aumentarExperiencia(int xp) {
        experiencia += xp;
        if (experiencia >= 10) {
            nivel++;
            experiencia = 0;
        }
    }

    /**
     * Ataque padrão com base em força + nível + multiplicadores de tipo
     */
    public int atacar(Pokemon alvo, int turno) {
        int danoBase = getForca() + (int)(Math.random() * (getNivel() + 1));
        double multiplicador = calcularMultiplicador(this.getTipo(), alvo.getTipo());
        int danoFinal = (int) Math.round(danoBase * multiplicador);

        // aplica dano no alvo
        alvo.receberDano(danoFinal);

        // penalidade se em ambiente adverso
        if (isEmAmbienteAdverso()) {
            int penalidade = (int)Math.round(danoFinal * 0.10);
            danoFinal -= penalidade;
        }

        return danoFinal;
    }

    private double calcularMultiplicador(String meuTipo, String tipoAlvo) {
        switch (meuTipo.toLowerCase()) {
            case "agua":
                if (tipoAlvo.equalsIgnoreCase("terra")) return 1.5;
                if (tipoAlvo.equalsIgnoreCase("eletrico")) return 0.5;
                break;
            case "terra":
                if (tipoAlvo.equalsIgnoreCase("eletrico")) return 1.5;
                if (tipoAlvo.equalsIgnoreCase("agua")) return 0.5;
                break;
            case "eletrico":
                if (tipoAlvo.equalsIgnoreCase("agua")) return 1.5;
                if (tipoAlvo.equalsIgnoreCase("terra")) return 0.5;
                break;
            case "floresta":
                if (tipoAlvo.equalsIgnoreCase("agua")) return 1.5;
                break;
        }
        return 1.0;
    }

    // -------- MÉTODOS ABSTRATOS --------
    // cada tipo de Pokémon implementa sua versão de ataque e cura
    public abstract int atacar(Pokemon alvo);
    public abstract void curar(int regen);
}
