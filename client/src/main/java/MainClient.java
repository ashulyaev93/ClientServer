import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class MainClient {

    public static void main(String...str){
        try(Socket socket = new Socket("localhost", 8085)){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            TempFileMessage tfm = new TempFileMessage(Paths.get("client/client_repository/exampleTxt.txt"));
            out.writeObject(tfm);

            //передача файла
            String fileName = "exampleTxt.txt";
            short fileNameLength = (short) fileName.length();
            out.writeShort(fileNameLength);
            out.write(fileName.getBytes());
            out.writeLong(new File("client/client_repository/"+fileName).length());
            byte[] buf = new byte[1024];
            try(InputStream in = new FileInputStream("client/client_repository/"+fileName)){
                int n;
                while ((n = in.read(buf)) != -1){
                    out.write(buf, 0, n);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
