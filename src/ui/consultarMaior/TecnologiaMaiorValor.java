package ui.consultarMaior;

import entidades.GerenciaTecnologias;
import entidades.Tecnologia;
import ui.personalizados.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TecnologiaMaiorValor extends JDialogComFuncoes {
    private List<Tecnologia> tecnologias;
    private Tecnologia tecMaiorValor;

    public TecnologiaMaiorValor(GerenciaTecnologias gerenciaTecnologias) {
        super();
        setBasics();

        tecnologias = gerenciaTecnologias.getTecnologias();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = new JPanel();

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        tecMaiorValor = gerenciaTecnologias.encontrarTecnologiaComMaiorValor();

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

}
