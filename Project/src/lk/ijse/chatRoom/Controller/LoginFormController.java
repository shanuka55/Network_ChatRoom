package lk.ijse.chatRoom.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public TextField nameTextfield;

    public static String clientName;

    public void startBtnOnAction(ActionEvent actionEvent) throws IOException {

        clientName = nameTextfield.getText();

        Parent parent = FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
