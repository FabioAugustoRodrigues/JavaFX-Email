package codigofonte.controller;

import codigofonte.EmailZ;
import codigofonte.model.converter.ConverterData;
import codigofonte.model.emails.ControlarEmails;
import codigofonte.model.emails.LendoEmails;
import codigofonte.model.emails.ListaEmails;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * FXML Controller class
 *
 * @author Fábio Augusto Rodrigues
 */
public class UsuarioController implements Initializable{
    
    @FXML
    private ImageView imgVoltar;
    
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private AnchorPane panelLista;
    
    @FXML
    private AnchorPane panelCampo;
    
    @FXML
    private AnchorPane paneArquivo;
    
    @FXML
    private Label lblMensagem;
    
    @FXML
    private JFXPasswordField txtSenha;
    
    @FXML
    private JFXListView<String> idListView;
    
    @FXML
    private Label idTitulo;
    
    @FXML
    private Label lblDeQuem;

    @FXML
    private Label lblTexto;
    
    @FXML
    private Label lblDataDeEnvio;
    
    @FXML
    private AnchorPane paneCarregar;
    
    @FXML
    private JFXProgressBar idProgressBar;

    @FXML
    private Button btnCopiar;
    
    @FXML
    private TextField procurarPorEmail;
   
    // Atributos da classe
    private ObservableList<String> obsLista;
    private List<ListaEmails> lista_emails;
    private ControlarEmails emailUser;
    private boolean conteudoOriginal;
    private ListaEmails emailAberto;
    
    // == Métodos ===================================================================
    
    /*
        Quando o usuário clicar em alguma célula na lista (ou na própria lista em si) este método é chamado
        e inseri num campo, as informações da célula correspondente
    */
    @FXML
    void mouseClicadoNaLista(MouseEvent event) {

        if (this.lista_emails != null && idListView.getSelectionModel().getSelectedIndex() != -1){
            
            panelLista.setVisible(false); 
            panelCampo.setVisible(true); 

            idTitulo.setText(idListView.getSelectionModel().getSelectedItem()); 

            idTitulo.setMaxSize(330, 33); 
           
            int id = 0;

            for (int i = 0; i < lista_emails.size(); i++){
                if (idTitulo.getText().equals(lista_emails.get(i).getTitulo())){
                    id = i;
                    break;
                }
            }

            emailAberto = new ListaEmails(lista_emails.get(id).getTitulo(), lista_emails.get(id).getDeQuem(),
                                        lista_emails.get(id).getTexto(), lista_emails.get(id).getData());
            
            lblDeQuem.setText(emailAberto.getDeQuem());
            lblDeQuem.setMaxSize(330, 14);
            
            lblTexto.setText(emailAberto.getTexto());
            lblTexto.setMaxSize(330, 166);
                  
            ConverterData cvData = new ConverterData(emailAberto.getData());
            
            lblDataDeEnvio.setText("Data de envio: " + cvData.getFormatacaoBR());
            
            
        }
    }
    
    /*
        Método para atualizar a lista de e-mails
        O método é chamado quando o usuário clica no botão de atualizar, que fica
        no canto superior direito
    */
    @FXML
    void btnCarregarLista(ActionEvent event) {
        
        if (this.lista_emails != null){
            
            paneCarregar.setVisible(true);
            panelLista.setVisible(false);
            panelCampo.setVisible(false);

            idListView.getItems().remove(0, this.lista_emails.size());

            // Carregando a lista
            carregarLista();
        }
    }
    
    /*
        Quando o usuário está vendo o conteúdo de um certo e-mail, e decide voltar para a lista.
        Nessa parte apenas deixamos o painel de lista vísivel e o painel de coteúdo invisivel
    */
    @FXML
    void voltarLista(ActionEvent event) {
        panelCampo.setVisible(false);
        panelLista.setVisible(true);
        emailAberto = null;
    }
    
