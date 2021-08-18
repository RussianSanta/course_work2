package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class EnterController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public void initialize(){
        passwordField.setFocusTraversable(false);
    }

    @FXML
    private void enterClick() throws IOException {
        Main.activeUser = checkUser(loginField.getText(),passwordField.getText());
        if (Main.activeUser != null){
            if (Main.activeUser.getTypeOfUser().equals("0")){
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("userWindow.fxml"));
                stage.setTitle("Taxi");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(this.getClass().getResource("taxi.png").toString()));
                stage.setResizable(false);
                stage.show();

                Stage stage1 = (Stage)loginField.getScene().getWindow();
                stage1.close();
            } else {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("driverWindow.fxml"));
                stage.setTitle("Taxi");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(this.getClass().getResource("taxi.png").toString()));
                stage.setResizable(false);
                stage.show();

                Stage stage1 = (Stage)loginField.getScene().getWindow();
                stage1.close();
            }
        }
    }

    @FXML
    private void registrationClick() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
        stage.setTitle("Registration");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(this.getClass().getResource("taxi.png").toString()));
        stage.setResizable(false);
        stage.show();
    }

    private User checkUser(String login, String password){
        for (User user: Main.usersList) {
            if (user.getLogin().equals(login)){
                if (user.getPassword().equals(password)){
                    return user;
                }
            }
        }
        loginField.setStyle("-fx-border-color: red");
        passwordField.setStyle("-fx-border-color: red");
        return null;
    }
}
