package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.SaldoInsuficienteException;

public abstract class Conta implements IConta, Serializable {	
    private static final long serialVersionUID = 1L;
    private String numeroConta;
    protected double saldo; 									// protegido para acesso nas subclasses
    protected List<Transacao> extrato; 							// Lista de transações da conta

    public Conta(String numeroConta) {
        this.numeroConta = numeroConta;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    @Override
    public double consultarSaldo() {
        return saldo;
    }
    
   
    protected void registrarTransacao(Transacao.TipoTransacao tipo, double valor) {
        extrato.add(new Transacao(tipo, valor));
    }
    
   
    protected void retirarSaldo(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
        } else {
            throw new SaldoInsuficienteException("Saque inválido ou saldo insuficiente.");
        }
    }
    
    
    protected void adicionarSaldo(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }
    
    
    public void imprimirExtrato() {
        System.out.println("Extrato da conta " + numeroConta + ":");
        if (extrato.isEmpty()) {
            System.out.println("Sem transações.");
        } else {
            for (Transacao t : extrato) {
                System.out.println(t);
            }
        }
    }
    
   
    @Override
    public void depositar(double valor) {					 // Depósito atualiza saldo e registra transação como DEPOSITO
        if (valor > 0) {
            adicionarSaldo(valor);
            registrarTransacao(Transacao.TipoTransacao.DEPOSITO, valor);
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }
    
    
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {			// Saque, atualiza saldo e registra transação como SAQUE
        retirarSaldo(valor);
        registrarTransacao(Transacao.TipoTransacao.SAQUE, valor);
    }

    
    @Override
    public abstract void transferir(double valor, Conta destino) throws SaldoInsuficienteException;

    @Override
    public String toString() {
        return "Conta{numeroConta='" + numeroConta + "', saldo=" + saldo + "}";
    }
}
