import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]) {
		ServerSocket server = null;
		int port = 3000;
		
		try {
			server = new ServerSocket(port);
			server.setReuseAddress(true);
			
			while (true) {
				Socket client = server.accept();
				System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
				ClientHandler clientSock = new ClientHandler(client);
				
				new Thread(clientSock).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class ClientHandler implements Runnable {
		private final Socket clientSocket;
		
		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}
		
		@Override
		public void run() {
			BufferedReader in = null;
			PrintWriter out = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				
				String line;
				while ((line = in.readLine()) != null) {
					System.out.printf("Message from the client: %s\n", line);
					out.println(line.hashCode());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
