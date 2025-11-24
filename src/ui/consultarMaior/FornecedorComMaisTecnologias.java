package ui.consultarMaior;

import entidades.Fornecedor;
import entidades.GerenciaFornecedores;
import entidades.Tecnologia;
import ui.funcoes.JDialogComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class FornecedorComMaisTecnologias extends JDialogComFuncoes {
    private List<Tecnologia> tecnologias;
    private GerenciaFornecedores gerenciaFornecedores;
    private HashMap<Long, Integer> contagemPorFornecedor;

    public FornecedorComMaisTecnologias(List<Tecnologia> tecnologias, GerenciaFornecedores gerenciaFornecedores) {
        super();
        setBasics();
        this.tecnologias = tecnologias;
        this.gerenciaFornecedores = gerenciaFornecedores;

        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel painelTexto = criarPainelTexto();
        preencherCampos();

        JPanel painelChao = criarPainelChao(this);

        this.add(criarPainelTitulo("FORNECEDOR COM MAIS TECNOLOGIAS", 30), BorderLayout.NORTH);
        this.add(painelTexto, BorderLayout.CENTER);
        this.add(painelChao, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setBasics() {
        this.setTitle("Fornecedor Com Mais Tecnologias");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    @Override
    public void preencherCampos() {
        contagemPorFornecedor = new HashMap<>();

        List<Fornecedor> fornecedorMaisTec = gerenciaFornecedores.encontrarFornecedorComMaisTec(tecnologias, contagemPorFornecedor);

        JTextArea areaTexto = getAreaTexto();

        if (fornecedorMaisTec.size() == 1) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedor Com Mais Tecnologias =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + fornecedorMaisTec.getFirst().geraDescricao() + "\n");
            areaTexto.setText(areaTexto.getText() + "\nN° de tecnologias: " + contagemPorFornecedor.get(fornecedorMaisTec.getFirst().getCod()));
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores (houve empate de quantidade) =-=-=-=-=-=-=\n");

            for (Fornecedor f : fornecedorMaisTec) {
                areaTexto.setText(areaTexto.getText() + f.geraDescricao() + "\n");
            }

            areaTexto.setText(areaTexto.getText() + "\nN° de tecnologias: " + contagemPorFornecedor.get(fornecedorMaisTec.getFirst().getCod()));
        }
    }
}
