package bancario.projeto.model;

import exceptions.SaldoInsuficienteException;

public class ContaCorrente extends Conta {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Defina uma taxa de transferência diferenciada para ContaCorrente (por exemplo, 5%)
    private static final double TAXA_TRANSFERENCIA = 0.05; 

    public ContaCorrente(String numeroConta) {
        super(numeroConta);
    }

    @Override
    public void transferir(double valor, Conta destino) throws SaldoInsuficienteException {
        double taxa = valor * TAXA_TRANSFERENCIA;
        double total = valor + taxa;
        // Retira o valor total (valor + taxa) sem registrar transação automática
        retirarSaldo(total);
        // Registra a transferência na conta de origem
        registrarTransacao(Transacao.TipoTransacao.TRANSFERENCIA, total);
        // Adiciona o valor na conta de destino e registra a operação como transferência
        destino.adicionarSaldo(valor);
        destino.registrarTransacao(Transacao.TipoTransacao.TRANSFERENCIA, valor);
        System.out.println("Transferência de R$ " + valor + " realizada com taxa de R$ " + taxa + " (Conta Corrente).");
    }
}