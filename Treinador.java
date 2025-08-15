package jogopokemon;

// Importa a classe Pokemon do subpacote "pokemons", igual ao que a Jogo usa
import jogopokemon.pokemons.Pokemon;
import java.util.ArrayList;

/**
 * Representa o jogador (Treinador Pokémon).
 * PDF (trecho que você mandou): "Os jogadores deste jogo são chamados de Treinadores Pokémon..."
 * - Objetivos: capturar espécies (Pokédex) e treinar/batalhar (experiência).
 */
public class Treinador {

    private String nome;                   // Nome do treinador (identificação)
    private ArrayList<Pokemon> mochila;    // Coleção de Pokémon capturados (PDF: “capturar...”)
    private Pokedex pokedex;               // Registro das espécies capturadas (objetivo i: completar Pokédex)

    // Posição do treinador no tabuleiro — requerido pela Jogo (usa get/setLinha/Coluna ao clicar)
    private int linha;                     // Linha atual do treinador na grade
    private int coluna;                    // Coluna atual do treinador na grade

    /**
     * Construtor: inicializa nome, mochila, pokédex e posição inicial (0,0).
     * - PDF: precisamos de um "Treinador" que capture (mochila) e registre (Pokédex).
     */
    public Treinador(String nome) {
        this.nome = nome;                  // Guarda o nome informado
        this.mochila = new ArrayList<>();  // Começa com mochila vazia (coleções em Java)
        this.pokedex = new Pokedex();      // Pokédex vazia (vai registrando espécies)
        this.linha = 0;                    // Posição inicial (compatível com Jogo)
        this.coluna = 0;                   // Posição inicial (compatível com Jogo)
    }

    // ===============================
    // Métodos exigidos pela Jogo
    // ===============================

    /** Retorna a linha atual do treinador na grade (usado pela Jogo). */
    public int getLinha() {
        return linha;                      // Necessário para a GUI saber onde desenhar o treinador
    }

    /** Define a linha atual do treinador (clicou numa célula -> move). */
    public void setLinha(int novaLinha) {
        this.linha = novaLinha;            // Atualiza posição que a GUI exibirá
    }

    /** Retorna a coluna atual do treinador. */
    public int getColuna() {
        return coluna;                     // Necessário para a GUI saber onde desenhar o treinador
    }

    /** Define a coluna atual do treinador. */
    public void setColuna(int novaColuna) {
        this.coluna = novaColuna;          // Atualiza posição que a GUI exibirá
    }

    // ===============================
    // Mochila e Pokédex (PDF)
    // ===============================

    /**
     * Adiciona um Pokémon capturado à mochila e registra na Pokédex.
     * PDF: “capturar todas as espécies” (registrar espécie) e “treinar seu time” (guardar na mochila).
     */
    public void adicionarPokemonNaMochila(Pokemon pokemon) {
        // Se sua classe Pokemon tiver setSelvagem(false), vale marcar que agora pertence a um treinador.
        try {
            // Tentamos marcar como não-selvagem, caso o método exista no seu Pokemon.
            // Isso evita quebrar se sua implementação não tiver esse setter.
            pokemon.getClass().getMethod("setSelvagem", boolean.class).invoke(pokemon, false);
        } catch (Exception ignore) {
            // Se não existir setSelvagem(boolean), simplesmente ignoramos sem travar o jogo.
        }

        mochila.add(pokemon);              // Guarda o Pokémon na mochila (coleção mutável)
        pokedex.registrar(pokemon);        // Registra a espécie capturada (sem repetição)
        System.out.println(pokemon.getNome() + " foi adicionado à mochila."); // Feedback no console
    }

    /** Exibe a mochila no console com nome/nível/experiência de cada Pokémon (útil no modo texto). */
    public void mostrarMochila() {
        if (mochila.isEmpty()) {                          // Se não capturou nada ainda
            System.out.println("Mochila vazia.");
            return;
        }
        System.out.println("=== Mochila de " + nome + " ===");
        for (Pokemon p : mochila) {                       // Lista todos os Pokémon capturados
            // Tenta exibir nível/XP se existirem os getters; se não, mostra só o nome.
            try {
                int nivel = (int) p.getClass().getMethod("getNivel").invoke(p);
                int xp    = (int) p.getClass().getMethod("getExperiencia").invoke(p);
                System.out.println("- " + p.getNome() + " (Nível: " + nivel + ", XP: " + xp + ")");
            } catch (Exception semNivelOuXp) {
                System.out.println("- " + p.getNome());
            }
        }
    }

    /** Retorna a soma da experiência de todos os Pokémon da mochila (PDF: critério de vencedor por XP total). */
    public int getTotalExperiencia() {
        int total = 0;                                     // Acumula XP
        for (Pokemon p : mochila) {
            try {
                // Se a sua classe Pokemon tiver getExperiencia(), somamos
                total += (int) p.getClass().getMethod("getExperiencia").invoke(p);
            } catch (Exception ignore) {
                // Se não houver XP no seu Pokemon, apenas ignora (não soma).
            }
        }
        return total;                                      // Retorna a pontuação do treinador
    }

    // ===============================
    // Getters auxiliares
    // ===============================

    /** Nome do treinador (usado pela GUI para escrever “Ash” na célula do treinador). */
    public String getNome() {
        return nome;                                       // Mostra o nome no botão da posição do treinador
    }

    /** Acesso direto à lista (útil para menus de seleção de Pokémon para batalha). */
    public ArrayList<Pokemon> getMochila() {
        return mochila;                                    // Retorna a coleção para outras telas/menus
    }

    /** Permite que outras classes (captura, menus) acessem a Pokédex. */
    public Pokedex getPokedex() {
        return pokedex;                                    // Ex.: registrar captura ou mostrar progresso
    }
}