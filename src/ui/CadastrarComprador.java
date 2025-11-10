

package ui;

import entidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CadastrarComprador extends JFrame implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaCompradores gerenciaCompradores;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList(
            "Código: ", "Nome: ", "País: ", "Email: "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR COMPRADORES", "CONFIRMAR"
    ));


    public CadastrarComprador(GerenciaCompradores gerenciaCompradores) {
        super();
        setBasics();

        this.gerenciaCompradores = gerenciaCompradores;


        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("CADASTRAR COMPRADOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.setContentPane(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Comprador");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
    }

    private void setBorda(JPanel painelBorda) {
        int largura = getWidth();
        int altura = getHeight();

        int vertical = (int) (altura * 0.16);
        int horizontal = (int) (largura * 0.20);

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    private JPanel criarPainelTitulo(String textoTitulo, int tamanhoFonte) {
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setOpaque(true);
        painelTitulo.setBackground(corPrincipal);

        int vertical = 16;
        int horizontal = 24;
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));

        JLabel titulo = new JLabel(textoTitulo, JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        titulo.setForeground(Color.WHITE);

        painelTitulo.add(titulo, BorderLayout.CENTER);

        return painelTitulo;
    }

    private JPanel criarPainelCampos() {
        JPanel painelGrid = new JPanel(new GridLayout(6, 2, 10, 10));
        camposTexto = new ArrayList<>();

        for (String s : labelsAtributos) {
            JTextField campo = new JTextField(70);
            JLabel atributo = new JLabel(s);
            Font fonteCampo = new Font("Arial", Font.PLAIN, 20);
            Font fonteAtributo = new Font("Arial", Font.BOLD, 20);

            campo.setFont(fonteCampo);
            atributo.setFont(fonteAtributo);

            painelGrid.add(atributo);
            painelGrid.add(campo);
            camposTexto.add(campo);
        }

        return painelGrid;
    }

    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel();
        botoes = new ArrayList<>();

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);

            botao.setMargin(new Insets(10, 20, 10, 20));
            botao.setBackground(Color.WHITE);
            botao.setForeground(corPrincipal);
            botao.addActionListener(this);

            botoes.add(botao);
            painelBotoes.add(botao);
        }

        return painelBotoes;
    }

    private void mostrarCompradores() {
        List<Comprador> compradores = gerenciaCompradores.getCompradores();

        if (compradores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há compradores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialogDados = new JDialog();
        dialogDados.setTitle("Dados cadastrados");
        dialogDados.setSize((int) (getWidth() * 0.8), (int)(getHeight() * 0.75));
        dialogDados.setLocationRelativeTo(null);
        dialogDados.setLayout(new BorderLayout());

        JPanel painelTexto = new JPanel();
        JTextArea areaTexto = new JTextArea(30, 50);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Compradores =-=-=-=-=-=-=\n");

        if (compradores.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhum comprador cadastrado.\n");
        } else {
            for (Comprador c : compradores) {
                areaTexto.setText(areaTexto.getText() + c.geraDescricao() + "\n");
            }
        }

        JPanel painelChao = new JPanel();
        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> dialogDados.dispose());
        painelChao.add(botaoOK);

        dialogDados.add(criarPainelTitulo("DADOS CADASTRADOS", 30), BorderLayout.NORTH);
        dialogDados.add(painelTexto, BorderLayout.CENTER);
        dialogDados.add(painelChao, BorderLayout.SOUTH);

        dialogDados.setVisible(true);
        dialogDados.setLocationRelativeTo(null);
    }


    private void cadastrarComprador() {
        if (camposVazios()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                long cod = Long.parseLong(camposTexto.get(0).getText());
                String nome = camposTexto.get(1).getText();
                String pais = camposTexto.get(2).getText();
                String email = camposTexto.get(3).getText();


                Comprador comprador = new Comprador(cod, nome, pais, email);

                if (!gerenciaCompradores.addComprador(comprador)) {
                    JOptionPane.showMessageDialog(this, "Código já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Comprador cadastrado com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Código deve ser números.", "ERRO", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean listasVazias(List<Comprador> compradores) {
        if (compradores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há compradores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return true;
        }

        return false;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    private void limparCampos() {
        for (JTextField campo : camposTexto) {
            campo.setText("");
        }
    }

    private boolean camposVazios() {
        for (JTextField campo : camposTexto) {
            if (campo.getText().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            limparCampos();
        }
        if (e.getSource() == botoes.get(2)){
            mostrarCompradores();
        }
        if (e.getSource() ==  botoes.get(3)){
            cadastrarComprador();

        }
    }
}

