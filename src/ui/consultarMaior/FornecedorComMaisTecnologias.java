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
    private Fornecedor fornecedorMaisTec;
    private HashMap<Long, Integer> contagemPorFornecedor;

    public FornecedorComMaisTecnologias(List<Tecnologia> tecnologias, GerenciaFornecedores gerenciaFornecedores) {
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

        fornecedorMaisTec = encontrarFornecedorComMaisTec(tecnologias, gerenciaFornecedores);

        if (fornecedorMaisTec != null) {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedor Com Mais Tecnologias =-=-=-=-=-=-=\n");
            areaTexto.setText(areaTexto.getText() + fornecedorMaisTec.geraDescricao() + "\n");
            areaTexto.setText(areaTexto.getText() + "N° de tecnologias: " + contagemPorFornecedor.get(fornecedorMaisTec.getCod()));
        } else {
            areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores (houve empate de maiores valores) =-=-=-=-=-=-=\n");

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

    private Fornecedor encontrarFornecedorComMaisTec(List<Tecnologia> tecnologias, GerenciaFornecedores gerenciaFornecedores) {
        contagemPorFornecedor  = new HashMap<>();
        long codMaiorF = 0;


        for (Tecnologia t : tecnologias) {
            try {
                Long codF = t.getFornecedor().getCod();

                if (contagemPorFornecedor.get(codF) == null) {
                    contagemPorFornecedor.put(codF, 1);
                    continue;
                }

                int contagemF = contagemPorFornecedor.get(codF);

                contagemPorFornecedor.put(codF, contagemF + 1);
            } catch (NullPointerException e) {}
        }

        int maior = -9999;

        for (Long cod : contagemPorFornecedor.keySet()) {
            int contagem = contagemPorFornecedor.get(cod);

            if (contagem > maior) {
                maior = contagem;
                codMaiorF = cod;
                continue;
            }

            if (contagem == maior) {
                return null;
            }
        }
        return gerenciaFornecedores.buscaFornecedorPorCod(codMaiorF);
    }
}
