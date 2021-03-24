import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BuyController {
    @FXML
    ListView bilete;
    @FXML
    TextField nume, tel;

    @FXML
    public void initialize(){
        bilete.getItems().add("bilet #356");
        bilete.getItems().add("bilet #357");
        bilete.getItems().add("bilet #358");
    }


    @FXML
    public void buyHandler(){
        if(nume.getText()!="" && tel.getText()!=""){
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setHeaderText("OK");
            errorAlert.setContentText("OK");
            errorAlert.showAndWait();
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("not OK");
            errorAlert.setContentText("not OK");
            errorAlert.showAndWait();
        }
    }
}