    /*
        Método para o usuário iniciar uma nova conversa por e-mail;
        Esse método chama uma nova tela JavaFX, com os componentes necessários para enviar um e-mail para alguém
    */
    @FXML
    void novaConversa(ActionEvent event) {
        if (this.lista_emails != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codigofonte/view/fxml/TelaEscrever.fxml"));
                Parent form = loader.load();

                EscreverController escreverController = loader.<EscreverController>getController();
                escreverController.inserirDados(emailUser.getEmail(), emailUser.getSenha());
                
                Stage stage = new Stage();
                stage.setScene(new Scene(form));
                stage.setTitle("Formulário");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/codigofonte/view/imagens/compartilhar.png")));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            }catch(Exception e){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRO");
                alert.setHeaderText(null);
                alert.setContentText("Algo inesperado aconteceu!");
                alert.showAndWait();
            }
        }
    }
    
    // Quando o mouse passa por cima da imagem de voltar para a área de login
    @FXML
    void mouseMovido(MouseEvent event) {
        Image img = new Image("/codigofonte/view/imagens/setas-flechas-on-removebg-preview.png");
        imgVoltar.setImage(img);
    }
    
    // Quando o mouse sai da imagem
    @FXML
    void mouseSaiu(MouseEvent event) {
        Image img = new Image("/codigofonte/view/imagens/setas-flechas.png");
        imgVoltar.setImage(img);
    }
    
    /*
        Quando o usuário clica no botão de copiar, copia todo o texto de email recebido.
    
        Esse método existe, por que não dá para copiar o texto com os atalhos do teclado 
        (no caso: ctrl+c)
    */
    @FXML
    void copiarTexto(ActionEvent event) {
        if (this.emailAberto != null){
            try{
                final Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection ss = new StringSelection(this.emailAberto.getTexto());
                clip.setContents(ss, ss);
            }catch(Exception error){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRO");
                alert.setHeaderText("Erro ao copiar ao texto");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }
    }
    
    // Método para voltar na tela de login
    @FXML
    void voltarNoLogin(MouseEvent event) {
        
        try{
            
            if (!paneCarregar.isVisible()){

                if (this.lista_emails != null){
                    idListView.getItems().remove(0, this.lista_emails.size());
                }

                lblMensagem.setText("");

                panelCampo.setVisible(false);
                panelLista.setVisible(true);
          
                this.lista_emails = null;
                
                EmailZ.changeScreen("Login", null);
            }
            
        }catch(Exception error){
            System.out.println("Erro");
        }
    }
    
    /*
        Quando o usuário entra com a senha de e-mail errada ou não autorizada aparece uma 
        label na tela e quando o usuário clica nela é invocado este método, no qual chama
        um alert e exibe o erro correspondente
    */
    @FXML
    void lblVerErro(MouseEvent event) {
        Alert alert =  new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText("Erro ao receber os e-mails!");
        alert.setContentText("Provavelmente a senha está errada ou o e-mail não está "+
                "habilitado para ser usado em aplicativos como este.");
        alert.showAndWait();
    }

    /*
        Quando o usuário clicar no TextField acima da lista e digitar nele o título de algum email e der enter
        aparecerá - caso o email digitado exista - na lista somente o email digitado.
        Para fazer com que volte a tela com todos os emails, basta limpar o TextFielf e der enter.
    */
    @FXML
    void procurarEmail(ActionEvent event) {
  
        if (lista_emails != null){
            
            ArrayList<String> titulos = new ArrayList();
            
            if (!conteudoOriginal && procurarPorEmail.getText().equals("")){
                
                for (int i = 0; i < lista_emails.size(); i++){
                    titulos.add(lista_emails.get(i).getTitulo());
                }

                obsLista = FXCollections.observableArrayList(titulos);
                idListView.setItems(obsLista);

                lblMensagem.setText("");

                conteudoOriginal = true;
            }else{
                for (int i = 0; i < lista_emails.size(); i++){
                    if (lista_emails.get(i).getTitulo().equalsIgnoreCase(procurarPorEmail.getText())){
                        titulos.add(lista_emails.get(i).getTitulo());
                    }
                }

                obsLista = FXCollections.observableArrayList(titulos);
                idListView.setItems(obsLista);

                lblMensagem.setText("");

                conteudoOriginal = false;
            }
        }
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmailZ.addOnChangeScreenListener(new EmailZ.OnChangeScreen() {
            @Override
            public void onScreenChange(String newScreen, ControlarEmails ctEmail) {
                
                paneCarregar.setVisible(true);
                panelLista.setVisible(false);
                panelCampo.setVisible(false);
                
                // Alterando a visibilidade do Label de mensagem de erro
                // lblMensagem.setVisible(false);

                emailUser = ctEmail;
 
                txtEmail.setText(emailUser.getEmail());
                txtSenha.setText(emailUser.getSenha());
                
                emailAberto = null;
                
                carregarLista();
            }
        });
        
    }    
    
    /*
        Configures the textfield to receive suggestions
    */
    public void loadAutoComplete(){
        // Variables for autosuggestion :)
        AutoCompletionBinding<String> acb;
        Set<String> ps;
        
        ArrayList<String> values = new ArrayList();
        for (int i = 0; i < lista_emails.size(); i++){
            values.add(lista_emails.get(i).getTitulo());
        }
        
        String[] _possibleSuggestions = values.toArray(new String[0]);
        ps = new HashSet<>(Arrays.asList(_possibleSuggestions));
        TextFields.bindAutoCompletion(procurarPorEmail, _possibleSuggestions);
        
    }
    
    /*
        Método para carregar a lista de dados
    */
    public void carregarLista(){
        
        try{
            new Thread(){
                //private ArrayList<ListaEmails> lista_emails;

                @Override
                public void run(){

                    try{
                    
                        // Recebendo os dados dos emails lidos
                        lista_emails = LendoEmails.check("pop.gmail.com", "pop3", txtEmail.getText(), txtSenha.getText());

                        // System.out.println("Lista: " + lista_emails);
                        
                        if (lista_emails != null){

                            ArrayList<String> titulos = new ArrayList();

                            for (int i = 0; i < lista_emails.size(); i++){
                                titulos.add(lista_emails.get(i).getTitulo());
                            }

                            obsLista = FXCollections.observableArrayList(titulos);
                            idListView.setItems(obsLista);
                       
                            conteudoOriginal = true;
     
                            lblMensagem.setText("");
                            
                            loadAutoComplete();

                        }else{
                            lblMensagem.setVisible(true);
                            panelLista.setVisible(false);
                        }

                    }catch(Exception error){
                        lblMensagem.setVisible(true);
                        // panelLista.setVisible(false);
                        
                        System.out.println("ERRO: " + error.getMessage());
                    }finally{
                        paneCarregar.setVisible(false);
                        panelLista.setVisible(true);
                    }
                }

            }.start();
        }catch(Exception error){
            lblMensagem.setVisible(true);
        }
    }
    
}
