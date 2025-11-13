package ui.cadastros;

import entidades.*;
import ui.relatorios.RelatorioFornecedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class CadastrarFornecedor extends JFrame implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaFornecedores gerenciaFornecedores;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList(
            "Código: ", "Nome: ", "Ano de Fundação: ", "Área: "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR FORNECEDORES CADASTRADOS", "CONFIRMAR"
    ));

    public CadastrarFornecedor(GerenciaFornecedores gerenciaFornecedores) {
        super();
        setBasics();
        this.gerenciaFornecedores = gerenciaFornecedores;

        painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(criarPainelTitulo("CADASTRAR FORNECEDOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Fornecedor");
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

    private void mostrarFornecedoresCadastrados() {
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        new RelatorioFornecedores(fornecedores);
    }

    private void cadastrarFornecedor() {
        if (camposVazios()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            long cod = Long.parseLong(camposTexto.get(0).getText());
            String nome = camposTexto.get(1).getText();
            int ano = Integer.parseInt(camposTexto.get(2).getText()) + 1;
            String area = (camposTexto.get(3).getText());

            Date data = transformaData(ano);
            Area areaEnum = verificaArea(area);

            Fornecedor fornecedor = new Fornecedor(cod, nome, data, areaEnum);

            if (!gerenciaFornecedores.addFornecedor(fornecedor)) {
                JOptionPane.showMessageDialog(this, "Código já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e ano de fundação devem ser números.", "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (EnumConstantNotPresentException e){
            JOptionPane.showMessageDialog(this,"Área inválida. Deve ser: 'TI', 'ANDROIDES', 'EMERGENTE' ou 'ALIMENTOS'.", "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Date transformaData(int ano){
        int anoMinimo = 1800;

        if(ano < anoMinimo){
            throw new IllegalArgumentException("O ano de fundação deve ser válido. (Entre " + anoMinimo + " e " + LocalDate.now().getYear() + ").");
        }

        return new Date(ano, 0, 0);
    }

    private Area verificaArea(String area) {
        if(area == null){
            throw new EnumConstantNotPresentException(Area.class, area);
        }

        area = area.toUpperCase();

        if(area.equals("TI")){
            return Area.TI;
        } else if(area.equals("ANDROIDES")){
            return Area.ANDROIDES;
        } else if(area.equals("EMERGENTE")){
            return Area.EMERGENTE;
        } else if(area.equals("ALIMENTOS")){
            return Area.ALIMENTOS;
        } else{
            throw new EnumConstantNotPresentException(Area.class, area);
        }
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
        if (e.getSource() == botoes.get(2)) {
            mostrarFornecedoresCadastrados();
        }
        if (e.getSource() == botoes.get(3)) {
            cadastrarFornecedor();
        }
    }
}
