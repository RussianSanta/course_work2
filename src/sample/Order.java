package sample;

import java.io.*;

public class Order {
    private String from;
    private String to;
    private String message;
    private User driver;
    private User passenger;
    private String status;

    public Order(String from, String to, String message, User passenger) {
        this.from = from;
        this.to = to;
        if (message == null) this.message = "";
        else this.message = message;
        this.passenger = passenger;
        this.status = "searching...";
    }

    public Order() {
    }

    public void addToFile(String fileName) throws FileNotFoundException {
        String pathName = "src/sample/" + fileName;
        File orders = new File(pathName);
        PrintStream filePrintStream = new PrintStream(new FileOutputStream(orders, true));
        String driverLogin;
        if (this.getDriver() == null) driverLogin = null;
        else driverLogin = this.getDriver().getLogin();
        filePrintStream.println(this.getFrom() + "#" + this.getTo() + "#" + driverLogin + "#" + this.getPassenger().getLogin() + "#" + this.getStatus() + "#" + this.getMessage());
    }

    public void updateOrderStatus(String status) throws IOException {
        this.driver = Main.activeUser;
        this.status = status;

        File orders = new File("src/sample/activeOrders");
        PrintWriter delete = new PrintWriter(new FileWriter(orders,false),false);
        delete.flush();
        delete.close();

        for (Order order: Main.activeOrdersList) {
            order.addToFile("activeOrders");
        }
    }

    public void closeOrder() throws IOException {
        this.status = "finished";
        Main.activeOrdersList.remove(this);
        Main.archiveOrdersList.add(this);
        File orders = new File("src/sample/activeOrders");
        PrintWriter delete = new PrintWriter(new FileWriter(orders,false),false);
        delete.flush();
        delete.close();

        for (Order order: Main.activeOrdersList) {
            order.addToFile("activeOrders");
        }

        addToFile("orderArchive");
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassengerName() {
        return getPassenger().getName();
    }

    public String getDriverName() {
        return getDriver().getName();
    }

    public String getCarModel() {
        return getDriver().getCarModel();
    }

    public String getNumberPlate() {
        return getDriver().getNumberPlate();
    }
}
