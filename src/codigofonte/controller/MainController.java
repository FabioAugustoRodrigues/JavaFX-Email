package codigofonte.controller;

import codigofonte.EmailZ;
import codigofonte.model.emails.ControlarEmails;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Fábio Augusto Rodrigues
 */
public class MainController implements Initializable {
    
    private Label label;
    @FXML
    private Label lblForgot;
    @FXML
    private Button btnEntrar;
    @FXML
    private Label lblForgot1;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXPasswordField txtSenha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    // Método para entrar
    @FXML
    private void entrarUsuario(ActionEvent event) {
        
        if (txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()){
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Preencha todos os campos!");
            alert.setContentText("Um ou mais campos não foram preenchidos");
            alert.showAndWait();
        }else{
            
            ControlarEmails ctEmail = new ControlarEmails(txtEmail.getText(), txtSenha.getText());
            
            if (ctEmail.validarEmail()){
     
                EmailZ.changeScreen("Usuario", ctEmail);
                        
                txtEmail.setText("");
                txtSenha.setText("");
                        
            }else{
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRO");
                alert.setHeaderText("E-mail invalída!");
                alert.setContentText("O E-mail inserido não existe");
                alert.showAndWait();
            }
            
        }
        
    }
    
}
