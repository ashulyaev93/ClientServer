import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;

public class MainClient {

    public static void main(String...str){
        try(Socket socket = new Socket("localhost", 8085)){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            TempFileMessage tfm = new TempFileMessage(Paths.get("client/exampleTxt.txt"));
            out.writeObject(tfm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
