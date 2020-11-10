package com.example.educamais.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.educamais.Config.ConfiguracaoFirebase;
import com.example.educamais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    EditText campoNome, campoEmail, campoSenha;
    Button btnCadastrar;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        // Configuração Firebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!nome.isEmpty()) {
                    if (!email.isEmpty()){
                        if (!senha.isEmpty()){


                autenticacao.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            exibirMensagemErro("Obrigado por se cadastrar");

                            startActivity(new Intent(getApplicationContext(),AdicionarNotasAlunos.class));

                        } else {
                            String erroExcecao = "";

                            try {
                                throw task.getException();
                            } catch ( FirebaseAuthWeakPasswordException e) {
                                erroExcecao = "Digite uma senha mais forte";
                            } catch ( FirebaseAuthInvalidCredentialsException e) {
                                erroExcecao = "Digite uma e-mail válido";
                            } catch ( FirebaseAuthUserCollisionException e) {
                                erroExcecao = "Conta já cadastrada";
                            } catch ( Exception e) {
                                erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            exibirMensagemErro("Erro: " + erroExcecao);
                        }
                    }
                });
                        } else {exibirMensagemErro("Preencha a Senha");}
                    } else {exibirMensagemErro("Preencha o E-mail");}
                } else {exibirMensagemErro("Preencha o Nome");}

            }
        });
    }

    public void inicializarComponentes () {
        campoNome = findViewById(R.id.campoNome);
        campoEmail = findViewById(R.id.campoEmail);
        campoSenha = findViewById(R.id.campoSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }
    private void exibirMensagemErro (String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
