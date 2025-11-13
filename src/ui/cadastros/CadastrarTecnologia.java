package ui.cadastros;

import entidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CadastrarTecnologia extends JFrame implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList(
            "ID: ", "Modelo: ", "Descrição: ", "Valor Base: ", "Peso: ", "Temperatura: "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR DADOS CADASTRADOS", "DEFINIR FORNECEDOR", "CONFIRMAR"
    ));


    public CadastrarTecnologia(GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias) {
        super();
        setBasics();

        this.gerenciaFornecedores = gerenciaFornecedores;
        this.gerenciaTecnologias = gerenciaTecnologias;

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("CADASTRAR TECNOLOGIA", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Tecnologia");
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

    private void mostrarDadosCadastrados() {
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        if (tecnologias.isEmpty() && fornecedores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias nem fornecedores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
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

        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Tecnologias =-=-=-=-=-=-=\n");

        if (tecnologias.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhuma tecnologia cadastrada.\n");
        } else {
            for (Tecnologia t : tecnologias) {
                areaTexto.setText(areaTexto.getText() + t + "\n");
            }
        }

        areaTexto.setText(areaTexto.getText() + "\n");
        areaTexto.setText(areaTexto.getText() + "=-=-=-=-=-=-= Fornecedores =-=-=-=-=-=-=\n");

        if (fornecedores.isEmpty()) {
            areaTexto.setText(areaTexto.getText() + "Nenhum fornecedor cadastrado.\n");
        } else {
            for (Fornecedor f : fornecedores) {
                areaTexto.setText(areaTexto.getText() + f.geraDescricao() + "\n");
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


    private void definirFornecedor() {
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        if(listasVazias(tecnologias, fornecedores)) {
            return;
        }

        HashMap<String, Tecnologia> mapaTecnologias = new HashMap<>();
        HashMap<String, Fornecedor> mapaFornecedores = new HashMap<>();

        JDialog dialogFornecedor = new JDialog();
        dialogFornecedor.setTitle("Definir Fornecedor");
        dialogFornecedor.setSize((int) (getWidth() * 0.8), (int)(getHeight() * 0.65));
        dialogFornecedor.setLocationRelativeTo(null);
        dialogFornecedor.setLayout(new BorderLayout());

        JPanel painelSelecao = new JPanel();
        painelSelecao.setLayout(new BoxLayout(painelSelecao, BoxLayout.Y_AXIS));

        JPanel painelTecnologia = new JPanel();
        JPanel painelFornecedor = new JPanel();

        JComboBox<String> comboTecnologia = new JComboBox<>();
        JComboBox<String> comboFornecedor = new JComboBox<>();

        Font fonteCombo = new Font("Arial", Font.PLAIN, 18);
        Font fonteLabel = new Font("Arial", Font.BOLD, 20);

        comboTecnologia.setFont(fonteCombo);
        comboFornecedor.setFont(fonteCombo);

        comboTecnologia.setPreferredSize(new Dimension(400, 35));
        comboFornecedor.setPreferredSize(new Dimension(400, 35));

        for (Tecnologia t : tecnologias) {
            String chave = t.getId() + " - " + t.getNome();
            comboTecnologia.addItem(chave);
            mapaTecnologias.put(chave, t);
        }

        for (Fornecedor f : fornecedores) {
            String chave = f.getCod() + " - " + f.getNome();
            comboFornecedor.addItem(chave);
            mapaFornecedores.put(chave, f);
        }

        JLabel labelTecnologia = new JLabel("Tecnologia: ");
        JLabel labelFornecedor = new JLabel("Fornecedor: ");
        labelTecnologia.setFont(fonteLabel);
        labelFornecedor.setFont(fonteLabel);

        painelTecnologia.add(labelTecnologia);
        painelTecnologia.add(comboTecnologia);

        painelFornecedor.add(labelFornecedor);
        painelFornecedor.add(comboFornecedor);

        painelSelecao.add(painelTecnologia);
        painelSelecao.add(painelFornecedor);

        JPanel painelChao = new JPanel();

        JButton botaoCancelar = criarBotao("VOLTAR");
        botaoCancelar.addActionListener(e -> dialogFornecedor.dispose());

        JButton botaoConfirmar = criarBotao("CONFIRMAR");
        botaoConfirmar.addActionListener(e -> {
            String chaveTecnologia = (String) comboTecnologia.getSelectedItem();
            String chaveFornecedor = (String) comboFornecedor.getSelectedItem();

            Tecnologia tecnologia = mapaTecnologias.get(chaveTecnologia);
            Fornecedor fornecedor = mapaFornecedores.get(chaveFornecedor);

            tecnologia.defineFornecedor(fornecedor);

            JOptionPane.showMessageDialog(this,
                    "Fornecedor '" + fornecedor.getNome() + "' definido para tecnologia '" + tecnologia.getNome() + "'.",
                    "SUCESSO", JOptionPane.PLAIN_MESSAGE);

        });

        painelChao.add(botaoCancelar);
        painelChao.add(botaoConfirmar);

        dialogFornecedor.add(criarPainelTitulo("DEFINIR FORNECEDOR", 30), BorderLayout.NORTH);
        dialogFornecedor.add(painelSelecao, BorderLayout.CENTER);
        dialogFornecedor.add(painelChao, BorderLayout.SOUTH);

        dialogFornecedor.setVisible(true);
    }

    private void cadastrarItem() {
        if (camposVazios()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                long id = Long.parseLong(camposTexto.get(0).getText());
                String modelo = camposTexto.get(1).getText();
                String descricao = camposTexto.get(2).getText();
                double valorBase = Double.parseDouble(camposTexto.get(3).getText());
                double peso = Double.parseDouble(camposTexto.get(4).getText());
                double temperatura = Double.parseDouble(camposTexto.get(5).getText());

                Tecnologia tecnologia = new Tecnologia(id, modelo, descricao, valorBase, peso, temperatura);

                if (!gerenciaTecnologias.addTecnologia(tecnologia)) {
                    JOptionPane.showMessageDialog(this, "ID já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Tecnologia cadastrada com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID, Valor, Peso e Temperatura devem ser números.", "ERRO", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean listasVazias(List<Tecnologia> tecnologias, List<Fornecedor> fornecedores) {
        if (tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return true;
        }

        if (fornecedores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há fornecedores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
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
        if (e.getSource() == botoes.get(2)) {
            mostrarDadosCadastrados();
        }
        if (e.getSource() == botoes.get(3)) {
            definirFornecedor();
        }
        if (e.getSource() == botoes.get(4)) {
            cadastrarItem();
        }
    }
}
