package com.example.enviardadosporemailougoole;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    Button google, email, back;
    TextView result;
    String nomeRecebido;
    Integer idadeRecebida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        google = findViewById(R.id.buttonGoogle);
        email = findViewById(R.id.buttonEmail);
        back = findViewById(R.id.buttonBack);
        result = findViewById(R.id.receivedResults);

        Bundle dados = getIntent().getExtras();
        if (dados != null){
            nomeRecebido = dados.getString("nome");
            idadeRecebida = dados.getInt("idade");

            result.setText("Nome: " + nomeRecebido + "\n"+ "Idade: " + idadeRecebida + " anos.");
        }else{
            result.setText("Nenhum dado recebido!");
            Toast.makeText(this, "Nenhum dado recebido", Toast.LENGTH_SHORT).show();
        }

        google.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent abrirNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.br"));
                startActivity(abrirNavegador);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "example@gmail.com", "null"));
                abrirEmail.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
                abrirEmail.putExtra(Intent.EXTRA_TEXT, "BODY");
                startActivity(Intent.createChooser(abrirEmail, "Enviar E-mail"));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}