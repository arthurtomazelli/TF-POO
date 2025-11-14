

package ui.cadastros;

import entidades.*;
import ui.relatorios.RelatorioCompradores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CadastrarComprador extends JFrame implements LimpaCampos, ActionListener {
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

        new RelatorioCompradores(compradores);
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

                if(!emailValido(email)){
                    JOptionPane.showMessageDialog(this, "Email deve terminar em um endereço válido.", "ERRO",  JOptionPane.ERROR_MESSAGE);
                } else if (!gerenciaCompradores.addComprador(comprador)) {
                    JOptionPane.showMessageDialog(this, "Código já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Comprador cadastrado com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Código deve ser um número.", "ERRO", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean emailValido(String email){
        if(email.endsWith("@gmail.com")||
                email.endsWith("@yahoo.com")||
                email.endsWith("@outlook.com")){
            return true;
        }

        return false;
    }

    @Override
    public void limparCampos() {
        for (JTextField campo : camposTexto) {
            campo.setText("");
        }
    }

    @Override
    public boolean camposVazios() {
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

