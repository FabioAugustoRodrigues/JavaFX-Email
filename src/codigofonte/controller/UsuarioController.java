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
    
    // Atributos do JAVAFX
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
    
    // == Métodos ===================================================================
    
    /*
        Quando o usuário clicar em alguma célula na lista (ou na própria lista em si) este método é chamado
        e inseri num campo, as informações da célula correspondente
    */
    @FXML
    void mouseClicadoNaLista(MouseEvent event) {

        if (this.lista_emails != null && idListView.getSelectionModel().getSelectedIndex() != -1){
            
            // Primeiramente vamos decidir qual tela irá aprecer
            panelLista.setVisible(false); // A tela de lista, não ficará vísivel para poder ver o conteúdo
            panelCampo.setVisible(true); // A tela do conteúdo começa a ser vísivel

            // Inserindo os dados na tela de conteúdo
            idTitulo.setText(idListView.getSelectionModel().getSelectedItem()); 

            idTitulo.setMaxSize(330, 33); // Definindo um tamanho para o título

            // Pegando a localização dos dados restantes num array
            int id = 0;

            for (int i = 0; i < lista_emails.size(); i++){
                if (idTitulo.getText().equals(lista_emails.get(i).getTitulo())){
                    id = i;
                    break;
                }
            }

            // Inserindo nas labels os dados
            
            // Iserindo o nome de quem enviou o email
            lblDeQuem.setText(lista_emails.get(id).getDeQuem());
            lblDeQuem.setMaxSize(330, 14); // Ajustando tamanho
            
            // Inserindo o texto
            lblTexto.setText(lista_emails.get(id).getTexto());
            lblTexto.setMaxSize(330, 166);
            
            // Pegando a data formatada em BR           
            ConverterData cvData = new ConverterData(lista_emails.get(id).getData());
            
            // Mandando para a label
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
        
        // Verificamos se a lista não é nula
        if (this.lista_emails != null){
            
            // Alterando a visibilidade dos painés
            paneCarregar.setVisible(true);
            panelLista.setVisible(false);
            panelCampo.setVisible(false);
            
            // Primeiramente removemos todos os itens atuais da lista
            // Caso não seja removido, ao atualizá-la, os itens seria adicionado, ou seja, duplicaria
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
    }
    
    /*
        Método para o usuário iniciar uma nova conversa por e-mail;
        Esse método chama uma nova tela JavaFX, com os componentes necessários para enviar um e-mail para alguém
    */
    @FXML
    void novaConversa(ActionEvent event) {
        
        // Lembrando que quando o emailUser é nulo, é por que ocorreu um erro para resgatar os dados
        // Ou seja, um erro no login.
        if (this.lista_emails != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codigofonte/view/fxml/TelaEscrever.fxml"));
                Parent form = loader.load();
                //Parent form = FXMLLoader.load(getClass().getResource("/codigofonte/view/fxml/TelaEscrever.fxml"));
                
                /*
                    Aqui pegamos o Controller que será usado na tela do formulário
                    e chamamos o método "inserirDados" para passar o e-mail e a senha do usuário
                    basicamente tudo isso serve para a passagem de parâmetro entre telas 
                */
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
        
        // Pegando a localização dos dados restantes num array
        int id = 0;

        for (int i = 0; i < lista_emails.size(); i++){
            if (idTitulo.getText().equals(lista_emails.get(i).getTitulo())){
                id = i;
                break;
            }
        }

        try{
            // Copiando o texto
            final Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection ss = new StringSelection(lista_emails.get(id).getTexto());
            clip.setContents(ss, ss);
        }catch(Exception error){
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro ao copiar ao texto");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }
    
    // Método para voltar na tela de login
    @FXML
    void voltarNoLogin(MouseEvent event) {
        
        try{
            
            // Verifcamos se a tela de carregamento está ativa, caso esteja ativa
            // não podemos voltar para a área de login
            if (!paneCarregar.isVisible()){
                // Se lista_emails não for nulo, removemos todo o conteúdo da lista
                // Caso for nulo, não removemos nada, pelo simples fato de não haver algo nela mesmo
                if (this.lista_emails != null){
                    idListView.getItems().remove(0, this.lista_emails.size());
                }

                // Atualizando a mensagem
                lblMensagem.setText("");

                // Atualizando as telas
                panelCampo.setVisible(false);
                panelLista.setVisible(true);
                
                // "Limpando" os dados da lista_emails
                this.lista_emails = null;
                
                // Por fim, chamando a tela de login
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
  
        // Primeiro verificamos se a lista de emails não é nula
        if (lista_emails != null){
            
            // Criando um array para armazenar apenas os títulos
            ArrayList<String> titulos = new ArrayList();
            
            /*
                Observação: a váriavel "conteudoOriginal" serve para indicarmos se na lista
                há todos os emails ou apenas os emails pesquisados no TextField.
                Caso esteja todos os emails, o "conteudoOriginal" será true, caso contrário será false
            */
            if (!conteudoOriginal && procurarPorEmail.getText().equals("")){
                
                // Inserindo no array "titulos", os titulos de todos os e-mails lidos
                for (int i = 0; i < lista_emails.size(); i++){
                    titulos.add(lista_emails.get(i).getTitulo());
                }

                // Inserindo os dados no ListView
                obsLista = FXCollections.observableArrayList(titulos);
                idListView.setItems(obsLista);

                // Atualizando label de mensagem
                lblMensagem.setText("");

                // Como inserimos denovo todos os emails, conteudoOriginal será true
                conteudoOriginal = true;
            }else{
                // Inserindo no array "titulos", os titulos de todos os e-mails lidos
                for (int i = 0; i < lista_emails.size(); i++){
                    if (lista_emails.get(i).getTitulo().equalsIgnoreCase(procurarPorEmail.getText())){
                        titulos.add(lista_emails.get(i).getTitulo());
                    }
                }

                // Inserindo os dados no ListView
                obsLista = FXCollections.observableArrayList(titulos);
                idListView.setItems(obsLista);

                // Atualizando label de mensagem
                lblMensagem.setText("");

                // conteudoOriginal será false, por que a lista tem somente os emails pesquisados
                conteudoOriginal = false;
            }
        }
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmailZ.addOnChangeScreenListener(new EmailZ.OnChangeScreen() {
            @Override
            public void onScreenChange(String newScreen, ControlarEmails ctEmail) {
                
                // Alterando a visibilidade dos painés, para que toda vez que passe pelo initializa
                // o primeiro painel a aparecer seja o que carrega um ProgressBar
                paneCarregar.setVisible(true);
                panelLista.setVisible(false);
                panelCampo.setVisible(false);
                
                // Alterando a visibilidade do Label de mensagem de erro
                // lblMensagem.setVisible(false);
                
                // Recebendo objeto que tem o e-mail e a senha do usuário logado
                emailUser = ctEmail;
                
                // Inserindo dados nos formulários
                txtEmail.setText(emailUser.getEmail());
                txtSenha.setText(emailUser.getSenha());
                
                // Carregando lista de dados
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

                            // Criando um array para armazenar apenas os títulos
                            ArrayList<String> titulos = new ArrayList();

                            // Inserindo no array "titulos", os titulos de todos os e-mails lidos
                            for (int i = 0; i < lista_emails.size(); i++){
                                titulos.add(lista_emails.get(i).getTitulo());
                            }

                            // Inserindo os dados no ListView
                            obsLista = FXCollections.observableArrayList(titulos);
                            idListView.setItems(obsLista);
                       
                            /*
                                Observação: a váriavel "conteudoOriginal" serve para indicarmos se na lista
                                há todos os emails ou apenas os emails pesquisados em um TextField.
                                Caso esteja todos os emails, o "conteudoOriginal" será true, caso contrário será false
                            */
                            conteudoOriginal = true;
     
                            // Atualizando label de mensagem
                            lblMensagem.setText("");
                            
                            // Configura o textfield para receber sugestões de autocomplete
                            loadAutoComplete();

                        }else{
                            // Exibindo erro
                            lblMensagem.setVisible(true);
                            // Fazendo com que o panel da lista não fique visivel
                            panelLista.setVisible(false);
                        }

                    }catch(Exception error){
                        // Exibindo erro
                        lblMensagem.setVisible(true);
                        // Fazendo com que o panel da lista não fique visivel
                        // panelLista.setVisible(false);
                        
                        System.out.println("ERRO: " + error.getMessage());
                    }finally{
                        // Atualizando visibilidade dos Painés
                        paneCarregar.setVisible(false);
                        panelLista.setVisible(true);
                    }
                }

            }.start();
        }catch(Exception error){
            // Exibindo erro
            lblMensagem.setVisible(true);
        }
    }
    
}
