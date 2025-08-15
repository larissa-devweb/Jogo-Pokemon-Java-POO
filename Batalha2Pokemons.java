package jogopokemon;

import java.util.Random;
import jogopokemon.pokemons.*;

/**
 * Batalha 1x1 com:
 * ✔ Turnos alternados até um cair (HP 0)
 * ✔ Terra com ataque dobrado em turno ímpar
 * ✔ Elétrico com 30% de chance de paralisar o oponente por 1 turno
 * ✔ Floresta regenera 20% do dano que causar
 * ✔ Vencedor ganha 10 XP e soma 10 à pontuação do treinador (PDF)
 */
public class Batalha2Pokemons {

    public static void iniciarBatalha(Pokemon p1, Pokemon p2) {
        System.out.println(p1.getNome() + " (Treinador: " + nomeTreinador(p1) + ") VS "
                + p2.getNome() + " (Treinador: " + nomeTreinador(p2) + ")");

        int turno = 1; // ✔ controlamos turno aqui (evita acoplamento no tipo)

        while (p1.getHp() > 0 && p2.getHp() > 0) {

            // ===== TURNO P1 =====
            if (p1.estaParalisado()) {
                System.out.println(p1.getNome() + " está paralisado e perde o turno.");
                p1.tickParalisia();
            } else {
                int dano = p1.calcularDano();

                // ✔ Terra: dano dobrado em turno ímpar
                if (p1 instanceof Terra && (turno % 2 != 0)) {
                    dano *= 2;
                }

                p2.receberDano(dano);
                System.out.println(p1.getNome() + " causou " + dano + ". "
                        + p2.getNome() + " ficou com " + p2.getHp() + " HP.");

                // ✔ Floresta: regenera 20% do dano causado
                if (p1 instanceof Floresta) {
                    int cura = (int) Math.round(dano * 0.20);
                    // acesso protegido ao hp via método simples:
                    // (não existe setHp público; ajustamos internamente)
                    // hack simples: receberDano negativo = curar
                    int antes = p1.getHp();
                    p1.receberDano(-cura);
                    System.out.println(p1.getNome() + " regenerou " + (p1.getHp() - antes) + " de HP.");
                }

                // ✔ Elétrico: 30% de chance de paralisar o adversário por 1 turno
                if (p1 instanceof Eletrico && new Random().nextDouble() < 0.30) {
                    p2.paralisarPor(1);
                    System.out.println(p2.getNome() + " ficou paralisado por 1 turno!");
                }
            }

            if (p2.getHp() == 0) {
                System.out.println(p2.getNome() + " foi nocauteado!");
                darRecompensaVitoria(p1);
                break;
            }

            turno++;

            // ===== TURNO P2 =====
            if (p2.estaParalisado()) {
                System.out.println(p2.getNome() + " está paralisado e perde o turno.");
                p2.tickParalisia();
            } else {
                int dano = p2.calcularDano();

                if (p2 instanceof Terra && (turno % 2 != 0)) {
                    dano *= 2;
                }

                p1.receberDano(dano);
                System.out.println(p2.getNome() + " causou " + dano + ". "
                        + p1.getNome() + " ficou com " + p1.getHp() + " HP.");

                if (p2 instanceof Floresta) {
                    int cura = (int) Math.round(dano * 0.20);
                    int antes = p2.getHp();
                    p2.receberDano(-cura);
                    System.out.println(p2.getNome() + " regenerou " + (p2.getHp() - antes) + " de HP.");
                }

                if (p2 instanceof Eletrico && new Random().nextDouble() < 0.30) {
                    p1.paralisarPor(1);
                    System.out.println(p1.getNome() + " ficou paralisado por 1 turno!");
                }
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
        vencedor.aumentarExperiencia(10); // ✔ 10 XP ao vencedor
        if (vencedor.getTreinador() != null) {
            vencedor.getTreinador().adicionarPontuacao(10); // ✔ pontuação do time
        }
        System.out.println(vencedor.getNome() + " recebeu 10 XP.");
    }
}
