package aplicacao;

import entidades.*;
import io.CarregaArquivos;
import ui.MenuPrincipal;

import java.util.Date;

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
        CarregaArquivos carregaArquivos = new CarregaArquivos(gerenciaCompradores, gerenciaFornecedores, gerenciaTecnologias, gerenciaVendas);

        carregaArquivos.lerArquivoParticipantes("PARTICIPANTESENTRADA.csv", true);
        carregaArquivos.lerArquivoTecnologias("TECNOLOGIASENTRADA.csv", true);
        carregaArquivos.lerArquivoVendas("VENDASENTRADA.csv", true);

        executar();
    }

    public void executar(){
        new MenuPrincipal(gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
    }
}
