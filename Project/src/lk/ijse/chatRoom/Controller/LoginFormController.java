package lk.ijse.chatRoom.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.chatRoom.Util.ClientMaintainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public TextField nameTextfield;

    public static String clientName;
    public Label lbltime;

    ServerSocket serverSocket;
    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public static String sms;


    public void startBtnOnAction(ActionEvent actionEvent) throws IOException {

        msgSender();


    }

    private void msgSender() throws IOException {

        //text feeld clientName Assign to this variable--->
        clientName = nameTextfield.getText();

        Parent parent = FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.show();

        new Thread(() -> {
            try {
                socket = serverSocket.accept();
                System.out.println("Client connected....!");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                ClientMaintainer clientMaintainer = new ClientMaintainer(socket);
                Thread thread = new Thread(clientMaintainer);
                thread.start();

                while (!socket.isConnected()){
                    sms = dataInputStream.readUTF();
                    System.out.println(sms);
                    dataOutputStream.writeUTF(sms.trim());
                    dataOutputStream.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
    public void showTime(){
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String time = currentTime.format(dateTimeFormatter);
            lbltime.setText(time);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTime();

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server Start....!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
