package ui.cadastros;

import entidades.*;
import ui.relatorios.RelatorioVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CadastrarVenda extends JFrame implements ActionListener {
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

    private final Color corPrincipal = new Color(20, 86, 160);

    private final List<String> labelsAtributos = new ArrayList<>(List.of(
            "Número: ", "Data (yyyy/MM/dd): "
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

        painelBorda.add(criarPainelCampos(), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(), BorderLayout.SOUTH);
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
        List<Comprador> compradores = gerenciaCompradores.getCompradores();
        List<Tecnologia> tecnologias = gerenciaTecnologias.getTecnologias();

        JPanel painelGrid = new JPanel(new GridLayout(4, 2, 10, 30));
        camposTexto = new ArrayList<>();

        Font fonteCampo = new Font("Arial", Font.PLAIN, 20);
        Font fonteAtributo = new Font("Arial", Font.BOLD, 20);

        JLabel labelComprador = new JLabel("Comprador: ");
        labelComprador.setFont(fonteAtributo);

        JLabel labelTecnologia = new JLabel("Tecnologia: ");
        labelTecnologia.setFont(fonteAtributo);

        mapCompradores = new HashMap<>();
        comboCompradores = new JComboBox<>();
        comboCompradores.setFont(new Font("Arial", Font.PLAIN, 18));

        for (Comprador c : compradores) {
            String chave = c.getCod() + " - " + c.getNome();
            comboCompradores.addItem(chave);
            mapCompradores.put(chave, c);
        }

        painelGrid.add(labelComprador);
        painelGrid.add(comboCompradores);

        mapTecnologias = new HashMap<>();
        comboTecnologias = new JComboBox<>();
        comboTecnologias.setFont(new Font("Arial", Font.PLAIN, 18));

        for (Tecnologia t : tecnologias) {
            String chave = t.getId() + " - " + t.getNome();
            comboTecnologias.addItem(chave);
            mapTecnologias.put(chave, t);
        }

        painelGrid.add(labelTecnologia);
        painelGrid.add(comboTecnologias);

        for (String s : labelsAtributos) {
            JTextField campo = new JTextField(70);
            JLabel atributo = new JLabel(s);

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

    private void mostrarVendasCadastradas() {
        List<Venda> vendas = gerenciaVendas.getVendas();

        new RelatorioVendas(vendas);
    }

    private void cadastrarVenda() {
        if (camposVazios()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            String chaveComprador = (String) comboCompradores.getSelectedItem();
            String chaveTecnologia = (String) comboTecnologias.getSelectedItem();

            Comprador comprador = mapCompradores.get(chaveComprador);
            Tecnologia tecnologia = mapTecnologias.get(chaveTecnologia);

            long numero = Long.parseLong(camposTexto.get(0).getText());
            String dataStr = camposTexto.get(1).getText();

            Date data = transformaData(dataStr);

            System.out.println(data);

            Venda venda = new Venda(numero, data, comprador, tecnologia, (double) desconto / 100);

            //falta fazer ele verificar data, formato do email, mostrar os dados de vendas ja cadastradas e mostrar o valor final na tela

            if (!verificaTecnologiaVendida(gerenciaVendas.getVendas(), venda)) {
                if (!gerenciaVendas.addVenda(venda)) {
                    JOptionPane.showMessageDialog(this, "Número já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Venda cadastrada com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                    limparCampos();

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

    private Date transformaData(String dataStr) {
        int cont = 0;

        try {
            String ano = "", mes = "", dia = "";

            for (int i = 0; i < dataStr.length(); i++) {
                char caracter = dataStr.charAt(i);

                if (caracter == '/') {
                    cont++;
                    continue;
                }

                if (cont == 0) {
                    ano += caracter;
                } else if (cont == 1) {
                    mes += caracter;
                } else if (cont == 2) {
                    dia += caracter;
                }
            }

            int anoInt = Integer.parseInt(ano);

            if(anoInt < 2000 || anoInt > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("Ano deve ser entre 2000 e 2025. Altere-o e tente novamente.");
            }

            System.out.println(anoInt);

            int mesInt = Integer.parseInt(mes);

            if(mesInt < 1 || mesInt > 12) {
                throw new IllegalArgumentException("Mês deve ser entre 1 e 12. Altere-o e tente novamente.");
            }

            int diaInt = Integer.parseInt(dia);

            if(diaInt < 1 || diaInt > 30) {
                throw new IllegalArgumentException("Dia deve ser entre 1 e 30. Altere-o e tente novamente.");
            }

            return new Date(anoInt, mesInt, diaInt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de data inválido. Altere-o para 'yyyy/MM/dd' e tente novamente.");
        }
    }

    private boolean verificaTecnologiaVendida(List<Venda> vendas, Venda novaVenda) {
        for (Venda v : vendas) {
            if (v.getTecnologia().getId() == novaVenda.getTecnologia().getId()) {
                return true;
            }
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
        comboCompradores.setSelectedIndex(-1);
        comboTecnologias.setSelectedIndex(-1);
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
            mostrarVendasCadastradas();
        }
        if (e.getSource() == botoes.get(3)) {
            cadastrarVenda();
        }
    }
}
