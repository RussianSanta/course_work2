package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public static User activeUser;
    public static ObservableList<User> usersList = FXCollections.observableArrayList();
    public static ObservableList<Order> activeOrdersList = FXCollections.observableArrayList();
    public static ObservableList<Order> archiveOrdersList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("enter.fxml"));
        primaryStage.setTitle("Taxi");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(this.getClass().getResource("taxi.png").toString()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        loadUsersList();
        loadActiveOrdersList();
        loadArchiveOrders();

        launch(args);
    }

    private static void loadUsersList() throws IOException {
        File usersFile = new File("src/sample/users");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(usersFile)));
        String line;
        while( (line = br.readLine())!= null ){
            User user = new User();
            String [] tokens = line.split("#");
            user.setLogin(tokens[0]);
            user.setPassword(tokens[1]);
            user.setName(tokens[2]);
            user.setTypeOfUser(tokens[3]);
            if (user.getTypeOfUser().equals("1")){
                user.setCarModel(tokens[4]);
                user.setNumberPlate(tokens[5]);
            }
            usersList.add(user);
        }
    }
    private static void loadActiveOrdersList() throws IOException {
        File activeOrdersFile = new File("src/sample/activeOrders");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(activeOrdersFile)));
        String line;
        while( (line = br.readLine())!= null ){
            String [] tokens = line.split("#");
            Order order = new Order();
            order.setFrom(tokens[0]);
            order.setTo(tokens[1]);
            for (User user:usersList) {
                if (user.getLogin().equals(tokens[2])) order.setDriver(user);
                if (user.getLogin().equals(tokens[3])) order.setPassenger(user);
            }
            order.setStatus(tokens[4]);
            if (tokens.length > 5) order.setMessage(tokens[5]);
            else order.setMessage("Message");

            activeOrdersList.add(order);
        }
    }

    private static void loadArchiveOrders() throws IOException {
        File archiveOrdersFile = new File("src/sample/orderArchive");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archiveOrdersFile)));
        String line;
        while( (line = br.readLine())!= null ){
            String [] tokens = line.split("#");
            Order order = new Order();
            order.setFrom(tokens[0]);
            order.setTo(tokens[1]);
            for (User user:usersList) {
                if (user.getLogin().equals(tokens[2])) order.setDriver(user);
                if (user.getLogin().equals(tokens[3])) order.setPassenger(user);
            }
            order.setStatus(tokens[4]);
            order.setMessage(tokens[5]);

            archiveOrdersList.add(order);
        }
    }
}
