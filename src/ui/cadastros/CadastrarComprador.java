

package ui.cadastros;

import entidades.*;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioCompradores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CadastrarComprador extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaCompradores gerenciaCompradores;

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList(
            "Código: ", "Nome: ", "País: ", "Email: "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR COMPRADORES CADASTRADOS", "CONFIRMAR"
    ));


    public CadastrarComprador(GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();

        this.gerenciaCompradores = gerenciaCompradores;
        this.botoes = new ArrayList<>();
        this.camposTexto = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("CADASTRAR COMPRADOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(camposTexto, labelsAtributos), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Comprador");
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

    private void mostrarCompradores() {
        List<Comprador> compradores = gerenciaCompradores.getCompradores();

        new RelatorioCompradores(compradores);
    }

    private void cadastrarComprador() {
        if (camposVazios(camposTexto)) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                long cod = Long.parseLong(camposTexto.get(0).getText());
                String nome = camposTexto.get(1).getText();
                String pais = camposTexto.get(2).getText();
                String email = camposTexto.get(3).getText();


                Comprador comprador = new Comprador(cod, nome, pais, email);

                if(!gerenciaCompradores.emailValido(email)){
                    JOptionPane.showMessageDialog(this, "Email deve terminar em um endereço válido.", "ERRO",  JOptionPane.WARNING_MESSAGE);
                } else if (!gerenciaCompradores.addComprador(comprador)) {
                    JOptionPane.showMessageDialog(this, "Código já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Comprador cadastrado com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos(camposTexto);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Código deve ser um número.", "ERRO", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            limparCampos(camposTexto);
        }
        if (e.getSource() == botoes.get(2)){
            mostrarCompradores();
        }
        if (e.getSource() ==  botoes.get(3)){
            cadastrarComprador();
        }
    }
}

