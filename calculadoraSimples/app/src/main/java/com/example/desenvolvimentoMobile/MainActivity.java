package com.example.desenvolvimentoMobile;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button soma, div, multi, sub;
    TextView result;
    EditText valorUm, valorDois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soma = findViewById(R.id.soma);
        div = findViewById(R.id.div);
        multi = findViewById(R.id.mult);
        sub = findViewById(R.id.sub);
        result = findViewById(R.id.result);
        valorDois = findViewById(R.id.valorDois);
        valorUm = findViewById(R.id.valorUm);

        soma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double valor1 = Double.parseDouble(valorUm.getText().toString());
                Double valor2 = Double.parseDouble(valorDois.getText().toString());

                Double resultado = valor1 + valor2;

                result.setText("Valor da soma: " + resultado);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double valor1 = Double.parseDouble(valorUm.getText().toString());
                Double valor2 = Double.parseDouble(valorDois.getText().toString());

                Double resultado = valor1 - valor2;

                result.setText("Valor da subtraçao: " + resultado);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double valor1 = Double.parseDouble(valorUm.getText().toString());
                Double valor2 = Double.parseDouble(valorDois.getText().toString());

                if (valor2 == 0){
                    Toast.makeText(MainActivity.this,
                            "Erro: Nao pode ser dividido por zero!!",
                            Toast.LENGTH_SHORT).show();
                }
                Double resultado = valor1 / valor2;
                result.setText("Valor da divisao: " + resultado);
            }
        });
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double valor1 = Double.parseDouble(valorUm.getText().toString());
                Double valor2 = Double.parseDouble(valorDois.getText().toString());

                Double resultado = valor1 * valor2;

                result.setText("Valor da multiplicaçao: " + resultado);
            }
        });
    }
}