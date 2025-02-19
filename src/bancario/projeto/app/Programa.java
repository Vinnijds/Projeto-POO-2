package bancario.projeto.app;

import java.util.Scanner;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.Conta;
import bancario.projeto.persistencia.PersistenciaCliente;

public class Programa {
    public static void main(String[] args) {
        PersistenciaCliente persistencia = new PersistenciaCliente();
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        do {
            System.out.println("===== Menu =====");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Exibir Clientes");
            System.out.println("3. Buscar Cliente por CPF");
            System.out.println("4. Deletar Cliente");
            System.out.println("5. Adicionar Conta para Cliente");
            System.out.println("6. Efetuar Depósito");
            System.out.println("7. Efetuar Saque");
            System.out.println("8. Efetuar Transferência");
            System.out.println("9. Consultar Saldo da Conta");
            System.out.println("10. Listar Contas de um Cliente");
            System.out.println("11. Consultar Balanço de Contas");
            System.out.println("12. Imprimir Extrato da Conta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma Opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    Cliente cliente = new Cliente(cpf, nome);
                    persistencia.adicionarCliente(cliente);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 2:
                    persistencia.listarClientes();
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 3:
                    System.out.print("CPF: ");
                    String cpfConsulta = sc.nextLine();
                    Cliente encontrado = persistencia.localizarClientePorCpf(cpfConsulta);
                    if (encontrado != null) {
                        System.out.println("Cliente encontrado: " + encontrado);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 4:
                    System.out.print("CPF: ");
                    String cpfRemover = sc.nextLine();
                    Cliente clienteRemover = persistencia.localizarClientePorCpf(cpfRemover);
                    if (clienteRemover != null) {
                        persistencia.removerCliente(clienteRemover);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 5:
                    System.out.print("CPF do Cliente: ");
                    String cpfConta = sc.nextLine();
                    System.out.print("Número da Conta: ");
                    String numeroConta = sc.nextLine();
                    System.out.println("Escolha o tipo de conta:");
                    System.out.println("1 - Conta Corrente");
                    System.out.println("2 - Conta Poupança");
                    System.out.print("Opção: ");

                    int opcaoConta;
                    try {
                        opcaoConta = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Opção inválida. Digite apenas 1 ou 2.");
                        break;
                    }

                    String tipoConta = null;
                    switch (opcaoConta) {
                        case 1:
                            tipoConta = "Corrente"; // ou "Bancaria", conforme a lógica do seu sistema
                            break;
                        case 2:
                            tipoConta = "Poupanca";
                            break;
                        default:
                            System.out.println("Opção inválida. Escolha entre 1 (Corrente) ou 2 (Poupança).");
                            break;
                    }

                    if (tipoConta != null) {
                        persistencia.criarContaParaCliente(cpfConta, numeroConta, tipoConta);
                        System.out.println("Conta criada com sucesso!");
                    }

                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;

                case 6:
                    System.out.print("CPF do Cliente: ");
                    String cpfDeposito = sc.nextLine();
                    System.out.print("Número da Conta: ");
                    String numeroContaDeposito = sc.nextLine();
                    System.out.print("Valor do Depósito: ");
                    double valorDeposito;
                    try {
                        valorDeposito = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                        break;
                    }
                    persistencia.realizarDeposito(cpfDeposito, numeroContaDeposito, valorDeposito);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 7:
                    System.out.print("CPF do Cliente: ");
                    String cpfSaque = sc.nextLine();
                    System.out.print("Número da Conta: ");
                    String numeroContaSaque = sc.nextLine();
                    System.out.print("Valor do Saque: ");
                    double valorSaque;
                    try {
                        valorSaque = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                        break;
                    }
                    persistencia.realizarSaque(cpfSaque, numeroContaSaque, valorSaque);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 8:
                    System.out.print("CPF do Cliente de Origem: ");
                    String cpfOrigem = sc.nextLine();
                    System.out.print("Número da Conta de Origem: ");
                    String numeroContaOrigem = sc.nextLine();
                    System.out.print("CPF do Cliente de Destino: ");
                    String cpfDestino = sc.nextLine();
                    System.out.print("Número da Conta de Destino: ");
                    String numeroContaDestino = sc.nextLine();
                    System.out.print("Valor da Transferência: ");
                    double valorTransferencia;
                    try {
                        valorTransferencia = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                        break;
                    }
                    persistencia.realizarTransferencia(cpfOrigem, numeroContaOrigem, cpfDestino, numeroContaDestino, valorTransferencia);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 9:
                    System.out.print("CPF do Cliente: ");
                    String cpfSaldo = sc.nextLine();
                    System.out.print("Número da Conta: ");
                    String numeroContaSaldo = sc.nextLine();
                    persistencia.consultarSaldo(cpfSaldo, numeroContaSaldo);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 10:
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfListarContas = sc.nextLine();
                    persistencia.listarContasDoCliente(cpfListarContas);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 11:
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfBalanco = sc.nextLine();
                    persistencia.consultarBalanco(cpfBalanco);
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;
                case 12:
                    System.out.print("CPF do Cliente: ");
                    String cpfExtrato = sc.nextLine();
                    System.out.print("Número da Conta: ");
                    String numeroContaExtrato = sc.nextLine();
                    Cliente clienteExtrato = persistencia.localizarClientePorCpf(cpfExtrato);
                    if (clienteExtrato != null) {
                        boolean contaEncontrada = false;
                        for (Conta conta : clienteExtrato.getContas()) {
                            if (conta.getNumeroConta().equals(numeroContaExtrato)) {
                                conta.imprimirExtrato();
                                contaEncontrada = true;
                                break;
                            }
                        }
                        if (!contaEncontrada) {
                            System.out.println("Conta não encontrada.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    System.out.println("Pressione Enter para continuar...");
                    sc.nextLine();
            }
        } while (opcao != 0);

        sc.close();
    }
}
