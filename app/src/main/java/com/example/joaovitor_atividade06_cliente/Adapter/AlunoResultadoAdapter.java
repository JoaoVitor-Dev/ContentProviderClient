package com.example.joaovitor_atividade06_cliente.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.R;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;
import com.example.joaovitor_atividade06_cliente.View.CadastroAluno;

import java.util.List;

public class AlunoResultadoAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private List<Aluno> alunos;
    private AlunoRepository repository;

    public AlunoResultadoAdapter(LayoutInflater inflater, List<Aluno> alunos, AlunoRepository repository)
    {
        this.inflater = inflater;
        this.alunos = alunos;
        this.repository = repository;
    }

    @Override
    public int getCount()
    {
        return alunos.size();
    }

    @Override
    public Object getItem(int i)
    {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        Aluno aluno = alunos.get(i);

        view = inflater.inflate(R.layout.item_aluno_resultado, null);

        TextView nome = view.findViewById(R.id.Nome);
        TextView media = view.findViewById(R.id.media);
        TextView status = view.findViewById(R.id.status);
        Button btnVer = view.findViewById(R.id.btnVer);

        Double _media = (aluno.getNota1() + aluno.getNota2()) / 2;

        nome.setText(aluno.getNome().toString().toUpperCase());
        media.setText(String.valueOf(_media));

        if (_media < 6){
            status.setText("Reprovado");
        }else {
            status.setText("Aprovado");
        }

        btnVer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(inflater.getContext(), CadastroAluno.class);
                intent.putExtra("aluno", aluno);
                inflater.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
