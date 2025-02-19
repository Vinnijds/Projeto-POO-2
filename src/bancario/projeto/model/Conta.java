package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.SaldoInsuficienteException;

public abstract class Conta implements IConta, Serializable {
    private static final long serialVersionUID = 1L;
    private String numeroConta;
    protected double saldo; // protegido para acesso nas subclasses
    protected List<Transacao> extrato; // Lista de transações da conta

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
    
    // Método para registrar uma transação
    protected void registrarTransacao(Transacao.TipoTransacao tipo, double valor) {
        extrato.add(new Transacao(tipo, valor));
    }
    
    // Métodos auxiliares para manipulação do saldo sem registro automático
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
    
    // Método para imprimir o extrato da conta
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
    
    // Depósito: atualiza saldo e registra transação como DEPOSITO
    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            adicionarSaldo(valor);
            registrarTransacao(Transacao.TipoTransacao.DEPOSITO, valor);
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }
    
    // Saque: atualiza saldo e registra transação como SAQUE
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        retirarSaldo(valor);
        registrarTransacao(Transacao.TipoTransacao.SAQUE, valor);
    }

    // Método transferir permanece abstrato para que cada conta implemente sua lógica
    @Override
    public abstract void transferir(double valor, Conta destino) throws SaldoInsuficienteException;

    @Override
    public String toString() {
        return "Conta{numeroConta='" + numeroConta + "', saldo=" + saldo + "}";
    }
}
