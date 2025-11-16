package ui;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import entidades.GerenciaVendas;
import ui.cadastros.PainelCadastros;
import ui.relatorios.PainelRelatorios;
import ui.consultarMaior.PainelConsultarMaior;
import ui.removerAlterar.PainelRemoverAlterar;
import ui.salvarCarregar.PainelSalvarCarregar;

import javax.swing.*;

public class MenuPrincipal extends JFrame{
    private PainelPrincipal painelPrincipal;
    private PainelCadastros painelCadastros;
    private PainelRelatorios painelRelatorios;
    private PainelRemoverAlterar painelRemoverAlterar;
    private PainelConsultarMaior painelConsultarMaior;
    private PainelSalvarCarregar painelSalvarCarregar;

    public MenuPrincipal(GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias, GerenciaCompradores gerenciaCompradores, GerenciaVendas gerenciaVendas) {
        super();
        setBasics();

        this.painelPrincipal = new PainelPrincipal(this);
        this.painelCadastros = new PainelCadastros(this, gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
        this.painelRelatorios = new PainelRelatorios(this, gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
        this.painelConsultarMaior = new PainelConsultarMaior(this, gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
        this.painelRemoverAlterar = new PainelRemoverAlterar(this, gerenciaVendas, gerenciaCompradores);
        this.painelSalvarCarregar = new PainelSalvarCarregar(this, gerenciaFornecedores, gerenciaCompradores);

        this.setContentPane(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Menu Principal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
    }

    public void mudaPainel(int painel) {
        switch(painel) {
            case 1:
                this.setContentPane(painelPrincipal);
                this.setTitle("Menu Principal");
                this.pack();
                this.setSize(1200,800);
                break;
            case 2:
                this.setContentPane(painelCadastros);
                this.setTitle("Menu Cadastros");
                this.pack();
                this.setSize(1200,800);
                break;
            case 3:
                this.setContentPane(painelRelatorios);
                this.setTitle("Menu Relatórios");
                this.pack();
                this.setSize(1200,800);
                break;
            case 4:
                this.setContentPane(painelRemoverAlterar);
                this.setTitle("Menu Remover/Alterar");
                this.pack();
                this.setSize(1200,800);
                break;
            case 5:
                this.setContentPane(painelConsultarMaior);
                this.setTitle("Menu Consultar Maior");
                this.pack();
                this.setSize(1200,800);
                break;
            case 6:
                this.setContentPane(painelSalvarCarregar);
                this.setTitle("Menu Salvar/Carregar");
                this.pack();
                this.setSize(1200,800);
                break;
        }
    }
}
