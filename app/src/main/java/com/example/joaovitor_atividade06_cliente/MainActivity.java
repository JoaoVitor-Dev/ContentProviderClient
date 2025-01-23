package com.example.joaovitor_atividade06_cliente;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.joaovitor_atividade06_cliente.Adapter.AlunoAdapter;
import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView lista;
    private Button btnNovo;
    private ListView listView;
    private AlunoRepository repository;
    private AlunoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        carregarLista();

        btnNovo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, CadastroAluno.class));
            }
        });
    }

    private void setup()
    {
        lista = findViewById(R.id.listaAlunos);
        btnNovo = findViewById(R.id.btnNovo);
        listView = findViewById(R.id.listaAlunos);
    }

    private void carregarLista()
    {
        ContentResolver contentResolver = getContentResolver();
        AlunoRepository alunoRepository = new AlunoRepository(contentResolver);

        ArrayList<Aluno> alunos = (ArrayList<Aluno>) alunoRepository.obterTodos();

        if (alunos.size() <= 0){
            Toast.makeText(this, "Nenhum aluno encontrado", Toast.LENGTH_LONG).show();
        }

        if (alunos != null){
            adapter = new AlunoAdapter(getLayoutInflater(), alunos);
            listView.setAdapter(adapter);
        }
    }
}