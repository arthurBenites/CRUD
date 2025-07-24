package com.example.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etEmail;
    private Button btnAdicionar, btnAtualizar, btnDeletar;
    private ListView lvClientes;
    private DataBaseHelper databaseHelper;
    private ArrayAdapter<Cliente> clienteAdapter;
    private Cliente clienteSelecionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnDeletar = findViewById(R.id.btnDeletar);
        lvClientes = findViewById(R.id.lvClientes);

        databaseHelper = new DataBaseHelper(this);
        carregarClientesNaLista();

        btnAdicionar.setOnClickListener(v -> adicionarCliente());

        lvClientes.setOnItemClickListener((parent, view, position, id) -> {
            clienteSelecionado = (Cliente) parent.getItemAtPosition(position);
            etNome.setText(clienteSelecionado.getNome());
            etEmail.setText(clienteSelecionado.getEmail());
            btnAtualizar.setEnabled(true);
            btnDeletar.setEnabled(true);
            btnAdicionar.setEnabled(false);
        });

        btnAtualizar.setOnClickListener(v -> atualizarCliente());
        btnDeletar.setOnClickListener(v -> deletarCliente());
    }

    private void carregarClientesNaLista() {
        List<Cliente> clientes = databaseHelper.getAllClientes();
        clienteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientes);
        lvClientes.setAdapter(clienteAdapter);
    }

    private void adicionarCliente() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = new Cliente(nome, email);
        long id = databaseHelper.adicionarCliente(cliente);

        if (id != -1) {
            Toast.makeText(this, "Cliente adicionado com sucesso!", Toast.LENGTH_SHORT).show();
            limparCamposEResetarUI();
        } else {
            Toast.makeText(this, "Erro ao adicionar cliente. O email pode já existir.", Toast.LENGTH_LONG).show();
        }
    }

    private void atualizarCliente() {
        if (clienteSelecionado == null) return;

        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        clienteSelecionado.setNome(nome);
        clienteSelecionado.setEmail(email);
        int rowsAffected = databaseHelper.atualizarCliente(clienteSelecionado);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            limparCamposEResetarUI();
        } else {
            Toast.makeText(this, "Erro ao atualizar cliente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletarCliente() {
        if (clienteSelecionado == null) return;
        databaseHelper.deletarCliente(clienteSelecionado);
        Toast.makeText(this, "Cliente deletado com sucesso!", Toast.LENGTH_SHORT).show();
        limparCamposEResetarUI();
    }

    private void limparCamposEResetarUI() {
        etNome.setText("");
        etEmail.setText("");
        clienteSelecionado = null;
        btnAdicionar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnDeletar.setEnabled(false);
        carregarClientesNaLista();
    }
}