package ui.consultarMaior;

import entidades.GerenciaTecnologias;
import entidades.Tecnologia;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TecnologiaMaiorValor extends JDialogComFuncoes {
    private GerenciaTecnologias gerenciaTecnologias;
    private List<Tecnologia> tecnologias;
    private Tecnologia tecMaiorValor;

    public TecnologiaMaiorValor(GerenciaTecnologias gerenciaTecnologias) {
        super();
        setBasics();
        this.gerenciaTecnologias = gerenciaTecnologias;

        tecnologias = gerenciaTecnologias.getTecnologias();

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
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
        tecMaiorValor = gerenciaTecnologias.encontrarTecnologiaComMaiorValor();

        JTextArea areaTexto = getAreaTexto();

        if (tecMaiorValor != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologia Com Maior Valor =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + tecMaiorValor);
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias (houve empate de maiores valores) =-=-=-=-=-=-=\n");

            for (Tecnologia t : tecnologias) {
                areaTexto.setText(areaTexto.getText() + t + "\n");
            }
        }
    }
}
