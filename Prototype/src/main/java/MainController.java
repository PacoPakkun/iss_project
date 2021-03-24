import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainController {
    public class Model{
        private String nr;
        private String pozitie;
        private String pret;

        public Model(String nr, String pozitie, String pret) {
            this.nr = nr;
            this.pozitie = pozitie;
            this.pret = pret;
        }

        public String getNr() {
            return nr;
        }

        public void setNr(String nr) {
            this.nr = nr;
        }

        public String getPozitie() {
            return pozitie;
        }

        public void setPozitie(String pozitie) {
            this.pozitie = pozitie;
        }

        public String getPret() {
            return pret;
        }

        public void setPret(String pret) {
            this.pret = pret;
        }
    }

    Stage stage;

    @FXML
    ListView spectacole;
    @FXML
    TableView locuri;
    @FXML
    TextField nume, ora;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        spectacole.getItems().add("Spectacol1");
        spectacole.getItems().add("Spectacol2");
        spectacole.getItems().add("Spectacol3");
        spectacole.getItems().add("Spectacol4");
        spectacole.getItems().add("Spectacol5");
    }

    @FXML
    public void showHandler(){
        nume.setText(spectacole.getSelectionModel().getSelectedItems().toString());
        ora.setText("ora 14:00");
        List<Model> list=new ArrayList<>();
        list.add(new Model("1","randul 1","30"));
        list.add(new Model("2","randul 4","30"));
        list.add(new Model("3","loja 3","50"));
        list.add(new Model("4","loja 10","50"));
        list.add(new Model("5","randul 7","25"));
        list.add(new Model("6","loja vip","100"));
        Collections.shuffle(list);
        locuri.getItems().clear();
        locuri.getItems().addAll(list);

    }

    @FXML
    public void ticketHandler(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyWindow.fxml"));
            Parent root = loader.load();
            BuyController ctrl = loader.getController();
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
}
