package com.example.joaovitor_atividade06_cliente.View.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.joaovitor_atividade06_cliente.Adapter.AlunoAdapter;
import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.R;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;
import com.example.joaovitor_atividade06_cliente.View.CadastroAluno;
import com.example.joaovitor_atividade06_cliente.View.MainActivity;

import java.util.ArrayList;


public class ListaFragment extends Fragment
{
    private Button btnNovo;
    private ListView listView;
    private AlunoRepository repository;
    private AlunoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        setup(view);

        carregarListaAlunos();

        btnNovo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), CadastroAluno.class));
            }
        });

        return view;
    }

    private void setup(View view)
    {
        btnNovo = view.findViewById(R.id.btnNovo);
        listView = view.findViewById(R.id.listaAlunos);
    }

    private void carregarListaAlunos()
    {
        ContentResolver contentResolver = getContext().getContentResolver();
        AlunoRepository alunoRepository = new AlunoRepository(contentResolver);

        ArrayList<Aluno> alunos = (ArrayList<Aluno>) alunoRepository.obterTodos();

        if (alunos.size() <= 0){
            Toast.makeText(getContext(), "Nenhum aluno cadastrado no app!", Toast.LENGTH_LONG).show();
        }

        if (alunos != null){
            adapter = new AlunoAdapter(getLayoutInflater(), alunos, repository);
            listView.setAdapter(adapter);
        }
    }
}