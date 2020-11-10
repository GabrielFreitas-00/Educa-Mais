package com.example.educamais.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.educamais.Config.ConfiguracaoFirebase;
import com.example.educamais.R;
import com.google.firebase.storage.StorageReference;

public class AdicionarNotasAlunos extends AppCompatActivity {

    EditText nomeAluno, turma, notaUm, notaDois, notaTres, notaQuatro;
    Button btnCadastrarNota;


    // Config StorageFirebase
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_notas_alunos);
        inicializarComponentes();

        // Config Storage
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();



        btnCadastrarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float num1 = Float.parseFloat(notaUm.getText().toString());
                float  num2 = Float.parseFloat(notaDois.getText().toString());
                float  num3 = Float.parseFloat(notaTres.getText().toString());
                float  num4 = Float.parseFloat(notaQuatro.getText().toString());

                float media = (num1 + num2 + num3 + num4 ) / 4;

                String nomeDoAluno = nomeAluno.getText().toString();
                String nomeTurma = turma.getText().toString();
                String primeiraNota = notaUm.getText().toString();
                String segundaNota = notaDois.getText().toString();
                String terceiraNota = notaTres.getText().toString();
                String quartaNota = notaQuatro.getText().toString();

                if ( !nomeDoAluno.isEmpty() ) {
                    if ( !nomeTurma.isEmpty() ){
                        if ( !primeiraNota.isEmpty() ) {
                            if ( !segundaNota.isEmpty() ){
                                if ( !terceiraNota.isEmpty() ){
                                    if ( !quartaNota.isEmpty() ){

                                    } else {
                                        exibirMensagemErro("Insira a 4ª nota");
                                    }
                                } else {
                                    exibirMensagemErro("Insira a 3ª nota");
                                }
                            } else {
                                exibirMensagemErro("Insira a 2ª nota");
                            }
                        } else {
                            exibirMensagemErro("Insira a 1ª nota");
                        }
                    } else {
                        exibirMensagemErro("Insira o nome da turma");
                    }
                } else {
                    exibirMensagemErro("Insira o nome do Aluno");
                }



                Intent insereNotas = new Intent(getApplicationContext(),ExibirMedia.class);
                insereNotas.putExtra("nome", nomeDoAluno);
                insereNotas.putExtra("media", media);
                insereNotas.putExtra("turma", nomeTurma);

                startActivity(insereNotas);
            }
        });
    }


    private void inicializarComponentes () {
        nomeAluno = findViewById(R.id.nomeAluno);
        turma = findViewById(R.id.turmaAluno);
        notaUm =  findViewById(R.id.notaUm);
        notaDois = findViewById(R.id.notaDois);
        notaTres = findViewById(R.id.notaTres);
        notaQuatro = findViewById(R.id.notaQuatro);
        btnCadastrarNota = findViewById(R.id.btnCadastrarNota);
    }
    private void exibirMensagemErro (String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
