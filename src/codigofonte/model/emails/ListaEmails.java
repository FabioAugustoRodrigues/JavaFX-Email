package codigofonte.model.emails;

/**
 *
 * @author fabio
 */
public class ListaEmails {
    
    // Atributos
    private String titulo, deQuem, texto, data;
    
    // Construtor
    public ListaEmails(String titulo, String deQuem, String texto, String data){
        this.titulo = titulo;
        this.deQuem = deQuem;
        this.texto = texto;
        this.data = data;
    }
    
    // Métodos especiais
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDeQuem() {
        return deQuem;
    }

    public void setDeQuem(String deQuem) {
        this.deQuem = deQuem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "ListaEmails{" + "titulo=" + titulo + ", deQuem=" + deQuem + ", texto=" + texto + '}';
    }
    
}
