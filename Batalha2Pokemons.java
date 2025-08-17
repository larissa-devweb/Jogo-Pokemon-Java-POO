package jogopokemon;

import java.util.Scanner;

public class Batalha2Pokemons {

    public static void iniciarBatalha(Pokemon meuPokemon, Pokemon inimigo, Scanner sc) {
        System.out.println("\n=== BATALHA INICIADA ===");
        System.out.println(meuPokemon.getNome() + " VS " + inimigo.getNome() + "\n");

        int turno = 1;

        while (meuPokemon.getHp() > 0 && inimigo.getHp() > 0) {
            System.out.println("=== Turno " + turno + " ===");
            System.out.println(meuPokemon.getNome() + " HP: " + meuPokemon.getHp());
            System.out.println(inimigo.getNome() + " HP: " + inimigo.getHp() + "\n");

            // Turno do jogador
            System.out.println("Seu turno! Pressione ENTER para atacar...");
            aguardarEnter(sc);
            int dano = atacarComTipo(meuPokemon, inimigo, turno);
            System.out.println(meuPokemon.getNome() + " causou " + dano + " de dano em " + inimigo.getNome() + "!\n");

            if (inimigo.getHp() <= 0) {
                System.out.println(inimigo.getNome() + " foi derrotado!");
                break;
            }

            // Turno do inimigo
            System.out.println("Turno do inimigo! Pressione ENTER para continuar...");
            aguardarEnter(sc);
            int danoInimigo = atacarComTipo(inimigo, meuPokemon, turno);
            System.out.println(inimigo.getNome() + " causou " + danoInimigo + " de dano em " + meuPokemon.getNome() + "!\n");

            if (meuPokemon.getHp() <= 0) {
                System.out.println(meuPokemon.getNome() + " foi derrotado!");
                break;
            }

            turno++;
        }

        System.out.println("\n=== BATALHA ENCERRADA ===");
    }

    // -------------------- MÉTODOS AUXILIARES --------------------

    private static void aguardarEnter(Scanner sc) {
        System.out.println("Pressione ENTER para continuar...");
        sc.nextLine();
    }

    public static int atacarComTipo(Pokemon atacante, Pokemon alvo, int turno) {
        int dano = atacante.getForca() + (int)(Math.random() * (atacante.getNivel() + 1));

        switch (atacante.getTipo().toLowerCase()) {
            case "agua":
                if (alvo.getTipo().equalsIgnoreCase("terra")) dano *= 1.5;
                if (alvo.getTipo().equalsIgnoreCase("eletrico")) dano *= 0.5;
                break;
            case "terra":
                if (alvo.getTipo().equalsIgnoreCase("eletrico")) dano *= 1.5;
                if (alvo.getTipo().equalsIgnoreCase("agua")) dano *= 0.5;
                // dano dobrado em turno ímpar
                if (turno % 2 == 1) dano *= 2;
                break;
            case "eletrico":
                if (alvo.getTipo().equalsIgnoreCase("agua")) dano *= 1.5;
                if (alvo.getTipo().equalsIgnoreCase("terra")) dano *= 0.5;
                // 30% chance de paralisar
                if (Math.random() < 0.3) {
                    alvo.paralisar();
                    System.out.println(alvo.getNome() + " foi paralisado!");
                }
                break;
            case "floresta":
                if (alvo.getTipo().equalsIgnoreCase("agua")) dano *= 1.5;
                // Regeneração parcial: 20% do dano causado
                int regen = (int)(dano * 0.2);
                atacante.curar(regen);
                System.out.println(atacante.getNome() + " recuperou " + regen + " de HP!");
                break;
        }

        alvo.receberDano(dano);
        return dano;
    }
}
