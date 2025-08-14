package jogopokemon;

public class Batalha2Pokemons {
    public static void batalhar(Pokemon p1, Pokemon p2) {
        int dano1 = p1.calcularDano();
        int dano2 = p2.calcularDano();

        System.out.println(p1.getNome() + " causou " + dano1 + " de dano.");
        System.out.println(p2.getNome() + " causou " + dano2 + " de dano.");

        if (dano1 > dano2) {
            System.out.println(p1.getNome() + " venceu!");
            p1.ganharExperiencia(50); // Ganha 50 pontos
        } else if (dano2 > dano1) {
            System.out.println(p2.getNome() + " venceu!");
            p2.ganharExperiencia(50);
        } else {
            System.out.println("Empate! Nenhum ganha experiência.");
        }
    }
}
