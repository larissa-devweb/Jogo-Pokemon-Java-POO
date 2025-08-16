package jogopokemon;

import java.util.Random;

public class Batalha2Pokemons {

    public static void iniciarBatalha(Pokemon p1, Pokemon p2) {
        System.out.println(p1.getNome() + " (Treinador: " + nomeTreinador(p1) + ") VS "
                + p2.getNome() + " (Treinador: " + nomeTreinador(p2) + ")");

        int turno = 1;
        Random rnd = new Random();

        while (p1.getHp() > 0 && p2.getHp() > 0) {

            // TURNO P1
            if (p1.estaParalisado()) {
                System.out.println(p1.getNome() + " está paralisado e perde o turno.");
                p1.tickParalisia();
            } else {
                int dano = p1.atacar(p2, turno);
                System.out.println(p1.getNome() + " causou " + dano + ". "
                        + p2.getNome() + " ficou com " + p2.getHp() + " HP.");
            }

            if (p2.getHp() == 0) {
                System.out.println(p2.getNome() + " foi nocauteado!");
                darRecompensaVitoria(p1);
                break;
            }

            turno++;

            // TURNO P2
            if (p2.estaParalisado()) {
                System.out.println(p2.getNome() + " está paralisado e perde o turno.");
                p2.tickParalisia();
            } else {
                int dano = p2.atacar(p1, turno);
                System.out.println(p2.getNome() + " causou " + dano + ". "
                        + p1.getNome() + " ficou com " + p1.getHp() + " HP.");
            }

            if (p1.getHp() == 0) {
                System.out.println(p1.getNome() + " foi nocauteado!");
                darRecompensaVitoria(p2);
                break;
            }

            turno++;
        }
    }

    private static String nomeTreinador(Pokemon p) {
        return (p.getTreinador() != null) ? p.getTreinador().getNome() : "Selvagem";
    }

    private static void darRecompensaVitoria(Pokemon vencedor) {
        vencedor.aumentarExperiencia(10);
        if (vencedor.getTreinador() != null) {
            vencedor.getTreinador().adicionarPontuacao(10);
        }
        System.out.println(vencedor.getNome() + " recebeu 10 XP.");
    }
}
