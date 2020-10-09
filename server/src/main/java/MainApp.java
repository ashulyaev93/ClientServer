import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class MainApp {
    public static void main(String...str){
        try(ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Выберите действие, которое Вы хотите сделать! 1-загрузить файл на сервер, 2-скачать файл с сервера");
            String number = reader.readLine();

            Socket socket = sc.accept();
            if(number.equals("1")) {
                //загрузка файла на сервер;
                try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                    TempFileMessage tfm = (TempFileMessage) in.readObject();
                    byte[] bytes = tfm.getBytes();

                    try (OutputStream out = new BufferedOutputStream(new FileOutputStream("server/server_repository/" + tfm.getFileName()))) {
                        for (byte b : bytes) {
                            out.write(b);
                        }
                    }
                }
            }

            else if(number.equals("2")) {
                //скачивание файла с сервера;
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                TempFileMessage tfm = new TempFileMessage(Paths.get("client/client_repository/exampleTxtClient.txt"));
                out.writeObject(tfm);
            }
                
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

//    //для авторизации;
//    public boolean isNickBusy(String newNick) {
//            for (ClientHandler o: clients) {
//                if (o.getNick().equals(nick)) {
//                    return true;
//                }
//            }
//            return false;
//    }
//
//    public void subscribe(ClientHandler client) {
//        clients.add(client);
//        broadcastClientList();
//    }
//
//    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
//        for (ClientHandler o: clients) {
//            if (o.getNick().equals(nickTo)) {
//                o.sendMsg("from " + from.getNick() + ": " + msg);
//                from.sendMsg("to " + nickTo + ": " + msg);
//                return;
//            }
//        }
//        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате!");
//    }
//
//    public void broadcastMsg(ClientHandler from, String msg) {
//        for (ClientHandler o : clients) {
//            if(!o.checkBlackList(from.getNick(), o.getNick())) {
//                o.sendMsg(msg);
//            }
//        }
//    }
//
//    public void unsubscribe(ClientHandler client) {
//        clients.remove(client);
//        broadcastClientList();
//    }
}
