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

public class CarregarDados extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JTextField campoTexto;
    private JLabel labelPrefixo;
    private List<JButton> botoes;
    private GerenciaJSON gerenciaJSON;

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "CARREGAR DADOS"
    ));

    public CarregarDados(GerenciaJSON gerenciaJSON) {
        super();
        this.gerenciaJSON = gerenciaJSON;
        this.botoes = new ArrayList<>();

        setBasics();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("CARREGAR DADOS", 40), BorderLayout.NORTH);

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
        this.setTitle("Carregar dados");
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

    private void carregarArquivos() {
        try {
            String prefixo = campoTexto.getText();

            List<String> carregados = gerenciaJSON.carregarTodos(prefixo);

            if (carregados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nome incorreto ou não há arquivos salvos com esse prefixo.",
                        "ERRO",
                        JOptionPane.WARNING_MESSAGE);
            } else if (carregados.size() < 4) {
                JOptionPane.showMessageDialog(this,
                        "Alguns arquivos foram carregados com sucesso.\n" +
                                "Arquivos encontrados: " + mostraCarregados(carregados),
                        "SUCESSO PARCIAL",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Arquivos carregados com sucesso.\n" +
                                "Arquivos encontrados: " + mostraCarregados(carregados),
                        "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "ERRO",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private String mostraCarregados(List<String> carregados) {
        String lista = "\n";

        for (int i = 0; i < carregados.size(); i++) {
            lista += (i + 1) + " - " + carregados.get(i) + "\n";
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
            carregarArquivos();
        }
    }
}
