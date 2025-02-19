package bancario.projeto.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa uma transação realizada em uma conta.
 * Dentro dela, temos o enum TipoTransacao.
 */
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private TipoTransacao tipo;
    private double valor;
    private Date data;
    
    public Transacao(TipoTransacao tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = new Date(); // Registra a data/hora da transação
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
    
    /**
     * Enum que define os tipos de transação.
     */
    public enum TipoTransacao {
        DEPOSITO,
        SAQUE,
        TRANSFERENCIA
    }
}
