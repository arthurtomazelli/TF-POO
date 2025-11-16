package ui.personalizados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class JFrameComFuncoes extends JFrame {
    private final Color corPrincipal = new Color(20, 86, 160);

    public JPanel criarPainelCampos(List<JTextField> camposTexto, List<String> labelsAtributos) {
        JPanel painelGrid = new JPanel(new GridLayout(labelsAtributos.size(), 2, 10, 10));

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

    public <T extends ActionListener> JPanel criarPainelBotoes(List<JButton> botoes, List<String> labelsBotoes, T classe) {
        JPanel painelBotoes = new JPanel();

        for (String s : labelsBotoes) {
            JButton botao = new JButton(s);

            botao.setMargin(new Insets(10, 20, 10, 20));
            botao.setBackground(Color.WHITE);
            botao.setForeground(corPrincipal);
            botao.addActionListener(classe);

            botoes.add(botao);
            painelBotoes.add(botao);
        }

        return painelBotoes;
    }

    public JPanel criarPainelTitulo(String textoTitulo, int tamanhoFonte) {
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

    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    public void limparCampos(List<JTextField> camposTexto) {
        for (JTextField campo : camposTexto) {
            campo.setText("");
        }
    }

    public boolean camposVazios(List<JTextField> camposTexto) {
        for (JTextField campo : camposTexto) {
            if (campo.getText().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public Color getCorPrincipal() {
        return corPrincipal;
    }
}
