package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class UserWindowController {
    @FXML
    private TabPane userTab;
    @FXML
    private Tab activeTab;
    @FXML
    private Tab driveTab;
    @FXML
    private Tab historyTab;
    @FXML
    private Pane activeMainPane;
    @FXML
    private Pane activeHidePane;
    @FXML
    private TextField fromField;
    @FXML
    private TextField toField;
    @FXML
    private TextArea messageArea;
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label driverLabel;
    @FXML
    private Label carLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TableView<Order> historyTable;
        @FXML
        private TableColumn<Order,String> fromColoumn;
        @FXML
        private TableColumn<Order,String> toColoumn;
        @FXML
        private TableColumn<Order,String> driverColoumn;
        @FXML
        private TableColumn<Order,String> carColoumn;
        @FXML
        private TableColumn<Order,String> numberColoumn;

    public void initialize(){
        userTab.getSelectionModel().select(driveTab);
        activeMainPane.setVisible(false);
        fillHistory();

        for (Order order: Main.activeOrdersList) {
            if (order.getPassenger().getLogin().equals(Main.activeUser.getLogin())){
                onActiveTab(order);
                break;
            }
        }
    }

    @FXML
    private void callClick() throws FileNotFoundException {
        boolean isTrue = true;
        if (fromField.getText().equals("")){
            fromField.setStyle("-fx-border-color: red");
            isTrue = false;
        }
        if (toField.getText().equals("")){
            toField.setStyle("-fx-border-color: red");
            isTrue = false;
        }
        if (isTrue) {
            Order order = newOrder(fromField.getText(),toField.getText(),messageArea.getText());
            fromField.setStyle("-fx-border-color: black");
            toField.setStyle("-fx-border-color: black");
            onActiveTab(order);
        }
    }

    public void onActiveTab(Order order){
        fromLabel.setText(order.getFrom());
        toLabel.setText(order.getTo());
        if (order.getDriver() != null) {
            driverLabel.setText(order.getDriver().getName());
            carLabel.setText(order.getDriver().getCarModel());
            numberLabel.setText(order.getDriver().getNumberPlate());
            statusLabel.setText(order.getStatus());
        }
        else {
            driverLabel.setText("waiting...");
            carLabel.setText("waiting...");
            numberLabel.setText("waiting...");
            statusLabel.setText("searching...");
        }

        unlockOrderTab(false);
    }

    public void unlockOrderTab(boolean bool){
        fromField.setText("");
        toField.setText("");
        messageArea.setText("");
        fromField.setEditable(bool);
        toField.setEditable(bool);
        messageArea.setEditable(bool);

        activeMainPane.setVisible(!bool);
        activeHidePane.setVisible(bool);

        if (!bool) userTab.getSelectionModel().select(activeTab);
        else userTab.getSelectionModel().select(driveTab);
    }

    private Order newOrder(String from, String to, String message) throws FileNotFoundException {
        Order order = new Order(from,to,message,Main.activeUser);
        order.addToFile("activeOrders");
        Main.activeOrdersList.add(order);
        return order;
    }

    private void fillHistory(){
        ObservableList<Order> userHistory = FXCollections.observableArrayList();
        for (Order order: Main.archiveOrdersList) {
            if (order.getPassenger().getLogin().equals(Main.activeUser.getLogin())) userHistory.add(order);
        }
        fromColoumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        driverColoumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        carColoumn.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        numberColoumn.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
        historyTable.setItems(userHistory);
    }
}
