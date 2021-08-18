package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DriverWindowController {
    private static Order selectedOrder;
    private ObservableList<Order> availableOrders;

    @FXML
    private TabPane driverTab;
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
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label passengerLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button acceptButton;
    @FXML
    private Button otpButton;
    @FXML
    private TableView<Order> historyTable;
        @FXML
        private TableColumn<Order, String> fromColoumn;
        @FXML
        private TableColumn<Order, String> toColoumn;
        @FXML
        private TableColumn<Order, String> passengerColoumn;
    @FXML
    private TableView<Order> availableTable;
        @FXML
        private TableColumn<Order, String> avFromColoumn;
        @FXML
        private TableColumn<Order, String> avToColoumn;
    @FXML
    private TextArea messageArea;

    public void initialize() {
        otpButton.setDisable(false);
        driverTab.getSelectionModel().select(driveTab);
        activeMainPane.setVisible(false);
        fillAvailable();
        fillHistory();

        for (Order order : Main.activeOrdersList) {
            if (order.getDriver() != null) {
                if (order.getDriver().getLogin().equals(Main.activeUser.getLogin())) {
                    selectedOrder = order;
                    onActiveTab(order);
                    break;
                }
            }
        }
    }

    public void onActiveTab(Order order) {
        fromLabel.setText(order.getFrom());
        toLabel.setText(order.getTo());
        passengerLabel.setText(order.getPassenger().getName());
        statusLabel.setText(order.getStatus());

        if (order.getStatus().equals("driver on the point")) otpButton.setText("Start");
        if (order.getStatus().equals("on the way")) otpButton.setDisable(true);

        unlockOrderTab(false);
    }

    public void unlockOrderTab(boolean bool) {
        acceptButton.setDisable(!bool);

        activeMainPane.setVisible(!bool);
        activeHidePane.setVisible(bool);

        if (!bool) driverTab.getSelectionModel().select(activeTab);
        else {
            driverTab.getSelectionModel().select(driveTab);
            messageArea.setPromptText("Message");
        }
    }

    @FXML
    private void otpClick() throws IOException {
        if (otpButton.getText().equals("On the point")) {
            selectedOrder.updateOrderStatus("driver on the point");
            statusLabel.setText(selectedOrder.getStatus());
            otpButton.setText("Start");
        } else {
            selectedOrder.updateOrderStatus("on the way");
            statusLabel.setText(selectedOrder.getStatus());
            otpButton.setDisable(true);
        }
    }

    @FXML
    private void endClick() throws IOException {
        selectedOrder.closeOrder();
        selectedOrder = null;
        unlockOrderTab(true);
        fillHistory();
    }

    @FXML
    private void acceptClick() throws IOException {
        if (selectedOrder != null) {
            selectedOrder.updateOrderStatus("driver on the way");
            availableOrders.remove(selectedOrder);
            messageArea.setText(null);
            messageArea.setPromptText("Message");
            onActiveTab(selectedOrder);
        }
    }

    private void fillHistory() {
        ObservableList<Order> driverHistory = FXCollections.observableArrayList();
        for (Order order : Main.archiveOrdersList) {
            if (order.getDriver().getLogin().equals(Main.activeUser.getLogin())) driverHistory.add(order);
        }
        fromColoumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        passengerColoumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        historyTable.setItems(driverHistory);
    }

    private void fillAvailable() {
        availableOrders = FXCollections.observableArrayList();
        for (Order order : Main.activeOrdersList) {
            if (order.getStatus().equals("searching...")) availableOrders.add(order);
        }
        avFromColoumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        avToColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        availableTable.setItems(availableOrders);

        availableTable.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> showOrder(newValue)
        );
    }

    private void showOrder(Order order) {
        if (order != null) {
            messageArea.setText(order.getMessage());
            selectedOrder = order;
        }
    }
}