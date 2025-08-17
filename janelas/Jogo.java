package jogopokemon.janelas;

import jogopokemon.*;
import jogopokemon.pokemons.*;
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final Treinador jogador;
    private final Treinador computador;
    private final int tamanho = 6; // tamanho do tabuleiro
    private final Scanner sc = new Scanner(System.in);

    // Construtor padrão (cria novo tabuleiro e jogador)
    public Jogo() {
        this.tabuleiro = new Tabuleiro(tamanho);
        this.jogador = new Treinador("Ash");
        this.computador = new Treinador("Computador");
        inicializarPokemons();
    }

    // Construtor que recebe tabuleiro e jogador existentes
    public Jogo(Tabuleiro tabuleiroExistente, Treinador jogadorExistente) {
        this.tabuleiro = tabuleiroExistente;
        this.jogador = jogadorExistente;
        this.computador = new Treinador("Computador");
        inicializarPokemonsComputadorESelvagens();
    }

    // Inicializa pokémons do jogador, computador e selvagens (padrão)
    private void inicializarPokemons() {
        // Pokémon inicial do jogador
        Pokemon p1 = new Agua("Squirtle", false);
        jogador.adicionarPokemon(p1);
        tabuleiro.posicionarPokemonAleatoriamente(p1);
        p1.setTreinador(jogador);

        inicializarPokemonsComputadorESelvagens();
    }

    // Inicializa pokémons do computador e selvagens
    private void inicializarPokemonsComputadorESelvagens() {
        // Pokémon do computador
        Pokemon pc1 = new Terra("Sandshrew", false);
        computador.adicionarPokemon(pc1);
        tabuleiro.posicionarPokemonAleatoriamente(pc1);
        pc1.setTreinador(computador);

        // Pokémons selvagens
        Random rnd = new Random();
        String[] tipos = {"agua", "floresta", "terra", "eletrico"};
        for (int i = 0; i < 8; i++) {
            String tipo = tipos[rnd.nextInt(tipos.length)];
            Pokemon selvagem = criarPokemon(tipo, tipo + i, true);
            tabuleiro.posicionarPokemonAleatoriamente(selvagem);
        }
    }

    // Cria Pokémon pelo tipo
    private Pokemon criarPokemon(String tipo, String nome, boolean selvagem) {
        switch (tipo.toLowerCase()) {
            case "agua": return new Agua(nome, selvagem);
            case "floresta": return new Floresta(nome, selvagem);
            case "terra": return new Terra(nome, selvagem);
            case "eletrico": return new Eletrico(nome, selvagem);
            default: return new Agua(nome, selvagem);
        }
    }

    // Mostra menu e interage com o usuário
    public void iniciarJogo() {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Procurar Pokémon");
            System.out.println("2. Batalhar / Capturar");
            System.out.println("3. Mostrar tabuleiro");
            System.out.println("4. Mostrar time");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            int opc = sc.nextInt();

            switch (opc) {
                case 1:
                    procurarPokemon();
                    break;
                case 2:
                    batalharOuCapturar();
                    break;
                case 3:
                    tabuleiro.exibir();
                    break;
                case 4:
                    jogador.mostrarTime();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

            // Movimento automático do computador
            moverComputador();
        }
    }

    // Procura Pokémon selvagem aleatório
    private void procurarPokemon() {
        Pokemon p = tabuleiro.getPokemonSelvagemAleatorio();
        if (p != null) {
            int[] pos = tabuleiro.localizar(p);
            System.out.println("Você encontrou um Pokémon selvagem: " + p.getNome()
                    + " na posição (" + pos[0] + "," + pos[1] + ")");
        } else {
            System.out.println("Nenhum Pokémon selvagem disponível!");
        }
    }

    // Interação de captura ou batalha
    private void batalharOuCapturar() {
        System.out.print("Digite linha do Pokémon: ");
        int linha = sc.nextInt();
        System.out.print("Digite coluna do Pokémon: ");
        int coluna = sc.nextInt();

        Pokemon p = tabuleiro.getPokemon(linha, coluna);
        if (p == null) {
            System.out.println("Não há Pokémon nessa posição!");
            return;
        }

        if (p.isSelvagem()) {
            boolean capturado = CapturaSelvagem.tentarCaptura(p, tabuleiro, linha, coluna, jogador);
            if (capturado) System.out.println("Você capturou " + p.getNome() + "!");
            else System.out.println(p.getNome() + " escapou!");
        } else {
            if (jogador.getPokemons().isEmpty()) {
                System.out.println("Você não possui pokémons para batalhar!");
                return;
            }
            Pokemon meu = jogador.getPokemons().getFirst();
            Batalha2Pokemons.iniciarBatalha(meu,p, sc);
        }

        verificarFimJogo();
    }

    // Simula movimento do computador (captura aleatória de selvagens)
    private void moverComputador() {
        Random rnd = new Random();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Pokemon p = tabuleiro.getPokemon(i, j);
                if (p != null && p.isSelvagem() && rnd.nextDouble() < 0.3) {
                    p.setSelvagem(false);
                    computador.adicionarPokemon(p);
                    computador.adicionarPontuacao(10);
                    System.out.println("Computador capturou " + p.getNome() + "!");
                }
            }
        }
    }

    // Checa fim de jogo
    private void verificarFimJogo() {
        if (!tabuleiro.temPokemonsSelvagens()) {
            System.out.println("\n=== Fim de jogo ===");
            String vencedor = (jogador.getPontuacao() >= computador.getPontuacao()) ?
                    jogador.getNome() : computador.getNome();
            System.out.println("Vencedor: " + vencedor);
            System.out.println("Pontuação jogador: " + jogador.getPontuacao());
            System.out.println("Pontuação computador: " + computador.getPontuacao());
            System.exit(0);
        }
    }

    // Main
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciarJogo();
    }
}
