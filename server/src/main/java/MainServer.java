import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) throws Exception {
        System.out.println("Start server");
        int port = Integer.parseInt(portRead());//  получаем номер потра из настроек

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> {
                        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // канал записи в сокет
                             BufferedReader in = new BufferedReader(  // канал чтения из сокета
                                     new InputStreamReader(clientSocket.getInputStream()))) {
                            String name= in.readLine();// тот текст который передал клиент
                            clientTxtWrite(name); // тот текст который передал клиент запись в файл
                            System.out.println(name);
                            out.println(name);// возврвщает этот текст клиенту
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close(); // закрываем сокет клиента
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static String portRead() {
        StringBuilder port = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("settings.txt"))) {

            String s;
            while ((s = br.readLine()) != null) {
                port.append(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return port.toString();
    }

    public static void clientTxtWrite(String txt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("fileServer.log", true))) {
            bw.write(txt);
            bw.write("\n");
            bw.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
