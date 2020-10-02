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
                for(byte b:bytes){
                    System.out.print((char)b);
                }

                  //передача файла;
                int x = in.read();
                short fileNameSize = in.readShort();
                byte[] fileNameBytes = new byte[fileNameSize];
                in.read(fileNameBytes);
                String fileName = new String(fileNameBytes);
                long fileSize = in.readLong();
                try(OutputStream out = new BufferedOutputStream(new FileOutputStream("server/server_repository"+fileName))){
                    for(int i = 0; i < fileSize; i++){
                        out.write(in.read());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
