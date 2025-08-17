package jogopokemon;

import java.io.*;
import jogopokemon.pokemons.*;

public class GerenciadorArquivos {

    // ======== Mochila ========
    public static void salvarMochila(Treinador t) {
        File f = new File("mochila.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (Pokemon p : t.getPokemons()) {
                pw.println(p.getTipo() + ";" + p.getNome() + ";" + p.getNivel() + ";" + p.getForca() + ";" + p.getHp());
            }
            System.out.println("Mochila salva em " + f.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erro ao salvar mochila: " + e.getMessage());
        }
    }

    public static void carregarMochila(Treinador t) {
        File f = new File("mochila.txt");
        if (!f.exists()) {
            System.out.println("Nenhum arquivo de mochila encontrado.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(";");
                if (parts.length >= 2) {
                    String tipo = parts[0];
                    String nome = parts[1];
                    Pokemon p = criarPokemonPorTipo(tipo, nome);
                    if (p != null) {
                        p.setSelvagem(false);
                        p.setTreinador(t);
                        t.adicionarPokemon(p);
                    }
                }
            }
            System.out.println("Mochila carregada.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar mochila: " + e.getMessage());
        }
    }

    // ======== Tabuleiro ========
    public static void salvarTabuleiro(Tabuleiro tab) {
        File f = new File("tabuleiro.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < tab.getTamanho(); i++) {
                for (int j = 0; j < tab.getTamanho(); j++) {
                    Pokemon p = tab.getPokemon(i, j);
                    if (p != null) {
                        pw.println(i + ";" + j + ";" + p.getTipo() + ";" + p.getNome() + ";" + (p.isSelvagem() ? "S" : "N"));
                    }
                }
            }
            System.out.println("Tabuleiro salvo em " + f.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erro ao salvar tabuleiro: " + e.getMessage());
        }
    }

    public static void carregarTabuleiro(Tabuleiro tab) {
        File f = new File("tabuleiro.txt");
        if (!f.exists()) {
            System.out.println("Nenhum arquivo de tabuleiro encontrado.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(";");
                if (parts.length >= 5) {
                    int i = Integer.parseInt(parts[0]);
                    int j = Integer.parseInt(parts[1]);
                    String tipo = parts[2];
                    String nome = parts[3];
                    boolean selvagem = "S".equalsIgnoreCase(parts[4]);

                    Pokemon p = criarPokemonPorTipo(tipo, nome);
                    if (p != null) {
                        p.setSelvagem(selvagem);
                        tab.posicionarPokemon(i, j, p, p.isSelvagem()); // passa true/false conforme o Pokémon
                    }
                }
            }
            System.out.println("Tabuleiro carregado.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar tabuleiro: " + e.getMessage());
        }
    }

    // ===== utilitário =====
    private static Pokemon criarPokemonPorTipo(String tipo, String nome) {
        switch (tipo.toLowerCase()) {
            case "agua":
            case "água":
                return new Agua(nome, false);
            case "terra":
                return new Terra(nome, false);
            case "floresta":
                return new Floresta(nome, false);
            case "elétrico":
            case "eletrico":
                return new Eletrico(nome, false);
            default:
                System.out.println("Tipo desconhecido: " + tipo);
                return null;
        }
    }
}
