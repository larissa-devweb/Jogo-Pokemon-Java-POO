package jogopokemon;

public class JogadorComputador extends Thread {
    private final Tabuleiro tabuleiro;
    private final Treinador treinador;
    private final IAtaque ataque;

    public JogadorComputador(Tabuleiro t, Treinador tr, IAtaque a) {
        this.tabuleiro = t;
        this.treinador = tr;
        this.ataque = a;
    }



    @Override
    public void run() {
        while (tabuleiro.temPokemonsSelvagens()) {
            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
            Pokemon alvo = tabuleiro.pegarPokemonSelvagemAleatorio();
            if (alvo != null) {
                int dano = ataque.calcularDano(treinador.getPokemons().get(0), alvo);
                alvo.receberDano(dano);
                if (alvo.getHp() <= 0) {
                    alvo.setSelvagem(false);
                    treinador.adicionarPontuacao(10);
                }
            }
        }
    }
}
