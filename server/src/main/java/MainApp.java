import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {
    public static void main(String...str){
        try(ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try(Socket socket = sc.accept(); ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
                TempFileMessage tfm = (TempFileMessage) in.readObject();
                byte[] bytes = tfm.getBytes();

                //передача файла
                try(OutputStream out = new BufferedOutputStream(new FileOutputStream("server/server_repository/"+tfm.getFileName()))){
                    for(byte b: bytes){
                        out.write(b);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
