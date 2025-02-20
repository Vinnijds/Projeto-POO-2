package bancario.projeto.model;

import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private TipoTransacao tipo;
    private double valor;
    private Date data;
    
    public Transacao(TipoTransacao tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = new Date(); // Registra data e hora da transação
    }
    
    public TipoTransacao getTipo() {
        return tipo;
    }
    
    public double getValor() {
        return valor;
    }
    
    public Date getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return "Transacao{" +
               "tipo=" + tipo +
               ", valor=" + valor +
               ", data=" + data +
               '}';
    }
    
     
     
    public enum TipoTransacao {					//Define os tipos de transação
        DEPOSITO(1),
        SAQUE(2),
        TRANSFERENCIA(3);
        
        private final int valor;
    	
    	private TipoTransacao(int valor) {
    		this.valor = valor;
    }
    public int getValor() {
    	return valor;
    }
    }
}
