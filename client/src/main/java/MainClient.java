import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class MainClient {

    public static void main(String...str){
        try(Socket socket = new Socket("localhost", 8085)){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //передача из этой папки;
            TempFileMessage tfm = new TempFileMessage(Paths.get("client/client_repository/exampleTxt.txt"));
            out.writeObject(tfm);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
