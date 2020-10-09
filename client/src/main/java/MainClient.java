import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class MainClient {

    public static void main(String...str){
        try(Socket socket = new Socket("localhost", 8085)){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Выберите действие, которое Вы хотите сделать! 1-загрузить файл на сервер, 2-скачать файл с сервера");
            String number = reader.readLine();

                //загрузка файла на сервер;
                if (number.equals("1")) {
                    System.out.println("Введите название файла, который нужно загрузить на сервер");
                    String name = reader.readLine();

                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    TempFileMessage tfm = new TempFileMessage(Paths.get("client/client_repository/exampleTxtClient.txt"));
                    if (name.equals(tfm.getFileName())) {
                        out.writeObject(tfm);
                    }
                }

                //скачивание файла с сервера;
                else if(number.equals("2")){
                    System.out.println("Введите название файла, который нужно скачать");
                    String name = reader.readLine();
                    //здесь сбрасывает соединение;
                    try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                        TempFileMessage tfm = (TempFileMessage) in.readObject();
                        byte[] bytes = tfm.getBytes();

                        try (OutputStream out = new BufferedOutputStream(new FileOutputStream("server/server_repository/" + tfm.getFileName()))) {
                            for (byte b : bytes) {
                                out.write(b);
                            }
                            if (name.equals(tfm.getFileName())) {
                                out.write(bytes);
                            }
                        }
                    }
                }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
