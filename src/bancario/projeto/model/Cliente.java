package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cpf;
    private String nome;
    private List<Conta> contas;

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    
    public String getCpf() {
        return cpf;
    }

    
    public String getNome() {
        return nome;
    }

    
    public List<Conta> getContas() {
        return contas;
    }

    
    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    
    @Override
    public String toString() {
        return "Cliente{cpf='" + cpf + "', nome='" + nome + "'}";
    }

    
    @Override
    public boolean equals(Object obj) {					//considera dois clientes com CPF iguais
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return cpf.equals(cliente.cpf);
    }

    
    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
