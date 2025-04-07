package tcp.servidor;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Gestor extends Thread{
    private LocalDateTime ahora= LocalDateTime.now();
    private DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Semaphore semaphore =new Semaphore(3);
    private Socket socket;
    public Gestor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            semaphore.acquire();
            //Envía instrucciones ("Elige 1 para fecha, 2 para hora, 3 para día de la semana, 0 para salir).
            out.println("Elige 1 para fecha, 2 para hora, 3 para día de la semana, 0 para salir");
            //Recibe el código del cliente
            String codigo = in.readLine();
            while (!codigo.equals("0")) {
                switch (codigo) {
                    case "1":
                        ahora= LocalDateTime.now();
                        out.println("Hoy es " +ahora.getDayOfMonth() + " de "+ahora.getMonth() + " de "+ahora.getYear());
                        break;
                    case "2":
                        ahora= LocalDateTime.now();
                        out.println("son las "+ ahora.format(f));
                        break;
                    case "3":
                        ahora= LocalDateTime.now();
                        out.println("hoy es "+ahora.getDayOfWeek());
                        break;
                    case "0":
                        break;
                }
                out.println("otro codigo");
                //Recibe el código del cliente
                codigo = in.readLine();
            }
            semaphore.release();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
