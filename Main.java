import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("*********************************************");
        System.out.println("Seja bem-vindo/a ao Conversor de Moeda =]");
        System.out.println();
        System.out.println("1) Dólar =>> Peso argentino");
        System.out.println("2) Peso argentino =>> Dólar");
        System.out.println("3) Dólar =>> Real brasileiro");
        System.out.println("4) Real brasileiro =>> Dólar");
        System.out.println("5) Dólar =>> Peso colombiano");
        System.out.println("6) Peso colombiano =>> Dólar");
        System.out.println("7) Sair");
        System.out.print("Escolha uma opção válida: ");
        int opcao = scanner.nextInt();
        System.out.println("********************************************");


        String moedaOrigem = "";
        String moedaDestino = "";


        if (opcao == 1) {
            moedaOrigem = "USD";
            moedaDestino = "ARS";
        } else if (opcao == 2) {
            moedaOrigem = "ARS";
            moedaDestino = "USD";
        } else if (opcao == 3) {
            moedaOrigem = "USD";
            moedaDestino = "BRL";
        } else if (opcao == 4) {
            moedaOrigem = "BRL";
            moedaDestino = "USD";
        } else if (opcao == 5) {
            moedaOrigem = "USD";
            moedaDestino = "COP";
        } else if (opcao == 6) {
            moedaOrigem = "COP";
            moedaDestino = "USD";
        } else if (opcao == 7) {
            System.out.println("Saindo do programa. Até logo!");
            scanner.close();
            return;
        } else {
            System.out.println("Opção inválida. Finalizando...");
            scanner.close();
            return;
        }



        System.out.print("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        // Chave da API espero que funcione ><
        String chaveAPI = "804dac9c857fa98514c15be2";
        String urlApi = "https://v6.exchangerate-api.com/v6/" + chaveAPI + "/pair/" + moedaOrigem + "/" + moedaDestino;

        try {
            // url pra conectar a api
            URL url = new URL(urlApi);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.connect();

            // pra ler a resposta da api
            InputStreamReader leitor = new InputStreamReader(conexao.getInputStream());
            JsonObject json = JsonParser.parseReader(leitor).getAsJsonObject();

            // verificação
            if (json.get("result").getAsString().equals("success")) {
                double taxa = json.get("conversion_rate").getAsDouble();
                double convertido = valor * taxa;

                // resultado legalzinho
                System.out.println("\n====================================");
                System.out.printf("%.2f %s = %.2f %s\n", valor, moedaOrigem, convertido, moedaDestino);
                System.out.println("Taxa usada: " + taxa);
                System.out.println("====================================");
            } else {
                System.out.println("Erro ao obter a taxa de câmbio.");
            }

        } catch (Exception e) {
            System.out.println("Erro durante a conexão: " + e.getMessage());
        }

        scanner.close();
    }
}
