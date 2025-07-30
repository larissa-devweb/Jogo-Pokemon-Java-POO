/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

import java.util.Scanner;

// Classe que executa a batalha entre dois Pokémons
public class Batalha2Pokemon {

    // Método principal da batalha
    public static void iniciarBatalha(Pokemon atacante, Pokemon defensor) {
        System.out.println("BATALHA INICIADA!");
        System.out.println(atacante.getNome() + " vs " + defensor.getNome());

        Scanner scanner = new Scanner(System.in);
        boolean turnoJogador = true; // Atacante começa atacando

        // Enquanto os dois tiverem energia, continua a batalha
        while (atacante.getEnergia() > 0 && defensor.getEnergia() > 0) {
            if (turnoJogador) {
                // Turno do Pokémon do jogador
                System.out.println("Turno de " + atacante.getNome());
                System.out.print("Deseja atacar (1) ou fugir (2)? ");
                int acao = scanner.nextInt();

                if (acao == 2) {
                    // Se quiser fugir, termina a batalha
                    System.out.println(atacante.getNome() + " fugiu da batalha!");
                    return;
                }

                // Ataca o adversário
                int dano = atacante.calcularDano();
                defensor.receberDano(dano);
                System.out.println(defensor.getNome() + " recebeu " + dano + " de dano. Energia atual: " + defensor.getEnergia());
            } else {
                // Turno do outro Pokémon (inimigo, selvagem ou do computador)
                System.out.println("Turno de " + defensor.getNome());
                int dano = defensor.calcularDano();
                atacante.receberDano(dano);
                System.out.println(atacante.getNome() + " recebeu " + dano + " de dano. Energia atual: " + atacante.getEnergia());
            }

            // Troca o turno para o próximo
            turnoJogador = !turnoJogador;
        }

        // Determina o vencedor com base na energia
        Pokemon vencedor = atacante.getEnergia() > 0 ? atacante : defensor;
        vencedor.ganharExperiencia(10);         // Ganha XP
        vencedor.restaurarEnergia();            // Restaura energia após batalha

        System.out.println("Vitória de " + vencedor.getNome() + "! Ganhou 10 de experiência.");
    }

    static void iniciar(Pokemon p1, Pokemon p2) {
        throw new UnsupportedOperationException("Nao suportado."); 
    }
}
