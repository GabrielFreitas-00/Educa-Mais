package com.example.educamais.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.educamais.R;

public class ExibirMedia extends AppCompatActivity {

    TextView nota, exibirNome, exibirTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_media);

        inicializarComponentes();

        Bundle dados = getIntent().getExtras();
        String nome = dados.getString("nome");
        String turma = dados.getString("turma");
        float media = dados.getFloat("media", 0f);

        nota.setText(String.valueOf(media));
        exibirNome.setText(nome);
        exibirTurma.setText(turma);



    }
    private void inicializarComponentes () {
        nota = findViewById(R.id.nota);
        exibirNome = findViewById(R.id.exibirNome);
        exibirTurma = findViewById(R.id.exibirTurma);
    }
}
