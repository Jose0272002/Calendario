package tcp.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto =46201;
        try(ServerSocket serverSocket = new ServerSocket(puerto)){
            while (true){
                Socket socket= serverSocket.accept();
                new Gestor(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
