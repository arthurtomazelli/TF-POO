package ui.removerAlterar;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import entidades.GerenciaVendas;
import ui.MenuPrincipal;
import ui.consultarMaior.TecnologiaMaiorValor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelRemoverAlterar extends JPanel implements ActionListener {
    private JPanel painelPrincipal;
    private GerenciaVendas gerenciaVendas;
    private GerenciaCompradores gerenciaCompradores;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "REMOVER DADOS DE UMA VENDA", "ALTERAR DADOS DE UM COMPRADOR"
    ));

    public PainelRemoverAlterar(MenuPrincipal menuPrincipal, GerenciaVendas gerenciaVendas, GerenciaCompradores gerenciaCompradores) {
        super(new BorderLayout());
        this.menuPrincipal = menuPrincipal;
        this.gerenciaVendas = gerenciaVendas;
        this.gerenciaCompradores = gerenciaCompradores;

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
        int horizontal = (int) (largura * 0.20);

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal)
        );
    }

    private JPanel criarPainelBotoes() {
        JPanel painelGrid = new JPanel(new GridLayout(1, 3, 70, 10));

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
        if (e.getSource() == botoes.get(0)){
            new RemoverVenda(gerenciaVendas);
        } else if (e.getSource() == botoes.get(1)){
            new AlterarDadosComprador(gerenciaCompradores);
        }
    }
}
