package lk.ijse.chatRoom.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public Label clientName;
    public TextArea textArea;
    public TextField textFieldSent;

    String Name;

    final int port = 5000;
    Socket socket;

    String sms;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public void sentBtnOnAction(ActionEvent actionEvent) throws IOException {

        sendMsg();
    }

    private void sendMsg() throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(textFieldSent.getText().trim());
        dataOutputStream.flush();
        textFieldSent.clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Name = LoginFormController.clientName;
        clientName.setText(Name);
        System.out.println(Name);

        new Thread(() -> {
            try {
                socket = new Socket("localhost",port);
                System.out.println("Client  started....!");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (socket.isConnected()){
                    sms = dataInputStream.readUTF();
                    textArea.appendText("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
