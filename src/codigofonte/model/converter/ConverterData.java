package codigofonte.model.converter;

/**
 *
 * @author fabio
 */
public class ConverterData {
    
    // Atributos
    private String hora, data, mes, dia, ano, dataSemFormat, formatacaoBR, diaExato;
    
    // Construtor
    public ConverterData(String dataSemFormat){
        this.dataSemFormat = dataSemFormat;
        converterBR();
    }
    
    // Métodos internos
    public boolean converterBR(){
        
        // Transformando a data sem formatação em uma char
        char[] data_char = getDataSemFormat().toCharArray();
        
        // Vamos formatar primeiramente o dia da semana
        String dia = data_char[0]+""+""+data_char[1]+""+""+data_char[2]+""+""+"";
        
        // Usamos switch para conferir os dias da semana
        switch(dia){
            case "Mon":
                setDia("Segunda-feira");
                break;
            case "Tue":
                setDia("Terça-feira");
                break;
            case "Wed":
                setDia("Quarta-feira");
                break;
            case "Thu":
                setDia("Quinta-feira");
                break;
            case "Fri":
                setDia("Sexta-feira");
                break;
            case "Sat":
                setDia("Sábado");
                break;
            case "Sun":
                setDia("Domingo");
                break;
            default:
                setDia(dia);
        }
        
        // Após a formatação do dia da semana, vamos formatar o mês
      
        // Mandamos o valor em inglês para uma segunda váriavel
        String mes = data_char[4]+""+data_char[5]+""+data_char[6]+"";
        
        // E usamos o bloco switch
        switch (mes){
            case "Jan":
                setMes("Janeiro");
                break;
            case "Feb":
                setMes("Fevereiro");
                break;
            case "Mar":
                setMes("Março");
                break;
            case "Apr":
                setMes("Abril");
                break;
            case "May":
                setMes("Maio");
                break;
            case "Jun":
                setMes("June");
                break;
            case "Jul":
                setMes("Julho");
                break;
            case "Aug":
                setMes("Agosto");
                break;
            case "Sep":
                setMes("Setembro");
                break;
            case "Oct":
                setMes("Outubro");
                break;
            case "Nov":
                setMes("Novembro");
                break;
            case "Dec":
                setMes("Dezembro");
                break;
            default:
                setMes(mes);
                break;
        }
        
        // Pegando o dia exato
        setDiaExato(data_char[8]+""+data_char[9]);
        
        // Pegando a hora
        setHora(data_char[12]+data_char[13]+"h"+data_char[14]+data_char[15]+"m");
        
        // Pegando o ano
        setAno(data_char[24]+""+data_char[25]+""+data_char[26]+""+data_char[27]+"");
        
        // Mandando tudo para uma formatação completa
        setFormatacaoBR("dia " + getDiaExato() + ", de " + getMes() + ", de " + getAno());
        
        return true;
    }
    
    // Métodos especiais
    public String getDiaExato() {
        return diaExato;
    }

    public void setDiaExato(String diaExato) {
        this.diaExato = diaExato;
    }
    
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getDataSemFormat() {
        return dataSemFormat;
    }

    public void setDataSemFormat(String dataSemFormat) {
        this.dataSemFormat = dataSemFormat;
    }

    public String getFormatacaoBR() {
        return formatacaoBR;
    }

    public void setFormatacaoBR(String formatacaoBR) {
        this.formatacaoBR = formatacaoBR;
    }
    
}
