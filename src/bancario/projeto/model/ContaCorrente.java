package bancario.projeto.model;

import exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta{
	  
	private static final long serialVersionUID = 1L;
	
    private static final double TAXA_TRANSFERENCIA = 0.05; 

    public ContaCorrente(String numeroConta) {
        super(numeroConta);
    }

    @Override
    public void transferir(double valor, Conta destino) throws SaldoInsuficienteException {
        double taxa = valor * TAXA_TRANSFERENCIA;
        double total = valor + taxa;
        retirarSaldo(total);
        registrarTransacao(Transacao.TipoTransacao.TRANSFERENCIA, total);
        destino.adicionarSaldo(valor);
        destino.registrarTransacao(Transacao.TipoTransacao.TRANSFERENCIA, valor);
        System.out.println("Transferência de R$ " + valor + " realizada com taxa de R$ " + taxa + " (Conta Corrente).");
    }
}