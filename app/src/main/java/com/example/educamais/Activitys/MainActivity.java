package com.example.educamais.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educamais.Config.ConfiguracaoFirebase;
import com.example.educamais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextSenha;
    CheckBox checkBoxSenha;
    Button btnEntrar;
    TextView textRegistre;
    ProgressDialog progressDialog;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        // Configuração Firebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        checkBoxSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editTextSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        textRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registreSe = new Intent(getApplicationContext(), CadastroActivity.class);
                Toast.makeText(MainActivity.this,"Registre - se", Toast.LENGTH_SHORT).show();
                startActivity(registreSe);
            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();

                // Inicializar progress
                progressDialog = new ProgressDialog(MainActivity.this);
                // Show dialog
                progressDialog.show();
                // Set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );



                if ( !email.isEmpty() ) {
                    if ( !senha.isEmpty() ) {
                        autenticacao.signInWithEmailAndPassword( email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if ( task.isSuccessful() ) {
                                    exibirMensagemErro("Obrigado por voltar");

                                    startActivity(new Intent(getApplicationContext(),AdicionarNotasAlunos.class));
                                } else {
                                    exibirMensagemErro("Erro ao fazer login, usuário ou senha inválido");
                                }
                            }
                        });
                    } else {
                        exibirMensagemErro("Preencha a senha !");
                    }
                } else {
                    exibirMensagemErro("Prencha o e-mail");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();


    }

    public void inicializarComponentes () {

        editTextEmail = findViewById(R.id.editEmail);
        editTextSenha = findViewById(R.id.editSenha);
        checkBoxSenha = findViewById(R.id.checkPassword);
        btnEntrar = findViewById(R.id.btnEntrar);
        textRegistre = findViewById(R.id.textRegistre);
    }
    private void exibirMensagemErro (String mensagem ) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}





