package ui.salvarCarregar;

import io.GerenciaJSON;
import ui.funcoes.JFrameComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalvarDados extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JTextField campoTexto;
    private JLabel labelPrefixo;
    private List<JButton> botoes;
    private GerenciaJSON gerenciaJSON;

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "SALVAR DADOS"
    ));

    public SalvarDados(GerenciaJSON gerenciaJSON) {
        super();
        this.gerenciaJSON = gerenciaJSON;
        this.botoes = new ArrayList<>();

        setBasics();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("SALVAR DADOS", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Salvar dados");
        this.setSize(700, 250);
    }

    private void setBorda(JPanel painelBorda) {
        int largura = getWidth();
        int altura = getHeight();
        int vertical = (int) (altura * 0.10);
        int horizontal = (int) (largura * 0.18);
        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    private JPanel criarPainelCampos() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 40));

        labelPrefixo = new JLabel("Prefixo dos arquivos:");
        campoTexto = new JTextField(20);

        Font fonteCampo = new Font("Arial", Font.PLAIN, 20);
        Font fonteLabel = new Font("Arial", Font.BOLD, 20);

        labelPrefixo.setFont(fonteLabel);
        campoTexto.setFont(fonteCampo);

        painel.add(labelPrefixo);
        painel.add(campoTexto);

        return painel;
    }

    @Override
    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(5, 18, 5, 18));
        botao.setBackground(Color.WHITE);
        botao.setForeground(getCorPrincipal());
        botao.setFocusPainted(false);
        return botao;
    }

    private void limparCampos() {
        campoTexto.setText("");
    }

    private void salvarArquivos() {
        try {
            String prefixo = campoTexto.getText().trim();

            List<String> salvos = gerenciaJSON.salvarTodos(prefixo);

            if (salvos.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "As listas de objetos estão vazias.\nNão foi possível gerar arquivos.",
                        "ERRO",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (salvos.size() < 4) {
                JOptionPane.showMessageDialog(this,
                        "Alguns arquivos foram salvos com sucesso.\n" +
                                "Arquivos gerados: " + mostraSalvos(salvos),
                        "SUCESSO PARCIAL",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Arquivos salvos com sucesso.\n" +
                                "Arquivos gerados: " + mostraSalvos(salvos),
                        "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private String mostraSalvos(List<String> salvos) {
        String lista = "\n";

        for (int i = 0; i < salvos.size(); i++) {
            lista += (i+1) + " - " + salvos.get(i) + "\n";
        }

        return lista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            limparCampos();
        }
        if (e.getSource() == botoes.get(2)) {
            salvarArquivos();
        }
    }
}
