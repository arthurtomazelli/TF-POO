package ui.relatorios;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import entidades.GerenciaVendas;
import ui.MenuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelRelatorios extends JPanel implements ActionListener {
    private JPanel painelPrincipal;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaVendas gerenciaVendas;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "RELATÓRIO DE FORNECEDORES", "RELATÓRIO DE TECNOLOGIAS", "RELATÓRIO DE COMPRADORES", "RELATÓRIO DE VENDAS"
    ));

    public PainelRelatorios(MenuPrincipal menuPrincipal, GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias, GerenciaCompradores gerenciaCompradores, GerenciaVendas gerenciaVendas) {
        super(new BorderLayout());
        this.menuPrincipal = menuPrincipal;
        this.gerenciaFornecedores = gerenciaFornecedores;
        this.gerenciaTecnologias = gerenciaTecnologias;
        this.gerenciaCompradores = gerenciaCompradores;
        this.gerenciaVendas = gerenciaVendas;

        painelPrincipal = new JPanel(new BorderLayout());

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelBotoes(), BorderLayout.CENTER);

        painelBotaoChao = new JPanel();

        JButton botaoVoltar = criarBotao("VOLTAR");
        botaoVoltar.addActionListener(e -> menuPrincipal.mudaPainel(1));

        painelBotaoChao.add(botaoVoltar);

        painelPrincipal.add(painelBotaoChao, BorderLayout.SOUTH);
        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal, BorderLayout.CENTER);
    }

    private void setBorda(JPanel painelBorda) {
        int largura = menuPrincipal.getWidth();
        int altura = menuPrincipal.getHeight();

        int vertical = (int) (altura * 0.16) + 100;
        int horizontal = (int) (largura * 0.20) - 120;

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal)
        );
    }

    private JPanel criarPainelBotoes() {
        JPanel painelGrid = new JPanel(new GridLayout(1, 3, 10, 10));

        botoes = new ArrayList<>();

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);
            botao.setMargin(new Insets(10, 0, 10, 0));
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
            new RelatorioFornecedores(gerenciaFornecedores.getFornecedores());
        } else if (e.getSource() == botoes.get(1)) {
            new RelatorioTecnologias("RELATÓRIO DE TECNOLOGIAS", gerenciaTecnologias.getTecnologias(), gerenciaFornecedores.getFornecedores());
        } else if (e.getSource() == botoes.get(2)) {
            new RelatorioCompradores(gerenciaCompradores.getCompradores());
        } else if (e.getSource() == botoes.get(3)) {
            new RelatorioVendas(gerenciaVendas.getVendas());
        }
    }
}
