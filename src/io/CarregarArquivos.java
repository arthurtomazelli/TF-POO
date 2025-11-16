package io;

import entidades.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CarregarArquivos extends JOptionPane {
    private GerenciaCompradores gerenciaCompradores;
    private GerenciaFornecedores gerenciaFornecedores;
    private GerenciaTecnologias gerenciaTecnologias;
    private GerenciaVendas gerenciaVendas;
    private int contCadastroSucesso = 0;
    private int contErrosFormato = 0;
    private int contErrosDadoInvalido = 0;
    private int contErrosObjRepetido = 0;

    private final String pastaRecursos = "recursos";

    public CarregarArquivos(GerenciaCompradores gerenciaCompradores, GerenciaFornecedores gerenciaFornecedores, GerenciaTecnologias gerenciaTecnologias, GerenciaVendas gerenciaVendas) {
        this.gerenciaCompradores = gerenciaCompradores;
        this.gerenciaFornecedores = gerenciaFornecedores;
        this.gerenciaTecnologias = gerenciaTecnologias;
        this.gerenciaVendas = gerenciaVendas;
    }

    public void lerArquivoParticipantes() {
        try {
            Path arquivoParticipantes = Paths.get(pastaRecursos, "PARTICIPANTESENTRADA.csv");

            lerArquivoParticipantes(arquivoParticipantes);

            showMessageDialog(this,
                    "Leitura de participantes finalizada.\n" +
                            "Participantes cadastrados: " + contCadastroSucesso + "\n" +
                            "Objetos repetidos: " + contErrosObjRepetido + "\n" +
                            "Erros de dados inválidos: " + contErrosDadoInvalido + "\n" +
                            "Erros de formato (CSV): " + contErrosFormato,
                    "RESULTADO", JOptionPane.PLAIN_MESSAGE);

        } catch (IOException e) {
            showMessageDialog(this, e.getMessage(), "ERRO DE E/S", JOptionPane.ERROR_MESSAGE);
        } finally {
            contCadastroSucesso = 0;
            contErrosFormato = 0;
            contErrosDadoInvalido = 0;
            contErrosObjRepetido = 0;
        }
    }

    public void lerArquivoParticipantes(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) {
                    contErrosFormato++;
                    continue;
                }

                try (Scanner sc = new Scanner(linha)) {
                    sc.useDelimiter(";");

                    Long cod = Long.parseLong(sc.next());
                    String nome = sc.next();
                    int tipo = Integer.parseInt(sc.next());
                    String fundacao_pais = sc.next();
                    String area_email = sc.next();

                    if (tipo == 1) {
                        Date data = gerenciaFornecedores.transformaData(fundacao_pais);
                        Area area = gerenciaFornecedores.verificaArea(area_email);

                        Fornecedor fornecedor = new Fornecedor(cod, nome, data, area);

                        if (!gerenciaFornecedores.addFornecedor(fornecedor)) {
                            contErrosObjRepetido++;
                        } else {
                            contCadastroSucesso++;
                        }

                    } else if (tipo == 2) {
                        if (!gerenciaCompradores.emailValido(area_email)) {
                            contErrosDadoInvalido++;
                        } else {
                            Comprador comprador = new Comprador(cod, nome, fundacao_pais, area_email);

                            if (!gerenciaCompradores.addComprador(comprador)) {
                                contErrosObjRepetido++;
                            } else {
                                contCadastroSucesso++;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    contErrosFormato++;
                } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
                    contErrosDadoInvalido++;
                }
            }

        } catch (IOException e) {
            throw new IOException("Impossivel realizar leitura. Arquivo de participantes não encontrado.");
        }
    }

    public void lerArquivoTecnologias() {
        try {
            Path arquivoParticipantes = Paths.get(pastaRecursos, "TECNOLOGIASENTRADA.csv");

            lerArquivoTecnologias(arquivoParticipantes);

            showMessageDialog(this,
                    "Leitura de tecnologias finalizada.\n" +
                            "Tecnologias cadastradas: " + contCadastroSucesso + "\n" +
                            "Objetos repetidos: " + contErrosObjRepetido + "\n" +
                            "Erros de dados inválidos: " + contErrosDadoInvalido + "\n" +
                            "Erros de formato (CSV): " + contErrosFormato,
                    "RESULTADO", JOptionPane.PLAIN_MESSAGE);

        } catch (IOException e) {
            showMessageDialog(this, e.getMessage(), "ERRO DE E/S", JOptionPane.ERROR_MESSAGE);
        } finally {
            contCadastroSucesso = 0;
            contErrosFormato = 0;
            contErrosDadoInvalido = 0;
            contErrosObjRepetido = 0;
        }
    }

    public void lerArquivoTecnologias(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) {
                    contErrosFormato++;
                    continue;
                }

                try (Scanner sc = new Scanner(linha)) {
                    sc.useDelimiter(";");

                    Long id = Long.parseLong(sc.next());
                    String modelo = sc.next();
                    String descricao = sc.next();
                    double valorBase = (Double.parseDouble(sc.next()));
                    double peso = Double.parseDouble(sc.next());
                    double temperatura = Double.parseDouble(sc.next());
                    long codFornecedor = Long.parseLong(sc.next());

                    Fornecedor fornecedor = gerenciaFornecedores.buscaFornecedorPorCod(codFornecedor);

                    Tecnologia tecnologia = new Tecnologia(id, modelo, descricao, valorBase, peso, temperatura, fornecedor);

                    if(fornecedor == null) {
                        contErrosDadoInvalido++;
                    } else if (!gerenciaTecnologias.addTecnologia(tecnologia)) {
                        contErrosObjRepetido++;
                    } else {
                        contCadastroSucesso++;
                    }

                } catch (NoSuchElementException e) {
                    contErrosFormato++;
                }
            }

        } catch (IOException e) {
            throw new IOException("Impossivel realizar leitura. Arquivo de tecnologias não encontrado.");
        }
    }


}
