package aplicacao;

import entidades.*;
import io.CarregarArquivos;
import ui.MenuPrincipal;

import java.nio.file.Path;
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
        /*adicionarTecnologiasProntas();
        adicionarFornecedoresProntos();
        adicionarCompradoresProntos();*/

        CarregarArquivos carregarArquivos = new CarregarArquivos(gerenciaCompradores, gerenciaFornecedores, gerenciaTecnologias, gerenciaVendas);
        carregarArquivos.lerArquivoParticipantes();
        carregarArquivos.lerArquivoTecnologias();

        executar();
    }

    public void executar(){
        new MenuPrincipal(gerenciaFornecedores, gerenciaTecnologias, gerenciaCompradores, gerenciaVendas);
    }

    private void adicionarTecnologiasProntas(){
        gerenciaTecnologias.addTecnologia(new Tecnologia(101L, "100 T Anvil", "Bigorna de 100 toneladas", 101.01, 100.1, 2000.2));
        gerenciaTecnologias.addTecnologia(new Tecnologia(444L, "Bird trap", "Armadilha para passaros", 444.44, 404.4, 400.4));
        gerenciaTecnologias.addTecnologia(new Tecnologia(202L, "FR armor", "Armadura resistente a fogo", 202.02, 200.2, 6000.6));
        gerenciaTecnologias.addTecnologia(new Tecnologia(303L, "Jericho", "Missil anti-missil", 303.03, 300.3, 3000.3));
        gerenciaTecnologias.addTecnologia(new Tecnologia(555L, "Shark repellent", "Repelente de tubarao", 555.55, 505.5, 50.5));
    }

    private void adicionarFornecedoresProntos(){
        gerenciaFornecedores.addFornecedor(new Fornecedor(4L, "ACME Corp.", new Date(1993 - 1900, 2, 3), Area.TI));
        gerenciaFornecedores.addFornecedor(new Fornecedor(44L, "Alianca rebelde", new Date(1993 - 1900, 2, 3), Area.ANDROIDES));
        gerenciaFornecedores.addFornecedor(new Fornecedor(22L, "Coyote", new Date(1993 - 1900, 2, 3), Area.EMERGENTE));
        gerenciaFornecedores.addFornecedor(new Fornecedor(1L, "Stark Industries", new Date(2005 - 1900, 4, 5), Area.TI));
        gerenciaFornecedores.addFornecedor(new Fornecedor(5L, "Wayne Enterprises", new Date(1984 - 1900, 3, 4), Area.ALIMENTOS));
    }

    private void adicionarCompradoresProntos() {
        gerenciaCompradores.addComprador(new Comprador(1001L, "Tony Stark", "EUA", "tony.stark@gmail.com"));

        gerenciaCompradores.addComprador(new Comprador(1002L, "Bruce Wayne", "EUA", "bruce.wayne@outlook.com"));

        gerenciaCompradores.addComprador(new Comprador(1003L, "Daffy Duck", "Desconhecido", "daffy.duck@yahoo.com"));

        gerenciaCompradores.addComprador(new Comprador(1004L, "Pernalonga", "Desconhecido", "pernalonga@gmail.com"));

        gerenciaCompradores.addComprador(new Comprador(1005L, "Rick Sanchez", "Multiverso", "rick.sanchez@outlook.com"));
    }


}
