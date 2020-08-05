package codigofonte.model.emails;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author fabio
 */
public class ControlarEmails {
    
    // Atributos
    private String email;
    protected String senha;
    
    // Construtor
    public ControlarEmails(String email, String senha){
        this.email = email;
        this.senha = senha;
    }
    
    // Métodos internos
    public boolean validarEmail(){
        boolean result = true; 
        try {
            InternetAddress emailAddr = new InternetAddress(getEmail());
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
    // Métodos getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
