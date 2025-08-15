package jogopokemon;

import jogopokemon.pokemons.Pokemon;

public class Batalha2Pokemons {

    public static void iniciarBatalha(Pokemon p1, Pokemon p2) {
        System.out.println(p1.getNome() + " (Treinador: " + nomeTreinador(p1) + ") VS " +
                p2.getNome() + " (Treinador: " + nomeTreinador(p2) + ")");

        // Loop até um Pokémon ser nocauteado
        while (p1.getHp() > 0 && p2.getHp() > 0) {

            // Turno P1
            int dano1 = p1.calcularDano();
            p2.receberDano(dano1);
            System.out.println(p1.getNome() + " causou " + dano1 + " de dano. " +
                    p2.getNome() + " agora tem " + p2.getHp() + " de HP.");
            if (p2.getHp() == 0) {
                System.out.println(p2.getNome() + " foi nocauteado!");
                p1.aumentarExperiencia(10); // ✅ Adicionado: XP ao vencedor
                p1.getTreinador().adicionarPontuacao(10);
                break;
            }

            // Turno P2
            int dano2 = p2.calcularDano();
            p1.receberDano(dano2);
            System.out.println(p2.getNome() + " causou " + dano2 + " de dano. " +
                    p1.getNome() + " agora tem " + p1.getHp() + " de HP.");
            if (p1.getHp() == 0) {
                System.out.println(p1.getNome() + " foi nocauteado!");
                p2.aumentarExperiencia(10); // ✅ Adicionado: XP ao vencedor
                p2.getTreinador().adicionarPontuacao(10);
                break;
            }
        }
    }

    private static String nomeTreinador(Pokemon p) {
        return p.getTreinador() != null ? p.getTreinador().getNome() : "Selvagem";
    }
}
