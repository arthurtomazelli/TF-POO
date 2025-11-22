package ui.salvarCarregar;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import entidades.GerenciaVendas;
import io.GerenciaJSON;
import ui.MenuPrincipal;
import ui.funcoes.JPanelComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelSalvarCarregar extends JPanelComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaVendas gerenciaVendas;
    private JPanel painelBorda;
    private JPanel painelBotaoChao;
    private MenuPrincipal menuPrincipal;
    private List<JButton> botoes;

    private final GerenciaJSON gerenciaJSON;

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "CARREGAR DADOS", "SALVAR DADOS"
    ));

    public PainelSalvarCarregar(MenuPrincipal menuPrincipal, GerenciaFornecedores gerenciaFornecedores, GerenciaCompradores gerenciaCompradores, GerenciaTecnologias gerenciaTecnologias, GerenciaVendas gerenciaVendas) {
        super();
        this.menuPrincipal = menuPrincipal;
        this.gerenciaFornecedores = gerenciaFornecedores;
        this.gerenciaCompradores = gerenciaCompradores;
        this.gerenciaTecnologias = gerenciaTecnologias;
        this.gerenciaVendas = gerenciaVendas;
        this.gerenciaJSON = new GerenciaJSON(gerenciaCompradores.getCompradores(), gerenciaFornecedores.getFornecedores(), gerenciaTecnologias.getTecnologias(), gerenciaVendas.getVendas());
        botoes = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelBotoes(botoes, labelsBotoes, this, 1, 3, 70, 10), BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)){
            new CarregarDados(gerenciaJSON);
        } else if (e.getSource() == botoes.get(1)){
            new SalvarDados(gerenciaJSON);
        }
    }
}
