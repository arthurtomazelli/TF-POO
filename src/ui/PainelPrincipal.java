package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelPrincipal extends JPanel implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "CADASTROS", "MOSTRAR RELATÓRIOS", "REMOVER / ALTERAR", "CONSULTAR MAIOR", "SALVAR / CARREGAR"
    ));

    public PainelPrincipal(MenuPrincipal menuPrincipal) {
        super(new BorderLayout());

        this.menuPrincipal = menuPrincipal;

        this.add(criarPainelEsquerda(), BorderLayout.WEST);
        this.add(criarPainelDireita(), BorderLayout.CENTER);
    }

    private JPanel criarPainelEsquerda(){
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(true);
        painel.setBackground(corPrincipal);
        painel.setPreferredSize(new Dimension(450, getHeight()));

        JLabel titulo = new JLabel("ACMETech", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 60));
        titulo.setForeground(Color.WHITE);

        painel.add(titulo);

        JLabel texto = new JLabel("© 2025 Yamaguti Corp. Todos os direitos reservados.", JLabel.CENTER);
        texto.setForeground(Color.WHITE);

        painel.add(texto, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelDireita(){
        painelPrincipal = new JPanel(new BorderLayout());

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelBotoes(), BorderLayout.CENTER);

        painelBotaoChao = new JPanel();

        JButton botaoSair = criarBotao("SAIR");
        botaoSair.addActionListener(e -> System.exit(0));

        painelBotaoChao.add(botaoSair);

        painelPrincipal.add(painelBotaoChao, BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        return painelPrincipal;
    }

    private void setBorda(JPanel painelBorda) {
        int largura = menuPrincipal.getWidth();
        int altura = menuPrincipal.getHeight();

        int vertical = (int) (altura * 0.16);
        int horizontal = (int) (largura * 0.20);

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    private JPanel criarPainelBotoes() {
        JPanel painelGrid = new JPanel(new GridLayout(5, 1, 10, 10));

        botoes = new ArrayList<>();

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);

            botao.setMargin(new Insets(10, 20, 10, 20));
            botao.setBackground(Color.WHITE);
            botao.setForeground(corPrincipal);
            botao.addActionListener(this);

            botoes.add(botao);
            painelGrid.add(botao);
        }

        return painelGrid;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            menuPrincipal.mudaPainel(2);
        } else if (e.getSource() == botoes.get(1)) {
            menuPrincipal.mudaPainel(3);
        } else if (e.getSource() == botoes.get(2)) {
            System.out.println("kkj nao ta pronto");
        } else if (e.getSource() == botoes.get(3)) {
            menuPrincipal.mudaPainel(5);
        }
    }
}
