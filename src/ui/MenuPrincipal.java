package ui;

import entidades.GerenciaFornecedores;
import entidades.GerenciaTecnologias;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuPrincipal extends JFrame{
    private PainelPrincipal painelPrincipal;
    private PainelCadastros painelCadastros;

    public MenuPrincipal(GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias) {
        super();
        setBasics();

        this.painelPrincipal = new PainelPrincipal(this, gerenciaFornecedores, gerenciaTecnologias);
        this.painelCadastros = new PainelCadastros(this);

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
                break;
            case 2:
                this.setContentPane(painelCadastros);
                break;
        }
    }
}
