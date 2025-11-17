package ui.removerAlterar;

import entidades.Comprador;
import entidades.GerenciaCompradores;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioCompradores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlterarDadosComprador extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private JTextField campoTexto;
    private JLabel atributo;
    private JTextArea areaTextoComprador;
    private List<JButton> botoes;
    private JButton botaoBuscar;
    private GerenciaCompradores gerenciaCompradores;
    private Comprador compradorAlterar;

    private final String labelAtributo = "Código: ";

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR COMPRADORES", "ALTERAR DADOS"
    ));

    public AlterarDadosComprador(GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();

        this.gerenciaCompradores = gerenciaCompradores;
        this.botoes = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("ALTERAR DADOS COMPRADOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(labelAtributo), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Alterar dados de um comprador");
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

        areaTextoComprador = new JTextArea(5, 40);
        areaTextoComprador.setEditable(false);
        areaTextoComprador.setFont(new Font("Arial", Font.PLAIN, 16));
        areaTextoComprador.setText("");

        painelPrincipalCampos.add(painelFlow, BorderLayout.NORTH);
        painelPrincipalCampos.add(areaTextoComprador, BorderLayout.CENTER);

        return painelPrincipalCampos;
    }

    private void mostrarCompradores() {
        List<Comprador> compradores = gerenciaCompradores.getCompradores();

        new RelatorioCompradores(compradores);
    }

    private void alterarDadosComprador() {
        if (campoTexto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo 'Código' deve ser preenchido.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (compradorAlterar == null) {
                JOptionPane.showMessageDialog(this, "Insira um código de comprador válido.", "ERRO", JOptionPane.WARNING_MESSAGE);
            } else {
                new SolicitarDadosComprador(gerenciaCompradores, compradorAlterar, this);
            }
        }
    }


    public void buscarComprador() {
        try {
            if (campoTexto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O campo 'Código' deve ser preenchido.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                compradorAlterar = gerenciaCompradores.buscaCompradorPorCod(Integer.parseInt(campoTexto.getText()));

                if (compradorAlterar == null) {
                    areaTextoComprador.setText("");
                    JOptionPane.showMessageDialog(this, "Não há um comprador com este código.", "ERRO", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                areaTextoComprador.setText("");
                areaTextoComprador.append("\n");
                areaTextoComprador.append("=-=-=-=-=-=-= Comprador encontrado =-=-=-=-=-=-=\n");
                areaTextoComprador.append(compradorAlterar.geraDescricao() + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código deve ser um número.", "ERRO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void limparCamposAposAlteracao() {
        campoTexto.setText("");
        areaTextoComprador.setText("");
        compradorAlterar = null;
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
            areaTextoComprador.setText("");
            compradorAlterar = null;
        }
        if (e.getSource() == botoes.get(2)) {
            mostrarCompradores();
        }
        if (e.getSource() == botoes.get(3)) {
            alterarDadosComprador();
        }
        if (e.getSource() == botaoBuscar) {
            buscarComprador();
        }
    }
}
