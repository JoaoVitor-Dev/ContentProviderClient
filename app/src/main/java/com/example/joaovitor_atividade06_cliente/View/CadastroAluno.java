package com.example.joaovitor_atividade06_cliente.View;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import com.example.joaovitor_atividade06_cliente.Model.Aluno;
import com.example.joaovitor_atividade06_cliente.R;
import com.example.joaovitor_atividade06_cliente.Repository.AlunoRepository;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroAluno extends AppCompatActivity {

    private Button btnCadastrar, btnVoltar;
    private TextInputEditText edtnome, edtidade, edtnota1, edtnota2;
    private AlunoRepository repository;
    private Aluno alunoRecebido;
    private final String CANAL_ID = "1";
    private static final int NOTIFICACAO_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        setup();
        ContentResolver contentResolver = getContentResolver();
        repository = new AlunoRepository(contentResolver);

        alunoRecebido = (Aluno) getIntent().getSerializableExtra("aluno");

        if (alunoRecebido != null) {
            preencherDados(alunoRecebido);
            definirModoDeVisualizacao();
        }

        criarCanalNotificacao();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarParaMain();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private void setup() {
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
        edtnome = findViewById(R.id.nome);
        edtidade = findViewById(R.id.idade);
        edtnota1 = findViewById(R.id.nota1);
        edtnota2 = findViewById(R.id.nota2);
    }

    private void criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nome = "Canal de Notificação";
            String descricao = "Canal para notificações de cadastro de aluno";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canal = new NotificationChannel(CANAL_ID, nome, importancia);
            canal.setDescription(descricao);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }

    private void enviarNotificacao(String titulo, String mensagem) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Adicione o ícone da notificação
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICACAO_ID, builder.build());
    }

    private void salvar() {
        String nome = edtnome.getText().toString().trim();
        String idadeText = edtidade.getText().toString().trim();
        String nota1Text = edtnota1.getText().toString().trim();
        String nota2Text = edtnota2.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o nome do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idadeText.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha a idade do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota1Text.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha a primeira nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota2Text.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha a segunda nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idade = Integer.parseInt(idadeText);
        Double nota1 = Double.parseDouble(nota1Text);
        Double nota2 = Double.parseDouble(nota2Text);

        repository.inserir(new Aluno(nome, idade, nota1, nota2));
        Toast.makeText(this, "Aluno cadastrado", Toast.LENGTH_SHORT).show();
        enviarNotificacao("Novo Cadastro", "Um novo aluno foi cadastrado com sucesso!");
        voltarParaMain();
    }

    private void voltarParaMain() {
        startActivity(new Intent(CadastroAluno.this, MainActivity.class));
    }

    private void preencherDados(Aluno aluno) {
        edtnome.setText(aluno.getNome().toString());
        edtidade.setText(String.valueOf(aluno.getIdade()).toString());
        edtnota1.setText(String.valueOf(aluno.getNota1()).toString());
        edtnota2.setText(String.valueOf(aluno.getNota2()).toString());
    }

    private void definirModoDeVisualizacao() {
        btnCadastrar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Permissão de notificações negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
