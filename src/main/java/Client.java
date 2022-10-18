import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Shang Zemo on 2022/10/18
 */
public class Client {

    static String serverIP;
    static int port;

    public static void main(String[] args) throws IOException {
        setServerIP();
        System.out.print("enter 'y' to start: ");
        try {
            Socket serverSocket = new Socket(serverIP, port);
            Scanner clientIn = new Scanner(System.in);
            String command = clientIn.nextLine();
            if (command.equals("y")) {
                new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream(), StandardCharsets.UTF_8), true).println("start114514");
            }
            serverSocket.shutdownOutput();
            Scanner serverOut = new Scanner(serverSocket.getInputStream(), StandardCharsets.UTF_8);
            while (serverOut.hasNextLine()) {
                System.out.println(serverOut.nextLine());
            }
        } catch (UnknownHostException | ConnectException e) {
            System.out.println("server is not opening.");
            main(args);
            return;
        }

        while (true) {
            try {
                command();
            } catch (UnknownHostException | ConnectException e) {
                System.out.println("server is not opening.");
                main(args);
                return;
            }
        }
    }

    static void setServerIP() {
        System.out.print("input the ip to connect: ");
        Scanner scanner = new Scanner(System.in);
        serverIP = scanner.nextLine();
        System.out.println("ip set down, and then the port");
        scanner = new Scanner(System.in);
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("illegal port number, 6201 have be used.");
        }
        System.out.println("all is done!");
    }

    static boolean command() throws UnknownHostException, ConnectException, IOException {
        Socket serverSocket = new Socket(serverIP, port);
        Scanner clientIn = new Scanner(System.in);
        String command = clientIn.nextLine();
        new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream(), StandardCharsets.UTF_8), true).println(command);
        serverSocket.shutdownOutput();
        Scanner serverOut = new Scanner(serverSocket.getInputStream(), StandardCharsets.UTF_8);
        while (serverOut.hasNextLine()) {
            System.out.println(serverOut.nextLine());
        }

        return true;
    }
}
