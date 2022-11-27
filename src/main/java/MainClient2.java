import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MainClient2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя пользователя");
        String nam = scanner.nextLine();

        System.out.println("Начинаем чат, для выхода наберите 'exit'");
        String host = "127.0.0.1";// хост моего компьютера
        int port = Integer.parseInt(portRead());//  получаем номер потра из настроек

        while (true) {
            try (Socket clientSocket = new Socket(host, port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.print(nam + ": ");
                String text = scanner.nextLine();
                if (text.equals("exit")) {
                    out.println(nam + "- " + LocalDateTime.now() + " : " + " Пользователь покинул чат");
                    clientTxtWrite(in.readLine()); // тот текст который пришел от сервера, запись в файл
                    return;
                }
                out.println(nam + "- " + LocalDateTime.now() + " : " + text); // отправляет данные

                clientTxtWrite(in.readLine()); // тот текст который пришел от сервера, запись в файл
            } catch (IOException ex) {
                ex.printStackTrace();
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("file.log", true))) {
            bw.write(txt);
            bw.write("\n");
            bw.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

