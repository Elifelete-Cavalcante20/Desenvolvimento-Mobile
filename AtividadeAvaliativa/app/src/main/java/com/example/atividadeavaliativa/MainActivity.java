package com.example.atividadeavaliativa;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView intro;
    Button prossegButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intro = findViewById(R.id.intro);
        prossegButton = findViewById(R.id.prossegButton);

        intro.setText("\n" + "Este trabalho avaliativo, foi realizado na data de: 11/04/20025." + "\n" +
                "Integrantes: Elifelete Cavalcante e Murilo Laino" + "\n" + "Docente: Alvaro Pereira (Desenvolvimento para dispositivos móveis)." + "\n" +
                "O objetivo da atividade era de realizar um APP com duas telas onde a primeria tela deveria conter uma introduçao simples e um botão que te direcionasse para tela seguinte. " +
                "\n" + " A segunda tela por sua vez deveriam conter dois campos onde o avaliador iria inserir os valores (nome e valor de salário para conversão) e um radio group com 3 radio buttons com nomes de 3 moedas " +
                "diferentes (dólar, euro e libra)" +
                " que ao serem selecionadas fariam o conversão para a moeda local (real) e um campo com exbisse uma mensagem após a conversão da moeda ser realizada." + "\n");

//      metodo intent para fazer a troca de tela
        prossegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}