package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Navegador {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
            String url = "";
            System.out.println("introduce url");
            boolean a =true;
            while (a==true) {
            url = sc.next();
            if (url.equals("salir")){
                a=false;
                break;
            }
            URI uri = URI.create(url);
            HttpClient cliente = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpRequest peticion = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .headers("Content-Type", "text/plain")
                    .setHeader("User-Agent", "Mozilla/5.0")
                    .build();

            HttpResponse<String> respuesta = null;
            try {
                respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(respuesta.statusCode());

            if (respuesta.statusCode() < 300 && respuesta.statusCode() >= 200) {
                System.out.println("lo quieres mostrar por pantalla(1) o guardarlo en un fichero(2)? ");
                String c = sc.next();
                if (c.equals("1")) {
                    System.out.println(respuesta.body());
                } else if (c.equals("2")) {
                    try {
                        HttpResponse<Path> respuestaFile = cliente.send(peticion,
                                HttpResponse.BodyHandlers.ofFile(Paths.get("pruebas.html")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (c.equals("salir")) {

                } else {
                    System.out.println("error: " + respuesta.statusCode());
                }
            } else {
                System.out.println(respuesta.statusCode());
            }
        }
    }
}
