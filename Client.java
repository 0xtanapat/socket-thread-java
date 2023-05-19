import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		String host = "127.0.0.1";
		int port = 3000;
		
		try (Socket socket = new Socket(host, port)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			String line = null;
			while (!"exit".equalsIgnoreCase(line)) {
				line = scanner.nextLine();
				out.println(line);
				out.flush();
				System.out.println("Server response: " + in.readLine());
			}
			
			scanner.close();
			System.out.println("Closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
