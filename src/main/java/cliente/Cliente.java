package cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket("localhost",46201);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String mensaje1 =in.readLine();
            System.out.println(mensaje1);
            String codigo = sc.next();
            out.println(codigo);

            while (!codigo.equals("0")){
                String mensaje2 =in.readLine();
                System.out.println(mensaje2);
                String mensaje3 =in.readLine();
                System.out.println(mensaje3);
                codigo = sc.next();
                out.println(codigo);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
