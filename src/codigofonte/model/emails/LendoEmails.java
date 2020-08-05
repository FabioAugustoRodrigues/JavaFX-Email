package codigofonte.model.emails;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.scene.control.Alert;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/*
    Nesta classe, abrimos cada pasta na caixa de correio e recebemos as suas respectivas mensagens.
    Pegamos os valores assunto De, Para, data...

    Utilizando a API JavaMail
*/
public class LendoEmails {

    public static ArrayList<ListaEmails> check(String host, String storeType, String user, String password){
        
        List<ListaEmails> lista_emails = new ArrayList();
        
        try {

            // criar campo de propriedades
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            // crie o objeto de armazenamento POP3 e conecte-se ao servidor pop
            Store store = emailSession.getStore("pop3s");
           
            store.connect(host, user, password);
            
            // crie o objeto da pasta e abra-o
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // recuperar as mensagens da pasta em uma matriz
            Message[] messages = emailFolder.getMessages();

            int x = 1;
            String data = null;
    
            // iterando e recuperando os dados num ArrayList de ListaEmails
            for (int i = 0; i < messages.length; i++){
                
                x = i+1;
                Message message = messages[messages.length-x];
                
                data = messages[messages.length-x].getSentDate()+"";
                
                ListaEmails lista = new ListaEmails(message.getSubject(), message.getFrom()[0]+"", message.getContent().toString(), data);
                lista_emails.add(lista);
               
            }
            
            // fechar os objetos de armazenamento e pasta
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) { 
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Ocorreu um erro no resgatamento de dados!");
            alert.setContentText("Provedor de segurança solicitado, não está disponível no ambiente!");
            alert.showAndWait();
            
            return null;
        } catch (MessagingException e) {         
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Ocorreu um erro no resgatamento de dados!");
            alert.setContentText("E-mail ou senha incorreto!");
            alert.showAndWait();
            
            return null;
        } catch (Exception e) {           
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Ocorreu um erro no resgatamento de dados!");
            alert.showAndWait();
            
            return null;
        }
        
        return (ArrayList<ListaEmails>) lista_emails;
    }

    /*public static void main(String[] args) {
    
    String host = "pop.gmail.com";// change accordingly
    String mailStoreType = "pop3";
    String username = "abc@gmail.com";// change accordingly
    String password = "*******";// change accordingly
    
    check(host, mailStoreType, username, password);
    
    }*/

}