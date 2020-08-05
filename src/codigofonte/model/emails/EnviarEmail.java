package codigofonte.model.emails;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

/**
 *
 * @author fabio
 */
public class EnviarEmail{
    
    // Métodos internos
    public static boolean enviarEmail(String para, String titulo, String texto, String de, String senha, String anexo){
        
        // Creamos a mensagem de e-mail
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        email.setAuthenticator(new DefaultAuthenticator(de, senha));

        try{
            email.setFrom(de);

            email.setSubject(titulo);
            email.setMsg(texto);
            email.addTo(para);

            if (anexo != null){
                // Creamos o anexo
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(anexo);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("Enviar e-mail");
                attachment.setName("E-mails");

                // adicionando o anexo
                email.attach(attachment);
            }
            
            // enviando o e-mail
            email.send();
                
        }catch(Exception error){
            return false;
        }
        
        return true;
    }
}
