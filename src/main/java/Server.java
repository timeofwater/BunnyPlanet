import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Shang Zemo on 2022/10/18
 */
public class Server {
    static int port;

    static ServerSocket serverSocket;
    static Socket clientSocket;

    static Scanner serverIn;
    static Scanner clientIn;
    static PrintStream serverOut;
    static PrintWriter clientOut;

    static BattleGround battleGround;

    static boolean isClientReady;
    static String clientIP;

    public static void main(String[] args) {
        try {
            System.out.println("input the port (6201 is suggested).");
            Scanner portScanner = new Scanner(System.in);
            try {
                port = Integer.parseInt(portScanner.nextLine());
            } catch (NumberFormatException e) {
                port = 6201;
                System.out.println("illegal port number, 6201 have be used.");
            }

            init();
            showIP();
            startListen();

            while (!isClientReady) {
                showIP();
                serverOut.println("waiting for client...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            serverOut.println("******* game started *******");
            while (!battleGround.isCleared) {
                serverIn = new Scanner(System.in);
                System.out.println(analyzeCommands(-1, serverIn.nextLine()));
            }
            serverOut.println("******* game over *******");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void init() throws IOException {
        serverSocket = new ServerSocket(port);

        serverOut = System.out;

        battleGround = new BattleGround();
    }

    static void showIP() throws UnknownHostException {
        serverOut.println("your ip is: " + InetAddress.getLocalHost().getHostAddress());
    }

    static void startListen() {
        Thread listener = new Thread(() -> {
            try {
                while (true) {
                    clientSocket = serverSocket.accept();
                    clientIP = clientSocket.getLocalAddress().toString();
                    clientIn = new Scanner(clientSocket.getInputStream(), StandardCharsets.UTF_8.name());
                    clientOut = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
                    clientOut.println(analyzeCommands(1, clientIn.nextLine()));
                    clientSocket.shutdownOutput();
                    serverOut.println("client's command takes effect");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "listener");
        listener.start();
    }

    static String analyzeCommands(int player, String command) {
        command = command.toLowerCase(Locale.ROOT);
        String[] fragments = command.split("[ ]+");

        if (fragments.length == 1) {
            int index;
            try {
                index = Integer.parseInt(fragments[0]);
            } catch (NumberFormatException e) {
                if (fragments[0].equals("start114514")) {
                    isClientReady = true;
                    return "******* game start *******";
                } else {
                    return " illegal command format(" + command + ")\n read README.md to find the command format.";
                }
            }
            return battleGround.getPlanetsInfo(index);
        }

        try {
            int index;
            try {
                index = Integer.parseInt(fragments[0]);
            } catch (NumberFormatException e) {
                return " illegal command format(" + command + ")\n read README.md to find the command format.";
            }

            if (!fragments[1].equals("/")) {
                return " illegal command format(" + command + ")\n read README.md to find the command format.";
            }

            if (fragments[2].equals("b")) {
                int num;
                try {
                    num = Integer.parseInt(fragments[3]);
                } catch (NumberFormatException e) {
                    return " illegal command format(" + command + ")\n read README.md to find the command format.";
                }

                int form;
                try {
                    form = Integer.parseInt(fragments[4]);
                } catch (NumberFormatException e) {
                    if (fragments[4].equals("producer") || fragments[4].equals("p")) {
                        form = 1;
                    } else if (fragments[4].equals("soldier") || fragments[4].equals("s")) {
                        form = 2;
                    } else {
                        return " illegal command format(" + command + ")\n read README.md to find the command format.";
                    }
                }

                return battleGround.buildCommand(player, index, num, form);
            } else if (fragments[2].equals("t")) {
                int num;
                try {
                    num = Integer.parseInt(fragments[3]);
                } catch (NumberFormatException e) {
                    return " illegal command format(" + command + ")\n read README.md to find the command format.";
                }

                if (!fragments[4].equals("->")) {
                    return " illegal command format(" + command + ")\n read README.md to find the command format.";
                }

                int to;
                try {
                    to = Integer.parseInt(fragments[5]);
                } catch (NumberFormatException e) {
                    return " illegal command format(" + command + ")\n read README.md to find the command format.";
                }

                return battleGround.transferCommand(player, index, to, num);
            } else {
                return " illegal command format(" + command + ")\n read README.md to find the command format.";
            }
        } catch (IndexOutOfBoundsException e) {
            return " illegal command format(" + command + ")\n read README.md to find the command format.";
        }
    }
}
