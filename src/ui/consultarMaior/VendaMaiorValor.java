package ui.consultarMaior;

import entidades.GerenciaVendas;
import entidades.Venda;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;

public class VendaMaiorValor extends JDialogComFuncoes {
    private GerenciaVendas gerenciaVendas;
    private Queue<Venda> vendas;

    public VendaMaiorValor(GerenciaVendas gerenciaVendas) {
        super();
        setBasics();
        this.gerenciaVendas = gerenciaVendas;

        vendas = gerenciaVendas.getVendas();

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vendas cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);


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

    @Override
    public void preencherCampos() {
        List<Venda> vendaMaiorValor = gerenciaVendas.encontrarVendaComMaiorValor();

        JTextArea areaTexto = getAreaTexto();

        if (vendaMaiorValor.size() == 1) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Venda Com Maior Valor =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + vendaMaiorValor.getFirst() + "\n");
            areaTexto.setText(areaTexto.getText() + "\nMaior valor: " + vendaMaiorValor.getFirst().getValorFinal());
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Venda (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            for (Venda v : vendaMaiorValor) {
                areaTexto.setText(areaTexto.getText() + v + "\n");
            }

            areaTexto.setText(areaTexto.getText() + "\nMaior valor: " + vendaMaiorValor.getFirst().getValorFinal());
        }
    }
}
