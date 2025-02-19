package bancario.projeto.model;

import exceptions.SaldoInsuficienteException;

public interface IConta {
    void depositar(double valor);
    void sacar(double valor) throws SaldoInsuficienteException,IllegalArgumentException;;
    double consultarSaldo();
    void transferir(double valor, Conta destino) throws SaldoInsuficienteException,IllegalArgumentException;;
}

