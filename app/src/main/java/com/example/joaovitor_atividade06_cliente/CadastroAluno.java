package com.example.joaovitor_atividade06_cliente;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroAluno extends AppCompatActivity
{
    private Button btnCadastrar, btnVoltar, btnDeletar;
    private TextInputEditText edtnome, edtidade, edtnota1, edtnota2;
    private AlunoRepository repository;
    private Aluno alunoRecebido;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        setup();

        ContentResolver contentResolver = getContentResolver();
        repository = new AlunoRepository(contentResolver);

        alunoRecebido = (Aluno) getIntent().getSerializableExtra("aluno");

        if (alunoRecebido != null) {
            preencherDados(alunoRecebido);
            definirModoDeEdicao();
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                salvar();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               voltarParaMain();
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletar();
            }
        });
    }

    private void setup()
    {
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnDeletar = findViewById(R.id.btnDeletar);
        edtnome = findViewById(R.id.nome);
        edtidade = findViewById(R.id.idade);
        edtnota1 = findViewById(R.id.nota1);
        edtnota2 = findViewById(R.id.nota2);
    }

    private void salvar()
    {
        String nome = edtnome.getText().toString().trim();
        String idadeText = edtidade.getText().toString().trim();
        String nota1Text = edtnota1.getText().toString().trim();
        String nota2Text = edtnota2.getText().toString().trim();

        if (nome.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha o nome do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idadeText.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a idade do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota1Text.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a primeira nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota2Text.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a segunda nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idade = Integer.parseInt(idadeText);
        Double nota1 = Double.parseDouble(nota1Text);
        Double nota2 = Double.parseDouble(nota2Text);

        if (alunoRecebido != null)
        {
            alunoRecebido.setNome(nome);
            alunoRecebido.setIdade(idade);
            alunoRecebido.setNota1(nota1);
            alunoRecebido.setNota2(nota2);
            repository.atualizar(alunoRecebido);
            Toast.makeText(this, "Cadastro atualizado", Toast.LENGTH_SHORT).show();
        } else
        {
            repository.inserir(new Aluno(nome, idade, nota1, nota2));
            Toast.makeText(this, "Aluno cadastrado", Toast.LENGTH_SHORT).show();
        }
        voltarParaMain();
    }


    private void voltarParaMain()
    {
        startActivity(new Intent(CadastroAluno.this, MainActivity.class));
    }

    private void preencherDados(Aluno aluno)
    {
        edtnome.setText(aluno.getNome().toString());
        edtidade.setText(String.valueOf(aluno.getIdade()).toString());
        edtnota1.setText(String.valueOf(aluno.getNota1()).toString());
        edtnota2.setText(String.valueOf(aluno.getNota2()).toString());
    }

    private void definirModoDeEdicao()
    {
        btnCadastrar.setText("Salvar");
        btnDeletar.setVisibility(View.VISIBLE);
    }

    private void deletar()
    {
        repository.excluir(alunoRecebido.getId());
        Toast.makeText(this, "Aluno excluído", Toast.LENGTH_SHORT).show();
        voltarParaMain();
    }
}