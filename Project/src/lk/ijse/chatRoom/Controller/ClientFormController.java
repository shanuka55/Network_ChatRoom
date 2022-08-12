package lk.ijse.chatRoom.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public Label clientName;
    public TextArea textArea;
    public TextField textFieldSent;

    String Name;

    public void sentBtnOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Name = LoginFormController.clientName;
        clientName.setText(Name);
        System.out.println(Name);
    }
}
