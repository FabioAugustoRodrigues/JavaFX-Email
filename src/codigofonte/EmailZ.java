package codigofonte;

import codigofonte.model.emails.ControlarEmails;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Fábio Augusto Rodrigues
 */
public class EmailZ extends Application {
    
    /*
        ´necessário habilitar a opção de acesso a aplicativos menos seguros no seu email para podeÉ
        logar no aplicativo   
    */
    
    // Atributos
    private static Stage stageX;
    private static Scene scene_user, scene_login;
    
    @Override
    public void start(Stage stage) throws Exception {
    
        // Instanciando a tela de Login
        /*
            Nessa tela o usuário informa o e-mail a sua respectiva senha para 
            poder entrar na tela de usuário
        */
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/fxml/TelaLogin.fxml"));;
        Parent root = loader.load();
        scene_login = new Scene(root);
        
        // Instanciando a tela de usuario
        /*
            Nessa tela, após o usuário ter feito o login, o usuário poderá ver os e-mails recebidos
            e enviar e-mails
        */
        Parent root2 = FXMLLoader.load(getClass().getResource("view/fxml/TelaUsuario.fxml"));
        scene_user = new Scene(root2);

        stageX = stage;
        
        stageX.getIcons().add(new Image(getClass().getResourceAsStream("view/imagens/icon.png")));
        stageX.setScene(scene_login);
        stageX.setTitle("EmailZ - Login");
        stageX.setResizable(false);
        stageX.show();
    
    }

    // Método para mudar de tela
    /*
        Basta acessar esse método e passar como parâmertro qual tela irá aparecer
    */
    public static void changeScreen(String tela, ControlarEmails ctEmail){
        switch (tela){
            case "Login":
                stageX.setScene(scene_login);
                stageX.setResizable(false);
                stageX.setTitle("EmailZ - Login");
                notifyAllListeners("Login", ctEmail);
                break;
            case "Usuario":
                stageX.setScene(scene_user);
                stageX.setResizable(false);
                stageX.setTitle("EmailZ - Usuário");
                notifyAllListeners("Usuario", ctEmail);
                break;
        }
    }
    
    // Sobrecarga
    public static void changeScreen(String tela){
        changeScreen(tela, null);
    }
    
    // MÉTODOS PARA LIDAR COM A PASSAGEM DE PARÂMETROS NA TROCA DE TELA
    private static ArrayList<OnChangeScreen> listeners = new ArrayList();
    
    public static interface OnChangeScreen{
        void onScreenChange(String newScreen, ControlarEmails ctEmail);
    }
    
    public static void addOnChangeScreenListener(OnChangeScreen newListener){
        listeners.add(newListener);
    }
    
    private static void notifyAllListeners(String newScreen, ControlarEmails ctEmail){
        for (OnChangeScreen l: listeners){
            l.onScreenChange(newScreen, ctEmail);
        }
    }
    
    // MÉTODO MAIN
    public static void main(String[] args) {
        launch(args);
    }
    
}
