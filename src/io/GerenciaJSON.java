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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GerenciaJSON {
    private List<Comprador> compradores;
    private List<Fornecedor> fornecedores;
    private List<Tecnologia> tecnologias;
    private List<Venda> vendas;

    private final String pastaRecursos = "recursos";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GerenciaJSON(List<Comprador> compradores,
                        List<Fornecedor> fornecedores,
                        List<Tecnologia> tecnologias,
                        Queue<Venda> vendas) {

        this.compradores = compradores;
        this.fornecedores = fornecedores;
        this.tecnologias = tecnologias;
        this.vendas = new ArrayList<>();
        this.vendas.addAll(vendas);
    }

    public List<String> salvarTodos(String prefixoNome) {
        if (prefixoNome.equals("")) {
            throw new IllegalArgumentException("O nome do arquivo não pode ser vazio.");
        }

        removeArquivosJsonExistentes();

        List<String> arquivosSalvos = new ArrayList<>();

        if (salvarLista(compradores, prefixoNome + "COMPRADORES.json")) {
            arquivosSalvos.add(prefixoNome + "COMPRADORES.json");
        }
        if (salvarLista(fornecedores, prefixoNome + "FORNECEDORES.json")) {
            arquivosSalvos.add(prefixoNome + "FORNECEDORES.json");
        }
        if (salvarLista(tecnologias, prefixoNome + "TECNLOGIAS.json")) {
            arquivosSalvos.add(prefixoNome + "TECNLOGIAS.json");
        }
        if (salvarLista(vendas, prefixoNome + "VENDAS.json")) {
            arquivosSalvos.add(prefixoNome + "VENDAS.json");
        }

        return arquivosSalvos;
    }

    private <T> boolean salvarLista(List<T> lista, String nomeArquivo) {
        if (lista == null || lista.isEmpty()) {
            return false;
        }

        Path caminho = Paths.get(pastaRecursos, nomeArquivo);

        try (BufferedWriter writer = Files.newBufferedWriter(caminho)) {
            gson.toJson(lista, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void removeArquivosJsonExistentes() {
        Path pasta = Paths.get(pastaRecursos);

        try (var stream = Files.list(pasta)) {
            stream.filter(p -> p.getFileName().toString().endsWith(".json"))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> carregarTodos(String prefixoNome) {
        if (prefixoNome.equals("")) {
            throw new IllegalArgumentException("O prefixo do arquivo não pode ser vazio.");
        }

        List<String> arquivosCarregados = new ArrayList<>();

        if (carregarCompradores(prefixoNome)) {
            arquivosCarregados.add(prefixoNome + "COMPRADORES.json");
        }
        if (carregarFornecedores(prefixoNome)) {
            arquivosCarregados.add(prefixoNome + "FORNECEDORES.json");
        }
        if (carregarTecnologias(prefixoNome)) {
            arquivosCarregados.add(prefixoNome + "TECNOLOGIAS.json");
        }
        if (carregarVendas(prefixoNome)) {
            arquivosCarregados.add(prefixoNome + "VENDAS.json");
        }

        return arquivosCarregados;
    }

    private boolean carregarCompradores(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "COMPRADORES.json");

        if (!Files.exists(caminho)) {
            return false;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Comprador>>() {
            }.getType();
            List<Comprador> lidos = gson.fromJson(br, tipoLista);

            compradores.clear();
            if (lidos != null) {
                compradores.addAll(lidos);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean carregarFornecedores(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "FORNECEDORES.json");

        if (!Files.exists(caminho)) {
            return false;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Fornecedor>>() {
            }.getType();
            List<Fornecedor> lidos = gson.fromJson(br, tipoLista);

            fornecedores.clear();
            if (lidos != null) {
                fornecedores.addAll(lidos);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean carregarTecnologias(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "TECNLOGIAS.json");

        if (!Files.exists(caminho)) {
            return false;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Tecnologia>>() {
            }.getType();
            List<Tecnologia> lidos = gson.fromJson(br, tipoLista);

            tecnologias.clear();
            if (lidos != null) {
                tecnologias.addAll(lidos);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean carregarVendas(String prefixoNome) {
        Path caminho = Paths.get(pastaRecursos, prefixoNome + "VENDAS.json");

        if (!Files.exists(caminho)) {
            return false;
        }

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            Type tipoLista = new TypeToken<List<Venda>>() {
            }.getType();
            List<Venda> lidos = gson.fromJson(br, tipoLista);

            vendas.clear();
            if (lidos != null) {
                vendas.addAll(lidos);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
