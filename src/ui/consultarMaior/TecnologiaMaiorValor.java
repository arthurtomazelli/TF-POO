package ui.consultarMaior;

import entidades.Tecnologia;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TecnologiaMaiorValor extends JDialogComFuncoes {
    private Tecnologia tecMaiorValor;

    public TecnologiaMaiorValor(List<Tecnologia> tecnologias) {
        super();
        setBasics();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        tecMaiorValor = encontrarTecnologiaComMaiorValor(tecnologias);

        if (tecMaiorValor != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologia Com Maior Valor =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + tecMaiorValor);
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            if (tecnologias.isEmpty()) {
                areaTexto.setText(areaTexto.getText() + "Nenhuma tecnologia cadastrada.\n");
            } else {
                for (Tecnologia t : tecnologias) {
                    areaTexto.setText(areaTexto.getText() + t + "\n");
                }
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

    private Tecnologia encontrarTecnologiaComMaiorValor(List<Tecnologia> tecnologias) {
        Tecnologia maior = tecnologias.getFirst();

        int cont = 0;

        for(Tecnologia t : tecnologias) {
            if(t.getValorBase() == maior.getValorBase()) {
                cont++;

                if(cont > 1) {
                    return null;
                }
            }
        }

        return maior;
    }
}
