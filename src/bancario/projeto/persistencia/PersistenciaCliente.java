package bancario.projeto.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.Conta;
import bancario.projeto.model.ContaCorrente;
import bancario.projeto.model.ContaPoupanca;
import exceptions.SaldoInsuficienteException;

public class PersistenciaCliente {
    private ArrayList<Cliente> clientes;
    private final String arquivo = "clientes.dat";

    public PersistenciaCliente() {
        clientes = carregarClientes();
    }
    

    private ArrayList<Cliente> carregarClientes() {
        File file = new File(arquivo);
        if (!file.exists()) {
   
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                return ((ArrayList<?>) obj).stream()
                        .filter(Cliente.class::isInstance)
                        .map(Cliente.class::cast)
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    

    public void listarContasDoCliente(String cpf) {
        Cliente cliente = localizarClientePorCpf(cpf); // Busca o cliente pelo CPF
        if (cliente != null) {
            List<Conta> contas = cliente.getContas(); // Obtém a lista de contas do cliente
            if (contas.isEmpty()) {
                System.out.println("O cliente não possui contas cadastradas.");
            } else {
                System.out.println("Contas do cliente " + cliente.getNome() + ":");
                for (Conta conta : contas) {
                    System.out.println( conta.getClass().getSimpleName() + " - Número da conta: " + conta.getNumeroConta() + ", Saldo: R$ " + conta.consultarSaldo());
                }
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    private void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void atualizarClientes() {			// Método público para atualizar os dados dos clientes.
        salvarClientes();
    }

    
    public void adicionarCliente(Cliente cliente) {
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
            salvarClientes();
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente já cadastrado.");
        }
    }
    

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    
    public Cliente localizarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    
    public void removerCliente(Cliente cliente) {
        if (clientes.remove(cliente)) {
            salvarClientes();
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    public void criarContaParaCliente(String cpf, String numeroConta, String tipoConta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            Conta novaConta;
            if (tipoConta.equalsIgnoreCase("Corrente")) {
                novaConta = new ContaCorrente(numeroConta);
            } else if (tipoConta.equalsIgnoreCase("Poupanca")) {
                novaConta = new ContaPoupanca(numeroConta);
            } else {
                System.out.println("Tipo de conta inválido.");
                return;
            }
            cliente.adicionarConta(novaConta);
            salvarClientes();
            System.out.println("Conta " + tipoConta + " criada e associada ao cliente.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    public void realizarDeposito(String cpf, String numeroConta, double valor) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumeroConta().equals(numeroConta)) {
                    conta.depositar(valor);
                    salvarClientes();
                    System.out.println("Depósito realizado com sucesso.");
                    return;
                }
            }
            System.out.println("Conta não encontrada.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    public void realizarSaque(String cpf, String numeroConta, double valor) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumeroConta().equals(numeroConta)) {
                	try {
                	    conta.sacar(valor);
                	    salvarClientes();
                	    System.out.println("Saque realizado com sucesso.");
                	} catch (SaldoInsuficienteException e) {
                	    System.out.println(e.getMessage());
                	}
                    return;
                }
            }
            System.out.println("Conta não encontrada.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    
    public void realizarTransferencia(String cpfOrigem, String numeroContaOrigem, String cpfDestino, String numeroContaDestino, double valor) {
        Cliente clienteOrigem = localizarClientePorCpf(cpfOrigem);
        Cliente clienteDestino = localizarClientePorCpf(cpfDestino);

        if (clienteOrigem != null && clienteDestino != null) {
            Conta contaOrigem = null;
            Conta contaDestino = null;

            for (Conta conta : clienteOrigem.getContas()) {
                if (conta.getNumeroConta().equals(numeroContaOrigem)) {
                    contaOrigem = conta;
                    break;
                }
            }

            for (Conta conta : clienteDestino.getContas()) {
                if (conta.getNumeroConta().equals(numeroContaDestino)) {
                    contaDestino = conta;
                    break;
                }
            }

            if (contaOrigem != null && contaDestino != null) {
                try {
                    contaOrigem.transferir(valor, contaDestino);
                    salvarClientes();
                    System.out.println("Transferência realizada com sucesso.");
                } catch (SaldoInsuficienteException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Conta de origem ou destino não encontrada.");
            }
        } else {
            System.out.println("Cliente de origem ou destino não encontrado.");
        }
    }


    public void consultarSaldo(String cpf, String numeroConta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumeroConta().equals(numeroConta)) {
                    System.out.println("Saldo da conta " + numeroConta + ": " + conta.consultarSaldo());
                    return;
                }
            }
            System.out.println("Conta não encontrada.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    

    public void removerConta(String cpf, String numeroConta) {
        Cliente cliente = localizarClientePorCpf(cpf);
        if (cliente != null) {
            Conta contaParaRemover = null;
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumeroConta().equals(numeroConta)) {
                    contaParaRemover = conta;
                    break;
                }
            }
            if (contaParaRemover != null) {
                cliente.getContas().remove(contaParaRemover);
                salvarClientes();
                System.out.println("Conta removida com sucesso.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }


    public void consultarBalanco(String cpf) {
        Cliente cliente = localizarClientePorCpf(cpf); // Busca o cliente pelo CPF
        if (cliente != null) {
            double saldoTotal = 0.0;
            List<Conta> contas = cliente.getContas(); // Obtém a lista de contas do cliente

            if (contas.isEmpty()) {
                System.out.println("O cliente não possui contas cadastradas.");
            } else {
                for (Conta conta : contas) {
                    saldoTotal += conta.consultarSaldo();
                }
                System.out.println("Balanço total do cliente " + cliente.getNome() + ": R$ " + saldoTotal);
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
}
