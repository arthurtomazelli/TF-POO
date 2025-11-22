package ui.relatorios;

import entidades.Comprador;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioCompradores extends JDialogComFuncoes {
    public RelatorioCompradores(List<Comprador> compradores) {
        super();
        setBasics();

        if (compradores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há compradores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores =-=-=-=-=-=-=\n");

        for (Comprador c : compradores) {
            areaTexto.setText(areaTexto.getText() + c.geraDescricao() + "\n");
        }
        
        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

        this.add(criarPainelTitulo("RELATÓRIO DE COMPRADORES", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Relatório de Compradores");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }
}
