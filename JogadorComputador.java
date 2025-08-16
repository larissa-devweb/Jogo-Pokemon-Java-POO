package jogopokemon;

import java.util.Random;

public class JogadorComputador extends Thread {
    private final Tabuleiro tabuleiro;
    private final Treinador treinador;
    private final Random rnd = new Random();

    public JogadorComputador(Tabuleiro tabuleiro, Treinador treinador) {
        this.tabuleiro = tabuleiro;
        this.treinador = treinador;
    }

    @Override
    public void run() {
        while (tabuleiro.temPokemonsSelvagens()) {
            try {
                Thread.sleep(4000); // espera 4 segundos entre jogadas
            } catch (InterruptedException ignored) {}

            // pega um Pokémon selvagem aleatório
            Pokemon alvo = tabuleiro.pegarPokemonSelvagemAleatorio();
            if (alvo != null) {
                // faz o ataque do primeiro Pokémon do computador
                Pokemon atacante = treinador.getPokemons().get(0);
                int dano = atacante.atacar(alvo); // usar o atacar padrão do Pokémon
                alvo.receberDano(dano);

                System.out.println("CPU (" + atacante.getNome() + ") atacou " + alvo.getNome() + " causando " + dano + " de dano.");

                // se nocauteado, captura
                if (alvo.getHp() <= 0) {
                    alvo.setSelvagem(false);
                    treinador.adicionarPontuacao(10);
                    System.out.println("CPU capturou " + alvo.getNome() + " e ganhou 10 pontos!");
                }
            }
        }
        System.out.println("Todos os pokémons selvagens foram capturados ou nocauteados!");
    }
}
