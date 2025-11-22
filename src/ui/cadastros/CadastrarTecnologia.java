package ui.cadastros;

import entidades.*;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioTecnologias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CadastrarTecnologia extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;

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
        this.botoes = new ArrayList<>();
        this.camposTexto = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("CADASTRAR TECNOLOGIA", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(camposTexto, labelsAtributos), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Tecnologia");
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

    private void mostrarDadosCadastrados() {
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        new RelatorioTecnologias("DADOS CADASTRADOS", gerenciaTecnologias, tecnologias, fornecedores);
    }


    private void definirFornecedor() {
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        if (listasVazias(tecnologias, fornecedores)) {
            return;
        }

        HashMap<String, Tecnologia> mapaTecnologias = new HashMap<>();
        HashMap<String, Fornecedor> mapaFornecedores = new HashMap<>();

        JDialog dialogFornecedor = new JDialog();
        dialogFornecedor.setTitle("Definir Fornecedor");
        dialogFornecedor.setSize((int) (getWidth() * 0.8), (int) (getHeight() * 0.65));
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
        if (camposVazios(camposTexto)) {
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
                    limparCampos(camposTexto);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            limparCampos(camposTexto);
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
