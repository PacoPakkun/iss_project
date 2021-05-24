import Model.Loc;
import Model.Manager;
import Model.Spectacol;
import Repo.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class MainController {

    Stage stage;
    Repository repo = new Repository();
    Manager manager;

    @FXML
    ListView spectacole;
    @FXML
    TableView locuri;
    @FXML
    TextField nume, ora, username;
    @FXML
    PasswordField password;
    @FXML
    Button adauga, modifica, sterge;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        List<Spectacol> s = repo.getSpectacole();

        ObservableList<Spectacol> model = FXCollections.observableArrayList();
        model.setAll(s);
        spectacole.setItems(model);

        locuri.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }

    @FXML
    public void showHandler() {
        Spectacol s = (Spectacol) spectacole.getSelectionModel().getSelectedItem();
        nume.setText(s.getNume());
        ora.setText(s.getOra());

        ObservableList<Loc> model = FXCollections.observableArrayList();
        model.setAll(s.getLocuri());
        locuri.setItems(model);
    }

    @FXML
    public void ticketHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyWindow.fxml"));
            Parent root = loader.load();
            BuyController ctrl = loader.getController();
            ctrl.setStage(stage);
            List<Loc> l = locuri.getSelectionModel().getSelectedItems();
            ctrl.setRepo(repo);
            ctrl.setBilete(l);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teatru Management System: Buy Tickets");
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            System.out.println(e);
            alert.showAndWait();
        }
    }

    @FXML
    public void loginHandler() {
        Manager m = repo.findManager(username.getText(), password.getText());
        if (m != null) {
            this.manager = m;
            spectacole.setPrefHeight(308);
            adauga.setVisible(true);
            modifica.setVisible(true);
            sterge.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Invalid login!");
            alert.showAndWait();
        }
    }

    public void loggedIn(Manager manager) {
        this.manager = manager;
        spectacole.setPrefHeight(308);
        adauga.setVisible(true);
        modifica.setVisible(true);
        sterge.setVisible(true);
    }

    @FXML
    public void deleteHandler() {
        Spectacol s = (Spectacol) spectacole.getSelectionModel().getSelectedItem();
        repo.deleteSpectacol(s.getId());
        spectacole.getItems().remove(s);
    }

    @FXML
    public void addHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWindow.fxml"));
            Parent root = loader.load();
            ManageController ctrl = loader.getController();
            ctrl.setStage(stage);
            ctrl.setRepo(repo);
            ctrl.setManage("add");
            ctrl.setManager(manager);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teatru Management System: Manage Shows");
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            System.out.println(e);
            alert.showAndWait();
        }
    }

    @FXML
    public void modifyHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWindow.fxml"));
            Parent root = loader.load();
            ManageController ctrl = loader.getController();
            ctrl.setStage(stage);
            ctrl.setRepo(repo);
            Spectacol s = (Spectacol) spectacole.getSelectionModel().getSelectedItem();
            ctrl.setManage("upd", s);
            ctrl.setManager(manager);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teatru Management System: Manage Shows");
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            System.out.println(e);
            alert.showAndWait();
        }
    }
}
