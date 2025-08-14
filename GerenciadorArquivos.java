package jogopokemon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * //AQUI FICA salvar e carregar os dados do jogo (partida, pokémons, treinadores e etc
 *
 */
import java.io.*;
import java.util.Scanner;

public class GerenciadorArquivos {
    private static final String CAMINHO_MOCHILA = "mochila.txt";
    private static final String CAMINHO_TABULEIRO = "tabuleiro.txt";

    // Salva os Pokémon da mochila do treinador no arquivo mochila.txt
    public static void salvarMochila(Treinador treinador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_MOCHILA))) {
            for (Pokemon p : treinador.getPokemons()) {
                writer.write(p.getNome() + ";" + p.getTipo());
                writer.newLine();
            }
            System.out.println("Mochila salva com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar mochila: " + e.getMessage());
        }
    }

    // Carrega os Pokémon do arquivo mochila.txt para a mochila do treinador
    public static void carregarMochila(Treinador treinador) {
        File arquivo = new File(CAMINHO_MOCHILA);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de mochila não encontrado.");
            return;
        }

        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    String nome = partes[0];
                    String tipo = partes[1].toLowerCase();
                    Pokemon p = criarPokemonPorTipo(tipo, nome);
                    if (p != null) {
                        p.setSelvagem(false);
                        treinador.adicionarPokemon(p);
                    }
                }
            }
            System.out.println("Mochila carregada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar mochila: " + e.getMessage());
        }
    }

    // Salva todos os Pokémon do tabuleiro no arquivo tabuleiro.txt
    public static void salvarTabuleiro(Tabuleiro tabuleiro) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_TABULEIRO))) {
            for (int linha = 0; linha < tabuleiro.tamanho; linha++) {
                for (int coluna = 0; coluna < tabuleiro.tamanho; coluna++) {
                    Pokemon p = tabuleiro.getPokemon(linha, coluna);
                    if (p != null) {
                        writer.write(p.getNome() + ";" + p.getTipo() + ";" + linha + ";" + coluna + ";" + p.isSelvagem());
                        writer.newLine();
                    }
                }
            }
            System.out.println("Tabuleiro salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar tabuleiro: " + e.getMessage());
        }
    }

    // Método utilitário para criar Pokémon com base no tipo (usado no carregamento)
    private static Pokemon criarPokemonPorTipo(String tipo, String nome) {
        switch (tipo) {
            case "agua" -> {
                return new PokemonAgua(nome);
            }
            case "terra" -> {
                return new PokemonTerra(nome);
            }
            case "floresta" -> {
                return new PokemonFloresta(nome);
            }
            case "eletrico" -> {
                return new PokemonEletrico(nome);
            }
            default -> {
                System.out.println("Tipo desconhecido: " + tipo);
                return null;
            }
        }
    }
}
