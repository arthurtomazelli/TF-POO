package ui.removerAlterar;

import entidades.Comprador;
import entidades.GerenciaCompradores;
import ui.personalizados.JFrameComFuncoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolicitarDadosComprador extends JFrameComFuncoes implements ActionListener {
    private JPanel painelPrincipal;
    private JPanel painelBorda;
    private List<JTextField> camposTexto;
    private List<JButton> botoes;

    private GerenciaCompradores gerenciaCompradores;
    private Comprador compradorAlterar;
    private AlterarDadosComprador telaOrigem;

    private final List<String> labelsAtributos = new ArrayList<>(Arrays.asList("Nome: ", "País: ", "Email: "));

    private final List<String> labelsBotoes = new ArrayList<>(Arrays.asList(
            "FECHAR", "LIMPAR", "CONFIRMAR"
    ));

    public SolicitarDadosComprador(GerenciaCompradores gerenciaCompradores, Comprador compradorAlterar, AlterarDadosComprador telaOrigem) {
        super();
        setBasics();

        this.gerenciaCompradores = gerenciaCompradores;
        this.compradorAlterar = compradorAlterar;
        this.telaOrigem = telaOrigem;

        this.camposTexto = new ArrayList<>();
        this.botoes = new ArrayList<>();

        painelPrincipal = new JPanel(new BorderLayout());

        painelPrincipal.add(criarPainelTitulo("ALTERAR DADOS COMPRADOR", 40), BorderLayout.NORTH);

        painelBorda = new JPanel(new BorderLayout());
        setBorda(painelBorda);
        painelBorda.add(criarPainelCampos(camposTexto, labelsAtributos), BorderLayout.CENTER);

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

        painelBorda.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical + 100, horizontal));
    }

    public void alterarDados() {
        if (camposVazios(camposTexto)) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "CAMPOS VAZIOS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String nome = camposTexto.get(0).getText();
            String pais = camposTexto.get(1).getText();
            String email = camposTexto.get(2).getText();

            if (!gerenciaCompradores.emailValido(email)) {
                JOptionPane.showMessageDialog(this, "Email deve terminar em um endereço válido.", "ERRO", JOptionPane.WARNING_MESSAGE);
            } else {
                List<Object> novosAtributos = new ArrayList<>();
                novosAtributos.add(nome);
                novosAtributos.add(pais);
                novosAtributos.add(email);

                gerenciaCompradores.alterarDadosComprador(compradorAlterar, novosAtributos);

                JOptionPane.showMessageDialog(this,
                        "Dados do comprador '" + compradorAlterar.getCod() + "' alterados com sucesso.",
                        "SUCESSO", JOptionPane.PLAIN_MESSAGE);

                if (telaOrigem != null) {
                    telaOrigem.limparCamposAposAlteracao();
                }

                this.dispose();
            }
        }
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
            limparCampos(camposTexto);
        }
        if (e.getSource() == botoes.get(2)) {
            alterarDados();
        }
    }
}
