package aplicacao;

import entidades.*;
import io.GerenciaCSV;
import ui.MenuPrincipal;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ACMETech {
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaVendas gerenciaVendas;
    private final String nomeArquivoSaida = "log_entradas.txt";
    private final String caminhoSaida = "recursos";

    public ACMETech() {
        gerenciaFornecedores = new GerenciaFornecedores();
        gerenciaTecnologias = new GerenciaTecnologias();
        gerenciaCompradores = new GerenciaCompradores();
        gerenciaVendas = new GerenciaVendas();

        redirecionaSaida();
    }

    public void inicializar(){
        GerenciaCSV gerenciaCSV = new GerenciaCSV(gerenciaCompradores, gerenciaFornecedores, gerenciaTecnologias, gerenciaVendas);

        String logEntradas = "";

        logEntradas += gerenciaCSV.lerArquivoParticipantesEntrada("PARTICIPANTESENTRADA.csv");
        logEntradas += gerenciaCSV.lerArquivoTecnologiasEntrada("TECNOLOGIASENTRADA.csv");
        logEntradas += gerenciaCSV.lerArquivoVendasEntrada("VENDASENTRADA.csv");

        System.out.println(logEntradas);

        executar();
    }

    public void executar(){
        new MenuPrincipal(gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
    }

    private void redirecionaSaida() {
        try {
            Path pathArquivoSaida = Paths.get(caminhoSaida, nomeArquivoSaida);
            PrintStream streamSaida = new PrintStream(pathArquivoSaida.toFile(), Charset.forName("UTF-8"));

            System.setOut(streamSaida);
        } catch (Exception e) {
            System.out.println(e);
        }

        Locale.setDefault(Locale.ENGLISH);
    }
}
