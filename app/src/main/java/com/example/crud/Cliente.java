package com.example.crud;

// Classe que representa o modelo de dados de um Cliente
public class Cliente {
    private int id; // ID único do cliente no banco de dados
    private String nome; // Nome do cliente
    private String email; // Email do cliente
    private String telefone; // Telefone do cliente

    // Construtor vazio (necessário para algumas operações)
    public Cliente() {
    }

    // Construtor para criar um novo cliente sem ID (o ID será gerado pelo banco)
    public Cliente(String nome, String email , String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Construtor para criar um cliente com ID (útil ao recuperar do banco)
    public Cliente(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Métodos Getters e Setters para acessar e modificar os atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Sobrescreve o método toString() para exibir o cliente de forma legível na ListView
    @Override
    public String toString() {
        return id + " - " + nome + " (" + email + ") - " + telefone + "";
    }
}
