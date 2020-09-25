import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {//сервер
    public static void main(String...str){
        try(ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try(Socket socket = sc.accept(); ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
                TempFileMessage tfm = (TempFileMessage) in.readObject();
                byte[] bytes = tfm.getBytes();
                for(byte b:bytes){
                    System.out.print((char)b);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
