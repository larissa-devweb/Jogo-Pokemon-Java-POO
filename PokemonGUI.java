package jogopokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/*
  PokemonGUI.java
  Interface gráfica (Swing) que integra o Tabuleiro, Treinador e lógica já implementada.

  Requisitos cobertos:
  - grid NxN com botões (cada célula = JButton)
  - controle de movimento do treinador (4 botões)
  - ao pisar em célula com Pokémon selvagem -> tentativa de captura
  - thread que movimenta Pokémons selvagens a cada X segundos
  - salvar/carregar mochila e salvar tabuleiro via GerenciadorArquivos
  - atualiza visual do grid sempre que algo muda
  - uso mínimo de imagens (usa texto); se quiser imagens depois eu adapto
*/

public class PokemonGUI extends JFrame {

    // Componentes de GUI
    private final JPanel gridPanel;       // painel que contém a grade de botões
    private final JButton[][] cellButtons; // botões correspondentes às células do tabuleiro
    private final JLabel statusLabel;     // mostra mensagens ao jogador (feedback)
    private final JButton upBtn, downBtn, leftBtn, rightBtn;
    private final JButton salvarMochilaBtn, carregarMochilaBtn, salvarTabuleiroBtn;

    // Lógica do jogo (reaproveita as classes já criadas)
    private final Tabuleiro tabuleiro;
    private final Treinador treinadorJogador;

    // posição atual do treinador na grade
    private int treinadorLinha;
    private int treinadorColuna;

    // controle da thread de movimento de pokémons selvagens
    private volatile boolean moverSelvagensAtivo = true;

    // construtor: inicializa GUI e lógica
    public PokemonGUI(Tabuleiro tabuleiro, Treinador treinador) {
        // chamada ao construtor da superclasse JFrame
        super("Pokémon POO - Interface Gráfica");

        // salva referencias das estruturas de lógica
        this.tabuleiro = tabuleiro;
        this.treinadorJogador = treinador;

        // inicializa posição do treinador (por padrão canto superior esquerdo [0,0] se livre)
        this.treinadorLinha = 0;
        this.treinadorColuna = 0;
        if (!tabuleiro.estaVazio(treinadorLinha, treinadorColuna)) {
            // se não estiver vazia, procura a primeira célula vazia
            outer:
            for (int r = 0; r < tabuleiro.tamanho; r++) {
                for (int c = 0; c < tabuleiro.tamanho; c++) {
                    if (tabuleiro.estaVazio(r, c)) {
                        treinadorLinha = r;
                        treinadorColuna = c;
                        break outer;
                    }
                }
            }
        }

        // layout básico da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 800);

        // painel da grade central
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(tabuleiro.tamanho, tabuleiro.tamanho));
        cellButtons = new JButton[tabuleiro.tamanho][tabuleiro.tamanho];

