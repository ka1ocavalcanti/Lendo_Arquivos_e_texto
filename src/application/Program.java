package application; // Pacote onde está localizada a classe Program

import entidades.Produto; // Importação da classe Produto

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program { // Definição da classe Program
    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.US); // Define a localização padrão para números com ponto decimal
        Scanner sc = new Scanner(System.in); // Inicializa um Scanner para entrada de dados

        List<Produto> lista = new ArrayList<>(); // Cria uma lista de objetos da classe Produto

        System.out.println("Insira um caminho de arquivo: ");
        String arquivoFonte = sc.nextLine(); // Solicita ao usuário o caminho do arquivo de origem

        File arquivo = new File(arquivoFonte); // Cria um objeto File com o caminho fornecido
        String pastaDeOrigem = arquivo.getParent(); // Obtém o diretório pai do arquivo

        boolean sucesso = new File(arquivoFonte + "/out").mkdir(); // Tenta criar uma pasta chamada "out"

        String arquivoDeDestino = arquivoFonte + "/out/resumo.csv"; // Define o caminho do arquivo de destino

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoFonte))) {
            String itemCsv = br.readLine(); // Lê a primeira linha do arquivo

            while (itemCsv != null) { // Enquanto não atingir o final do arquivo
                String[] pastas = itemCsv.split(","); // Divide a linha em campos separados por vírgula
                String nome = pastas[0]; // Obtém o nome do produto
                double preco = Double.parseDouble(pastas[1]); // Obtém o preço do produto
                int quantidade = Integer.parseInt(pastas[2]); // Obtém a quantidade do produto

                lista.add(new Produto(nome, preco, quantidade)); // Cria um objeto Produto e adiciona à lista

                itemCsv = br.readLine(); // Lê a próxima linha do arquivo
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDeDestino))) {
                for (Produto item : lista) {
                    // Escreve o nome do produto e o total formatado no arquivo de destino
                    bw.write(item.getNome() + "," + String.format("%.2f", item.total()));
                    bw.newLine(); // Escreve uma nova linha
                }
                System.out.println(arquivoDeDestino + " CREATED"); // Exibe uma mensagem de sucesso
            } catch (IOException e) {
                System.out.println("Error de escrita no nome: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error de escrita na pasta" + e.getMessage());
        }

        sc.close(); // Fecha o Scanner
    }
}
