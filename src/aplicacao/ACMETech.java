package aplicacao;

import entidades.*;
import ui.CadastrarComprador;
import ui.CadastrarFornecedor;
import ui.CadastrarTecnologia;
import ui.MenuPrincipal;

import java.util.Date;

public class ACMETech {
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaCompradores gerenciaCompradores;

    public ACMETech() {
        gerenciaFornecedores = new GerenciaFornecedores();
        gerenciaTecnologias = new GerenciaTecnologias();
        gerenciaCompradores = new GerenciaCompradores();
    }

    public void inicializar(){
        adicionarTecnologiasProntas();
        adicionarFornecedoresProntos();

        executar();
    }

    public void executar(){
        new MenuPrincipal(gerenciaFornecedores, gerenciaTecnologias);
    }

    private void adicionarTecnologiasProntas(){
        gerenciaTecnologias.addTecnologia(new Tecnologia(101L, "100 T Anvil", "Bigorna de 100 toneladas", 101.01, 100.1, 2000.2));
        gerenciaTecnologias.addTecnologia(new Tecnologia(444L, "Bird trap", "Armadilha para passaros", 444.44, 404.4, 400.4));
        gerenciaTecnologias.addTecnologia(new Tecnologia(202L, "FR armor", "Armadura resistente a fogo", 202.02, 200.2, 6000.6));
        gerenciaTecnologias.addTecnologia(new Tecnologia(303L, "Jericho", "Missil anti-missil", 303.03, 300.3, 3000.3));
        gerenciaTecnologias.addTecnologia(new Tecnologia(555L, "Shark repellent", "Repelente de tubarao", 555.55, 505.5, 50.5));
    }

    private void adicionarFornecedoresProntos(){
        gerenciaFornecedores.addFornecedor(new Fornecedor(4L, "ACME Corp.", new Date(1993, 2, 3), Area.TI));
        gerenciaFornecedores.addFornecedor(new Fornecedor(44L, "Alianca rebelde", new Date(1993, 2, 3), Area.ANDROIDES));
        gerenciaFornecedores.addFornecedor(new Fornecedor(22L, "Coyote", new Date(1993, 2, 3), Area.EMERGENTE));
        gerenciaFornecedores.addFornecedor(new Fornecedor(1L, "Stark Industries", new Date(2005, 4, 5), Area.TI));
        gerenciaFornecedores.addFornecedor(new Fornecedor(5L, "Wayne Enterprises", new Date(1984, 3, 4), Area.ALIMENTOS));
    }
}