        // cria botões para cada célula e adiciona ao gridPanel
        for (int i = 0; i < tabuleiro.tamanho; i++) {
            for (int j = 0; j < tabuleiro.tamanho; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setMargin(new Insets(1,1,1,1));
                final int linha = i;
                final int coluna = j;

                // ao clicar em uma célula, movemos o treinador para essa célula (opcional)
                // ou podemos tratar clique para "buscar" — aqui optamos por mover treinador ao clicar
                btn.addActionListener(e -> {
                    // tenta mover treinador até a célula clicada (caminhada simples em linha/coluna)
                    moveTreinadorPara(linha, coluna);
                });

                cellButtons[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        // painel inferior com controle de movimento e ações
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        // painel de setas de movimento
        JPanel arrows = new JPanel(new GridLayout(2, 3));
        arrows.add(new JLabel()); // célula vazia
        upBtn = new JButton("Cima");
        arrows.add(upBtn);
        arrows.add(new JLabel());
        leftBtn = new JButton("Esquerda");
        arrows.add(leftBtn);
        downBtn = new JButton("Baixo");
        arrows.add(downBtn);
        rightBtn = new JButton("Direita");
        arrows.add(rightBtn);

        // listeners dos botões de movimento - cada um chama método que tenta mover e trata eventos
        upBtn.addActionListener(e -> tentarMoverTreinador(treinadorLinha - 1, treinadorColuna));
        downBtn.addActionListener(e -> tentarMoverTreinador(treinadorLinha + 1, treinadorColuna));
        leftBtn.addActionListener(e -> tentarMoverTreinador(treinadorLinha, treinadorColuna - 1));
        rightBtn.addActionListener(e -> tentarMoverTreinador(treinadorLinha, treinadorColuna + 1));

        // painel de ações à direita do controle
        JPanel actions = new JPanel(new GridLayout(4,1,5,5));
        salvarMochilaBtn = new JButton("Salvar Mochila");
        carregarMochilaBtn = new JButton("Carregar Mochila");
        salvarTabuleiroBtn = new JButton("Salvar Tabuleiro");
        JButton mostrarMochilaBtn = new JButton("Ver Mochila");

        actions.add(salvarMochilaBtn);
        actions.add(carregarMochilaBtn);
        actions.add(salvarTabuleiroBtn);
        actions.add(mostrarMochilaBtn);

        // status label para mensagens ao jogador
        statusLabel = new JLabel("Bem-vindo! Movimente o treinador ou clique numa célula.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        // conectando ações dos botões de salvar/carregar
        salvarMochilaBtn.addActionListener(e -> {
            GerenciadorArquivos.salvarMochila(treinadorJogador);
            statusLabel.setText("Mochila salva em arquivo.");
        });

        carregarMochilaBtn.addActionListener(e -> {
            GerenciadorArquivos.carregarMochila(treinadorJogador);
            statusLabel.setText("Mochila carregada do arquivo.");
            atualizarGrid(); // atualiza caso alguma informação de exibição dependa da mochila
        });

        salvarTabuleiroBtn.addActionListener(e -> {
            GerenciadorArquivos.salvarTabuleiro(tabuleiro);
            statusLabel.setText("Tabuleiro salvo em arquivo.");
        });

        mostrarMochilaBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Mochila de ").append(treinadorJogador.getNome()).append(":\n");
            if (treinadorJogador.getQuantidadePokemons() == 0) {
                sb.append("[nenhum Pokémon]\n");
            } else {
                for (Pokemon p : treinadorJogador.getPokemons()) {
                    sb.append("- ").append(p.getNome()).append(" (").append(p.getTipo()).append(")\n");
                }
            }
            // mostra mochila em dialog modal
            JOptionPane.showMessageDialog(this, sb.toString(), "Mochila", JOptionPane.PLAIN_MESSAGE);
        });

        // organiza painel inferior: setas à esquerda, ações à direita
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(arrows, BorderLayout.CENTER);
        bottom.add(actions, BorderLayout.EAST);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        controlPanel.add(bottom, BorderLayout.CENTER);

        // adiciona componentes à janela principal
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // popula a grade inicialmente (coloca texto/iniciais)
        atualizarGrid();

        // inicia a thread que move pokémons selvagens automaticamente
        iniciarThreadMovimentoSelvagens();

        // exibe a janela centralizada
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // método que tenta mover treinador para posição (novaLinha, novaColuna)
    // verifica limites e executa captura se houver pokémon
    private void tentarMoverTreinador(int novaLinha, int novaColuna) {
        // verifica se a nova posição é válida dentro do tabuleiro
        if (!tabuleiro.posicaoValida(novaLinha, novaColuna)) {
            statusLabel.setText("Posição inválida.");
            return;
        }

        // atualiza posição do treinador
        this.treinadorLinha = novaLinha;
        this.treinadorColuna = novaColuna;

        // verifica se existe um Pokémon na célula
        Pokemon p = tabuleiro.getPokemon(novaLinha, novaColuna);
        if (p != null) {
            if (p.isSelvagem()) {
                // tenta capturar usando a lógica existente
                statusLabel.setText("Encontrado " + p.getNome() + " — tentando captura...");
                boolean capturado = CapturaSelvagem.tentarCaptura(p, tabuleiro, novaLinha, novaColuna, treinadorJogador);

                // se capturado, removemos do tabuleiro e atualizamos status
                if (capturado) {
                    // remover do tabuleiro para refletir captura
                    tabuleiro.removerPokemon(novaLinha, novaColuna);
                    statusLabel.setText(p.getNome() + " capturado e adicionado à mochila.");
                } else {
                    // se fugiu, CapturaSelvagem já tentou mover; atualiza mensagem de acordo
                    statusLabel.setText(p.getNome() + " não foi capturado e pode ter se movido.");
                }
            } else {
                // se pokemon já é do treinador ou adversário, aqui tratamos como "encontro"
                statusLabel.setText("Aqui está " + p.getNome() + ". (não é selvagem)");
            }
        } else {
            statusLabel.setText("Posição vazia.");
        }

        // atualiza a exibição do grid sempre que o treinador se move
        atualizarGrid();
    }

    // método que move diretamente o treinador até a célula clicada (caminho simples)
    private void moveTreinadorPara(int linhaDestino, int colunaDestino) {
        // implementação simples: posiciona imediatamente na célula de destino se válida
        if (!tabuleiro.posicaoValida(linhaDestino, colunaDestino)) {
            statusLabel.setText("Destino inválido.");
            return;
        }
        tentarMoverTreinador(linhaDestino, colunaDestino);
    }

    // atualiza todos os botões da grade para refletir o estado atual do tabuleiro + treinador
    private void atualizarGrid() {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < tabuleiro.tamanho; i++) {
                for (int j = 0; j < tabuleiro.tamanho; j++) {
                    JButton btn = cellButtons[i][j];
                    Pokemon p = tabuleiro.getPokemon(i, j);

                    // se o treinador está nesta célula, exibimos "T" + nome opcional
                    if (i == treinadorLinha && j == treinadorColuna) {
                        btn.setText("T");
                        btn.setBackground(Color.CYAN);
                        btn.setToolTipText("Treinador aqui");
                    } else if (p == null) {
                        // célula vazia
                        btn.setText("");
                        btn.setBackground(null);
                        btn.setToolTipText("Vazio");
                    } else {
                        // célula com Pokémon: exibimos a inicial e o tipo como tooltip
                        String inicial = p.getNome().isEmpty() ? "?" : String.valueOf(p.getNome().charAt(0));
                        btn.setText(inicial);
                        btn.setBackground(Color.WHITE);
                        btn.setToolTipText(p.getNome() + " (" + p.getTipo() + (p.isSelvagem() ? ", selvagem" : ", capturado") + ")");
                    }
                }
            }
        });
    }

    // inicia uma thread que, periodicamente, tenta mover pokémons selvagens aleatoriamente
    private void iniciarThreadMovimentoSelvagens() {
        // SwingWorker é uma boa forma de executar tarefas em background no Swing
        SwingWorker<Void, Void> moverWorker = new SwingWorker<>() {
            private final Random rand = new Random();

            @Override
            protected Void doInBackground() throws Exception {
                // enquanto a flag estiver ativa, o loop roda
                while (moverSelvagensAtivo) {
                    // percorre todas as células procurando pokémons selvagens
                    for (int i = 0; i < tabuleiro.tamanho; i++) {
                        for (int j = 0; j < tabuleiro.tamanho; j++) {
                            Pokemon p = tabuleiro.getPokemon(i, j);
                            if (p != null && p.isSelvagem()) {
                                // para cada pokémon selvagem, escolhe uma direção aleatória e tenta mover
                                int[][] direcoes = {{-1,0},{1,0},{0,-1},{0,1}};
                                int[] d = direcoes[rand.nextInt(direcoes.length)];
                                int novaLinha = i + d[0];
                                int novaColuna = j + d[1];

                                // sincroniza no objeto tabuleiro para evitar condições de corrida
                                synchronized (tabuleiro) {
                                    if (tabuleiro.posicaoValida(novaLinha, novaColuna) && tabuleiro.estaVazio(novaLinha, novaColuna)) {
                                        // move o pokémon para a nova posição
                                        tabuleiro.removerPokemon(i, j);
                                        try {
                                            // usa modo debug=true para posicionar sem validação regional (movimento livre)
                                            tabuleiro.posicionarPokemon(novaLinha, novaColuna, p, true);
                                        } catch (RegiaoInvalidaException ex) {
                                            // se, por algum motivo, a região for inválida (não esperado aqui), reverte
                                            tabuleiro.posicionarPokemon(i, j, p, true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // após mover todos, atualiza a interface
                    atualizarGrid();

                    // faz a thread "dormir" por 3 segundos (simula tempo de pensar / movimento)
                    Thread.sleep(3000);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // para propagar exceções se houveram
                } catch (InterruptedException | ExecutionException e) {
                    // log simples em status label
                    statusLabel.setText("Erro na thread de movimento: " + e.getMessage());
                }
            }
        };

        moverWorker.execute(); // dispara a SwingWorker
    }

    // método para encerrar a thread de movimento (quando fechar a UI)
    public void pararMovimentoSelvagens() {
        moverSelvagensAtivo = false;
    }

    // método main para rodar a GUI (ponto de entrada)
    // integra com as classes existentes: cria Tabuleiro e Treinador, então abre a GUI
    public static void main(String[] args) {
        // criar estruturas de lógica (reaproveitando implementação já existente)
        Tabuleiro tabuleiro = new Tabuleiro(6);
        Treinador treinador = new Treinador("Ash");

        // posiciona alguns pokémons iniciais em modo debug para teste
        try {
            tabuleiro.posicionarPokemon(1, 1, new PokemonAgua("Squirtle"), true);
            tabuleiro.posicionarPokemon(2, 3, new PokemonEletrico("Pikachu"), true);
            tabuleiro.posicionarPokemon(4, 2, new PokemonFloresta("Chikorita"), true);
            tabuleiro.posicionarPokemon(5-1, 0, new PokemonTerra("Diglett"), true); // ajuste se necessário
        } catch (RegiaoInvalidaException e) {
            // em modo debug usamos true para ignorar validação, então normalmente não cai aqui
            System.err.println("Erro ao posicionar inicial: " + e.getMessage());
        }

        // cria e mostra a interface na EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            PokemonGUI gui = new PokemonGUI(tabuleiro, treinador);

            // adiciona um WindowListener para parar a thread de movimento ao fechar a janela
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    gui.pararMovimentoSelvagens();
                    super.windowClosing(e);
                }
            });
        });
    }
}

