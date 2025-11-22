package ui;

import ui.funcoes.JPanelComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelPrincipal extends JPanelComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "CADASTROS", "MOSTRAR RELATÓRIOS", "REMOVER / ALTERAR", "CONSULTAR MAIOR", "SALVAR / CARREGAR"
    ));

    public PainelPrincipal(MenuPrincipal menuPrincipal) {
        super();
        botoes = new ArrayList<>();

        this.menuPrincipal = menuPrincipal;

        this.add(criarPainelEsquerda(), BorderLayout.WEST);
        this.add(criarPainelDireita(), BorderLayout.CENTER);
    }

    private JPanel criarPainelEsquerda(){
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(true);
        painel.setBackground(getCorPrincipal());
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
        painelBorda.add(criarPainelBotoes(botoes, labelsBotoes, this, 5, 1, 10, 10), BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            menuPrincipal.mudaPainel(2);
        } else if (e.getSource() == botoes.get(1)) {
            menuPrincipal.mudaPainel(3);
        } else if (e.getSource() == botoes.get(2)) {
            menuPrincipal.mudaPainel(4);
        } else if (e.getSource() == botoes.get(3)) {
            menuPrincipal.mudaPainel(5);
        } else if (e.getSource() == botoes.get(4)) {
            menuPrincipal.mudaPainel(6);
        }
    }
}
