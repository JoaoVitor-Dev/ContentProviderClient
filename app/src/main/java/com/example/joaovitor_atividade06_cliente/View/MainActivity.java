package com.example.joaovitor_atividade06_cliente.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.joaovitor_atividade06_cliente.Adapter.AlunoAdapter;
import com.example.joaovitor_atividade06_cliente.Adapter.ViewPagerAdapter;
import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.R;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

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

        //carregarLista();

//        btnNovo.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                startActivity(new Intent(MainActivity.this, CadastroAluno.class));
//            }
//        });
    }

    private void setup()
    {
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
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
            adapter = new AlunoAdapter(getLayoutInflater(), alunos, repository);
            listView.setAdapter(adapter);
        }
    }
}