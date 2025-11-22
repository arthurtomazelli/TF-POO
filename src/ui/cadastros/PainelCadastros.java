package ui.cadastros;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import entidades.GerenciaVendas;
import ui.MenuPrincipal;
import ui.funcoes.JPanelComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelCadastros extends JPanelComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaVendas gerenciaVendas;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;
    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "CADASTRAR FORNECEDOR", "CADASTRAR TECNOLOGIA", "CADASTRAR COMPRADOR", "CADASTRAR VENDA"
    ));

    public PainelCadastros(MenuPrincipal menuPrincipal, GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias, GerenciaCompradores gerenciaCompradores, GerenciaVendas gerenciaVendas) {
        super();
        this.menuPrincipal = menuPrincipal;
        this.gerenciaFornecedores = gerenciaFornecedores;
        this.gerenciaTecnologias = gerenciaTecnologias;
        this.gerenciaCompradores = gerenciaCompradores;
        this.gerenciaVendas = gerenciaVendas;
        botoes = new ArrayList<>();


        painelPrincipal = new JPanel(new BorderLayout());

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelBotoes(botoes, labelsBotoes, this, 1, 3, 10, 10), BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            new CadastrarFornecedor(gerenciaFornecedores);
        } else if (e.getSource() == botoes.get(1)) {
            new CadastrarTecnologia(gerenciaFornecedores, gerenciaTecnologias);
        } else if (e.getSource() == botoes.get(2)) {
            new CadastrarComprador(gerenciaCompradores);
        } else if (e.getSource() == botoes.get(3)) {
            new CadastrarVenda(gerenciaVendas, gerenciaCompradores, gerenciaTecnologias);
        }
    }
}
