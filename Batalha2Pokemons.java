package jogopokemon;

import jogopokemon.pokemons.Pokemon;
import java.util.Scanner;

/**
 * Classe responsável por realizar batalhas entre dois Pokémons.
 * Segue o que o professor pediu: o vencedor ganha experiência
 * e o perdedor é nocauteado (HP = 0).
 */
public class Batalha2Pokemons {

    /**
     * Inicia uma batalha entre dois Pokémons.
     * @param p1 Pokémon 1
     * @param p2 Pokémon 2
     */
    public static void iniciarBatalha(Pokemon p1, Pokemon p2) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== BATALHA INICIADA ===");
        System.out.println(p1.getNome() + " (Treinador: " + nomeTreinador(p1) + ") VS " +
                p2.getNome() + " (Treinador: " + nomeTreinador(p2) + ")");

        // Loop até um Pokémon ser nocauteado
        while (p1.getHp() > 0 && p2.getHp() > 0) {
            System.out.println("\n--- Turno de " + p1.getNome() + " ---");
            int dano1 = p1.calcularDano();
            p2.receberDano(dano1);
            System.out.println(p1.getNome() + " causou " + dano1 + " de dano. " +
                    p2.getNome() + " agora tem " + p2.getHp() + " de HP.");
            if (p2.getHp() <= 0) break;

            System.out.println("\n--- Turno de " + p2.getNome() + " ---");
            int dano2 = p2.calcularDano();
            p1.receberDano(dano2);
            System.out.println(p2.getNome() + " causou " + dano2 + " de dano. " +
                    p1.getNome() + " agora tem " + p1.getHp() + " de HP.");
        }

        // Definir vencedor
        if (p1.getHp() > 0) {
            System.out.println("\n" + p1.getNome() + " venceu a batalha!");
            p1.ganharExperiencia(10); // Ganha XP
            System.out.println(p1.getNome() + " ganhou 10 pontos de experiência. XP total: " + p1.getExperiencia());
        } else if (p2.getHp() > 0) {
            System.out.println("\n" + p2.getNome() + " venceu a batalha!");
            p2.ganharExperiencia(10); // Ganha XP
            System.out.println(p2.getNome() + " ganhou 10 pontos de experiência. XP total: " + p2.getExperiencia());
        } else {
            System.out.println("\nEmpate! Ambos foram nocauteados.");
        }
    }

    // Apenas para não quebrar se o Pokémon não tiver treinador definido
    private static String nomeTreinador(Pokemon p) {
        return (p.getTreinador() != null) ? p.getTreinador().getNome() : "Selvagem";
    }

    public static void iniciarBatalha(Treinador treinadorJogador, Treinador treinadorOponente, Pokemon pokemon) {
    }
}
