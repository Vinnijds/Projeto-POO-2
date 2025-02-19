package bancario.projeto.model;

import exceptions.SaldoInsuficienteException;

public class ContaPoupanca extends Conta {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Taxa de transferência menor para conta poupança (por exemplo, 1%)
    private static final double TAXA_TRANSFERENCIA = 0.01; 

    public ContaPoupanca(String numeroConta) {
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
        System.out.println("Transferência de R$ " + valor + " realizada com taxa de R$ " + taxa + " (Conta Poupança).");
    }
}

