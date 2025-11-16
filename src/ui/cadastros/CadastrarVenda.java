package ui.cadastros;

import entidades.*;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CadastrarVenda extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaVendas gerenciaVendas;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaTecnologias gerenciaTecnologias;
    private JComboBox<String> comboCompradores;
    private JComboBox<String> comboTecnologias;
    private HashMap<String, Comprador> mapCompradores;
    private HashMap<String, Tecnologia> mapTecnologias;
    private int desconto;

    private final List<String> labelsAtributos = new ArrayList<>(List.of(
            "Número: ", "Data (dd/MM/yyyy): "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(List.of(
            "FECHAR", "LIMPAR", "MOSTRAR VENDAS CADASTRADAS", "CONFIRMAR"
    ));

    public CadastrarVenda(GerenciaVendas gerenciaVendas, GerenciaCompradores gerenciaCompradores, GerenciaTecnologias gerenciaTecnologias) {
        super();
        setBasics();

        this.gerenciaVendas = gerenciaVendas;
        this.gerenciaCompradores = gerenciaCompradores;
        this.gerenciaTecnologias = gerenciaTecnologias;
        this.botoes = new ArrayList<>();
        this.camposTexto = new ArrayList<>();

        if (gerenciaCompradores.getCompradores().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há compradores cadastrados.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (gerenciaTecnologias.getTecnologias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há tecnologias cadastradas.", "ERRO", JOptionPane.WARNING_MESSAGE);
            return;
        }

        painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(criarPainelTitulo("CADASTRAR VENDA", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);

        painelBorda.add(criarPainelCampos(camposTexto, labelsAtributos), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);
        painelPrincipal.add(painelBorda, BorderLayout.CENTER);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    private void setBasics() {
        this.setTitle("Cadastrar Venda");
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

    @Override
    public JPanel criarPainelCampos(List<JTextField> camposTexto, List<String> labelsAtributos) {
        List<Comprador> compradores = gerenciaCompradores.getCompradores();
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();

        JPanel painelGrid = new JPanel(new GridLayout(4, 2, 10, 30));

        Font fonteCampo = new Font("Arial", Font.PLAIN, 20);
        Font fonteAtributo = new Font("Arial", Font.BOLD, 20);
        Font fonteCombo = new Font("Arial", Font.PLAIN, 18);

        JLabel labelComprador = new JLabel("Comprador: ");
        JLabel labelTecnologia = new JLabel("Tecnologia: ");

        labelComprador.setFont(fonteAtributo);
        labelTecnologia.setFont(fonteAtributo);

        mapCompradores = new HashMap<>();
        mapTecnologias = new HashMap<>();

        comboCompradores = new JComboBox<>();
        comboTecnologias = new JComboBox<>();

        comboCompradores.setFont(fonteCombo);
        comboTecnologias.setFont(fonteCombo);

        for (Comprador c : compradores) {
            String chave = c.getCod() + " - " + c.getNome();
            comboCompradores.addItem(chave);
            mapCompradores.put(chave, c);
        }

        for (Tecnologia t : tecnologias) {
            String chave = t.getId() + " - " + t.getNome();
            comboTecnologias.addItem(chave);
            mapTecnologias.put(chave, t);
        }

        painelGrid.add(labelComprador);
        painelGrid.add(comboCompradores);
        painelGrid.add(labelTecnologia);
        painelGrid.add(comboTecnologias);

        for (String s : labelsAtributos) {
            JLabel atributo = new JLabel(s);
            JTextField campo = new JTextField(70);

            atributo.setFont(fonteAtributo);
            campo.setFont(fonteCampo);

            painelGrid.add(atributo);
            painelGrid.add(campo);
            camposTexto.add(campo);
        }

        return painelGrid;
    }

    private void mostrarVendasCadastradas() {
        List<Venda> vendas = gerenciaVendas.getVendas();

        new RelatorioVendas(vendas);
    }

    private void cadastrarVenda() {
        if (camposVazios(camposTexto)) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            String chaveComprador = (String) comboCompradores.getSelectedItem();
            String chaveTecnologia = (String) comboTecnologias.getSelectedItem();

            Comprador comprador = mapCompradores.get(chaveComprador);
            Tecnologia tecnologia = mapTecnologias.get(chaveTecnologia);

            long numero = Long.parseLong(camposTexto.get(0).getText());
            String dataString = camposTexto.get(1).getText();

            Date data = gerenciaVendas.transformaData(dataString);

            Venda venda = new Venda(numero, data, comprador, tecnologia, (double) desconto / 100);

            if (!gerenciaVendas.verificaTecnologiaVendida(venda)) {
                if (!gerenciaVendas.addVenda(venda)) {
                    JOptionPane.showMessageDialog(this, "Número já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Venda cadastrada com sucesso.\n" +
                                                                                "Valor final: R$ " + venda.getValorFinal(), "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos(camposTexto);

                    if (desconto != 10) {
                        desconto += 1;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tecnologia já vendida. Selecione uma diferente e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número deve ser um valor numérico.", "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Tecnologia não possui um fornecedor.", "ERRO", JOptionPane.WARNING_MESSAGE);
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
        if (e.getSource() == botoes.get(2)) {
            mostrarVendasCadastradas();
        }
        if (e.getSource() == botoes.get(3)) {
            cadastrarVenda();
        }
    }
}
