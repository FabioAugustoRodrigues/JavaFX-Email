package codigofonte.controller;

import codigofonte.model.emails.EnviarEmail;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * FXML Controller class
 *
 * @author Fábio Augusto Rodrigues
 */
public class EscreverController implements Initializable {

    // Atributos do JAVAFX
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPara;
    @FXML
    private JFXTextField txtTitulo;
    @FXML
    private JFXTextArea txtTexto;
    @FXML
    private JFXRadioButton radioAnexo;
    @FXML
    private Label lblAnexo;
    @FXML
    private Button btnEntrar;
    @FXML
    private JFXPasswordField txtSenha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /*
        Método para inserir o e-mail e senha nos formulários
    */
    public void inserirDados(String email, String senha){
        txtEmail.setText(email);
        txtSenha.setText(senha);
    }
    
    /*
        Quando o usuário clica no RadioButton, automaticamente abrirá a tela para selecionar um arquivo
    */
    @FXML
    void colocarAnexo(ActionEvent event) {
        
        if (radioAnexo.isSelected()){
            // Caso o componente radioAnexo esteja selecionado, ele abre a caixa de arquivos
            // para o usuário selecionar o anexo que deseja enviar por e-mail
            
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Arquivo");

            File arq =  fileChooser.showOpenDialog(stage);

            if (arq != null){
                lblAnexo.setText(arq+"");
            }else{
                radioAnexo.setSelected(false);
            }
        }
    }
    
    // Método para enviar e-mail
    @FXML
    private void enviarEmail(ActionEvent event) {
        
        // Verificando se todos os campos foram preenchidos
        if (txtPara.getText().isEmpty() || txtTitulo.getText().isEmpty() || txtTexto.getText().isEmpty()){
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Preencha todos os campos!");
            alert.setContentText("Um ou mais campos não foram preenchidos");
            alert.showAndWait();
        }else {
            
            boolean result = true;
            try {
                InternetAddress emailAddr = new InternetAddress(txtPara.getText());
                emailAddr.validate();
            } catch (AddressException ex) {
                result = false;
            }
            
            // Verificando se o email destinatário é valido
            if (result){
                
                boolean env = true;
                
                if (radioAnexo.isSelected()){
                    env = EnviarEmail.enviarEmail(txtPara.getText(), txtTitulo.getText(), txtTexto.getText(), txtEmail.getText(),
                        txtSenha.getText(), lblAnexo.getText());
                }else{
                    env = EnviarEmail.enviarEmail(txtPara.getText(), txtTitulo.getText(), txtTexto.getText(), txtEmail.getText(),
                        txtSenha.getText(), null);
                }
                
                // Por fim, enviando o e-mail
                if (env){
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informação");
                    alert.setHeaderText(null);
                    alert.setContentText("Email enviado com sucesso!");

                    alert.show();
                    
                    // Limpando todos os formulários
                    txtEmail.setText("");
                    txtPara.setText("");
                    txtSenha.setText("");
                    txtTitulo.setText("");
                    txtTexto.setText("");
                    lblAnexo.setText("");
                    radioAnexo.setSelected(false);
                    
                }else{
                    Alert alert =  new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERRO");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro no envio de e-mail");
                    alert.showAndWait();
                }     
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
