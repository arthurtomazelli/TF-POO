package ui;

import entidades.GerenciaCompradores;
import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;
import ui.cadastros.PainelCadastros;
import ui.cadastros.PainelRelatorios;

import javax.swing.*;

public class MenuPrincipal extends JFrame{
    private PainelPrincipal painelPrincipal;
    private PainelCadastros painelCadastros;
    private PainelRelatorios painelRelatorios;

    public MenuPrincipal(GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias, GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();

        this.painelPrincipal = new PainelPrincipal(this);
        this.painelCadastros = new PainelCadastros(this, gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores);
        this.painelRelatorios = new PainelRelatorios(this, gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores);

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
        }
    }
}
