package aplicacao;

import entidades.*;
import io.GerenciaCSV;
import io.GerenciaJSON;
import ui.MenuPrincipal;

public class ACMETech {
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaVendas gerenciaVendas;

    public ACMETech() {
        gerenciaFornecedores = new GerenciaFornecedores();
        gerenciaTecnologias = new GerenciaTecnologias();
        gerenciaCompradores = new GerenciaCompradores();
        gerenciaVendas = new GerenciaVendas();
    }

    public void inicializar(){
        GerenciaCSV gerenciaCSV = new GerenciaCSV(gerenciaCompradores, gerenciaFornecedores, gerenciaTecnologias, gerenciaVendas);

        gerenciaCSV.lerArquivoParticipantesEntrada("PARTICIPANTESENTRADA.csv", true);
        gerenciaCSV.lerArquivoTecnologiasEntrada("TECNOLOGIASENTRADA.csv", true);
        gerenciaCSV.lerArquivoVendasEntrada("VENDASENTRADA.csv", true);

        executar();
    }

    public void executar(){
        new MenuPrincipal(gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
    }
}
