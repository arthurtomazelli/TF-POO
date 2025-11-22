package ui.consultarMaior;

import entidades.GerenciaVendas;
import entidades.Venda;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VendaMaiorValor extends JDialogComFuncoes {
    private List<Venda> vendas;
    private Venda vendaMaiorValor;

    public VendaMaiorValor(GerenciaVendas gerenciaVendas) {
        super();
        setBasics();

        vendas = gerenciaVendas.getVendas();

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        vendaMaiorValor = gerenciaVendas.encontrarVendaComMaiorValor();

        if (vendaMaiorValor != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Venda Com Maior Valor =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + vendaMaiorValor);
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Venda (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            for (Venda v : vendas) {
                areaTexto.setText(areaTexto.getText() + v + "\n");
            }
        }

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> this.dispose());
        painelChao.add(botaoOK);

        this.add(criarPainelTitulo("TECNOLOGIA COM MAIOR VALOR", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Tecnologia Com Maior Valor");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

}
