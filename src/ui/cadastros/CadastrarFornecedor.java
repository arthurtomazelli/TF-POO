package ui.cadastros;

import entidades.*;
import ui.funcoes.JFrameComFuncoes;
import ui.relatorios.RelatorioFornecedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class CadastrarFornecedor extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;
    private GerenciaFornecedores gerenciaFornecedores;

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList(
            "Código: ", "Nome: ", "Fundação (dd/MM/yyyy): ", "Área: "
    ));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "MOSTRAR FORNECEDORES CADASTRADOS", "CONFIRMAR"
    ));

    public CadastrarFornecedor(GerenciaFornecedores gerenciaFornecedores) {
        super();
        setBasics();
        this.gerenciaFornecedores = gerenciaFornecedores;
        this.botoes = new ArrayList<>();
        this.camposTexto = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(criarPainelTitulo("CADASTRAR FORNECEDOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(camposTexto, labelsAtributos), BorderLayout.CENTER);

        painelPrincipal.add(criarPainelBotoes(botoes, labelsBotoes, this), BorderLayout.SOUTH);

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
        int horizontal = (int) (largura * 0.20) - 50;

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    private void mostrarFornecedoresCadastrados() {
        List<Fornecedor> fornecedores = gerenciaFornecedores.getFornecedores();

        new RelatorioFornecedores(fornecedores);
    }

    private void cadastrarFornecedor() {
        if (camposVazios(camposTexto)) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            long cod = Long.parseLong(camposTexto.get(0).getText());
            String nome = camposTexto.get(1).getText();
            String dataString = camposTexto.get(2).getText();
            String area = (camposTexto.get(3).getText());

            Date data = transformaData(dataString);
            Area areaEnum = verificaArea(area);

            Fornecedor fornecedor = new Fornecedor(cod, nome, data, areaEnum);

            if (!gerenciaFornecedores.addFornecedor(fornecedor)) {
                JOptionPane.showMessageDialog(this, "Código já cadastrado. Altere-o e tente novamente.", "ERRO", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso.", "SUCESSO", JOptionPane.PLAIN_MESSAGE);
                limparCampos(camposTexto);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e ano de fundação devem ser números.", "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (EnumConstantNotPresentException e){
            JOptionPane.showMessageDialog(this,"Área inválida. Deve ser: 'TI', 'ANDROIDES', 'EMERGENTE' ou 'ALIMENTOS'.", "ERRO", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRO", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Date transformaData(String dataString) {
        int cont = 0;

        try {
            String dia = "", mes = "", ano = "";

            for (int i = 0; i < dataString.length(); i++) {
                char caracter = dataString.charAt(i);

                if (caracter == '/') {
                    cont++;
                    continue;
                }

                if (cont == 0) {
                    dia += caracter;
                } else if (cont == 1) {
                    mes += caracter;
                } else if (cont == 2) {
                    ano += caracter;
                }
            }

            int anoInt = Integer.parseInt(ano);

            if(anoInt < 2000 || anoInt > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("Ano deve ser entre 2000 e 2025. Altere-o e tente novamente.");
            }

            int mesInt = Integer.parseInt(mes);

            if(mesInt < 1 || mesInt > 12) {
                throw new IllegalArgumentException("Mês deve ser entre 1 e 12. Altere-o e tente novamente.");
            }

            int diaInt = Integer.parseInt(dia);

            if(diaInt < 1 || diaInt > 30) {
                throw new IllegalArgumentException("Dia deve ser entre 1 e 30. Altere-o e tente novamente.");
            }

            return new Date(anoInt - 1900, mesInt - 1, diaInt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de data inválido. Altere-o para 'dd/MM/yyyy' e tente novamente.");
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoes.get(0)) {
            this.dispose();
        }
        if (e.getSource() == botoes.get(1)) {
            limparCampos(camposTexto);
        }
        if (e.getSource() == botoes.get(2)) {
            mostrarFornecedoresCadastrados();
        }
        if (e.getSource() == botoes.get(3)) {
            cadastrarFornecedor();
        }
    }
}
