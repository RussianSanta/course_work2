package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class RegistrationController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;

    @FXML
    private void okClick() throws FileNotFoundException {
        boolean isTrue = true;
        if (loginField.getText().equals("")){
            loginField.setStyle("-fx-border-color: black");
            isTrue = false;
        } else {
            for (User user: Main.usersList) {
                if (user.getLogin().equals(loginField.getText())){
                    loginField.setText("");
                    loginField.setStyle("-fx-border-color: black");
                    isTrue = false;
                }
            }
        }
        if (passwordField.getText().equals("")){
            passwordField.setStyle("-fx-border-color: black");
            isTrue = false;
        }
        if (nameField.getText().equals("")){
            nameField.setStyle("-fx-border-color: black");
            isTrue = false;
        }
        if (isTrue) {
            User user = new User(loginField.getText(),passwordField.getText(),nameField.getText(),"0");
            Main.usersList.add(user);
            user.addToFile();

            Stage stage1 = (Stage)loginField.getScene().getWindow();
            stage1.close();
        }
    }
}
