package ui.removerAlterar;

import entidades.GerenciaVendas;
import entidades.Venda;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class RemoverVenda extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JTextField campoTexto;
    private JLabel atributo;
    private JTextArea areaTextoVenda;
    private List<JButton> botoes;
    private JButton botaoBuscar;
    private GerenciaVendas gerenciaVendas;
    private Venda vendaRemover;

    private final String labelAtributo = "Número: ";

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR VENDAS", "REMOVER VENDA"
    ));

    public RemoverVenda(GerenciaVendas gerenciaVendas) {
        super();
        setBasics();

        this.gerenciaVendas = gerenciaVendas;
        this.botoes = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("REMOVER VENDA", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(labelAtributo), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Remover Venda");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    private void setBorda(JPanel painelBorda) {
        int largura = getWidth();
        int altura = getHeight();

        int vertical = (int) (altura * 0.16);
        int horizontal = (int) (largura * 0.20);

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    public JPanel criarPainelCampos(String texto) {
        JPanel painelPrincipalCampos = new JPanel(new BorderLayout());

        JPanel painelFlow = new JPanel(new FlowLayout());

        campoTexto = new JTextField(15);
        atributo = new JLabel(texto);

        botaoBuscar = criarBotao("Buscar");
        botaoBuscar.addActionListener(this);

        Font fonteCampo = new Font("Arial", Font.PLAIN, 20);
        Font fonteAtributo = new Font("Arial", Font.BOLD, 20);

        campoTexto.setFont(fonteCampo);
        atributo.setFont(fonteAtributo);

        painelFlow.add(atributo);
        painelFlow.add(campoTexto);
        painelFlow.add(botaoBuscar);

        areaTextoVenda = new JTextArea(5, 40);
        areaTextoVenda.setEditable(false);
        areaTextoVenda.setFont(new Font("Arial", Font.PLAIN, 16));
        areaTextoVenda.setText("");

        painelPrincipalCampos.add(painelFlow, BorderLayout.NORTH);
        painelPrincipalCampos.add(areaTextoVenda, BorderLayout.CENTER);

        return painelPrincipalCampos;
    }

    private void mostrarVendas() {
        Queue<Venda> vendas = gerenciaVendas.getVendas();

        new RelatorioVendas(vendas);
    }

    private void removerVenda() {
        if (campoTexto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo 'Número' deve ser preenchido.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (vendaRemover == null) {
                JOptionPane.showMessageDialog(this, "Insira um número de venda válido.", "ERRO", JOptionPane.WARNING_MESSAGE);
            } else {
                gerenciaVendas.removerVenda(vendaRemover);
                JOptionPane.showMessageDialog(this, "Venda '" + vendaRemover.getNum() + "' removida com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                campoTexto.setText("");
                areaTextoVenda.setText("");
                vendaRemover = null;
            }

        }
    }

    public void buscarVenda() {
        try {
            if (campoTexto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O campo 'Número' deve ser preenchido.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                vendaRemover = gerenciaVendas.buscaVendaPorNum(Integer.parseInt(campoTexto.getText()));

                if (vendaRemover == null) {
                    areaTextoVenda.setText("");
                    JOptionPane.showMessageDialog(this, "Não há uma venda com este número.", "ERRO", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                areaTextoVenda.setText("");
                areaTextoVenda.append("\n");
                areaTextoVenda.append("=-=-=-=-=-=-= Venda encontrada =-=-=-=-=-=-=\n");
                areaTextoVenda.append(vendaRemover + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número deve ser um valor numérico.", "ERRO", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(3, 20, 3, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(getCorPrincipal());
        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            campoTexto.setText("");
            areaTextoVenda.setText("");
            vendaRemover = null;
        }
        if (e.getSource() == botoes.get(2)) {
            mostrarVendas();
        }
        if (e.getSource() == botoes.get(3)) {
            removerVenda();
        }
        if (e.getSource() == botaoBuscar) {
            buscarVenda();
        }
    }
}
