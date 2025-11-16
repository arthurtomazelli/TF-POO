package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GerenciaJSON {
    private List<Comprador> compradores;
    private List<Fornecedor> fornecedores;
    private List<Tecnologia> tecnologias;
    private List<Venda> vendas;

    private final String pastaRecursos = "recursos";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GerenciaJSON(List<Comprador> compradores, List<Fornecedor> fornecedores, List<Tecnologia> tecnologias, List<Venda> vendas) {
        this.compradores = compradores;
        this.fornecedores = fornecedores;
        this.tecnologias = tecnologias;
        this.vendas = vendas;
    }

    public void salvarTodos(String prefixoNome) {
        if (prefixoNome.equals("")) {
            throw new IllegalArgumentException("O nome do arquivo não pode ser vazio.");
        }

        salvarListaPorTipo(compradores, prefixoNome);
        salvarListaPorTipo(fornecedores, prefixoNome);
        salvarListaPorTipo(tecnologias, prefixoNome);
        salvarListaPorTipo(vendas, prefixoNome);
    }

    private <T> void salvarListaPorTipo(List<T> lista, String prefixoNome) {
        if (lista.isEmpty()) {
            return;
        }

        T primeiro = lista.getFirst();
        String sufixo = "";

        if (primeiro instanceof Comprador) {
            sufixo = "COMPRADORES";
        } else if (primeiro instanceof Fornecedor) {
            sufixo = "FORNECEDORES";
        } else if (primeiro instanceof Tecnologia) {
            sufixo = "TECNLOGIAS";
        } else if (primeiro instanceof Venda) {
            sufixo = "VENDAS";
        }

        Path caminho = Paths.get(pastaRecursos, prefixoNome + sufixo + ".json");

        try (BufferedWriter writer = Files.newBufferedWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarTodos(String prefixoNome) {
        carregarCompradores(prefixoNome);
        carregarFornecedores(prefixoNome);
        carregarTecnologias(prefixoNome);
        carregarVendas(prefixoNome);

        //caso a pessoa insira e nao funcione, devolver um erro de "nome incorreto ou não há arquivos salvos."
        //talvez fazer cada um retornar um int e ver se é 0 ou 1
    }

    private void carregarCompradores(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "COMPRADORES.json");

        if (!Files.exists(caminho)) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Comprador>>(){}.getType();
            List<Comprador> lidos = gson.fromJson(br, tipoLista);

            compradores.clear();

            if (lidos != null) {
                compradores.addAll(lidos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarFornecedores(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "FORNECEDORES.json");

        if (!Files.exists(caminho)) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Fornecedor>>(){}.getType();
            List<Fornecedor> lidos = gson.fromJson(br, tipoLista);

            fornecedores.clear();

            if (lidos != null) {
                fornecedores.addAll(lidos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarTecnologias(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "TECNLOGIAS.json");

        if (!Files.exists(caminho)) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Tecnologia>>(){}.getType();
            List<Tecnologia> lidos = gson.fromJson(br, tipoLista);

            tecnologias.clear();

            if (lidos != null) {
                tecnologias.addAll(lidos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarVendas(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "VENDAS.json");

        if (!Files.exists(caminho)) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Venda>>(){}.getType();
            List<Venda> lidos = gson.fromJson(br, tipoLista);

            vendas.clear();

            if (lidos != null) {
                vendas.addAll(lidos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


