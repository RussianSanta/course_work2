package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class User {
    private String login;
    private String password;
    private String name;
    private String carModel;
    private String numberPlate;
    private String typeOfUser;

    public User(String login, String password, String name, String typeOfUser) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.typeOfUser = typeOfUser;
    }

    public User() {
    }

    public void addToFile() throws FileNotFoundException {
        File users = new File("src/sample/users");
        PrintStream filePrintStream = new PrintStream(new FileOutputStream(users, true));
        filePrintStream.println(this.getLogin() + "#" + this.getPassword() + "#" + this.getName() + "#" + this.getTypeOfUser());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
}