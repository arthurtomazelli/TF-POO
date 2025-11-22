package ui.funcoes;

import entidades.Comprador;

import javax.swing.*;
import javax.swing.table.TableStringConverter;
import java.awt.*;
import java.util.List;

public abstract class JDialogComFuncoes extends JDialog {
    private JTextArea areaTexto;
    private final Color corPrincipal = new Color(20, 86, 160);

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

    public JPanel criarPainelTexto(){
        JPanel painelTexto = new JPanel();

        areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        painelTexto.add(areaTexto);

        return painelTexto;
    }

    public <T extends JDialog> JPanel criarPainelChao(T classe){
        JPanel painelChao = new JPanel();

        JButton botaoOK = criarBotao("OK");
        botaoOK.addActionListener(e -> classe.dispose());
        painelChao.add(botaoOK);

        return painelChao;
    }

    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setMargin(new Insets(10, 20, 10, 20));
        botao.setBackground(Color.WHITE);
        botao.setForeground(corPrincipal);

        return botao;
    }

    public abstract void preencherCampos();

    public JTextArea getAreaTexto() {
        return areaTexto;
    }

    public Color getCorPrincipal() {
        return corPrincipal;
    }
}
