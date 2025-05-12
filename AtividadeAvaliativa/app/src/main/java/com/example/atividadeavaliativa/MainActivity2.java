package com.example.atividadeavaliativa;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    RadioButton dolar, euro, libra;
    EditText name, wage;
    TextView result;
    Button back;
    String savedName;
    Double savedWage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dolar = findViewById(R.id.dolar);
        euro = findViewById(R.id.euro);
        libra = findViewById(R.id.libra);
        name = findViewById(R.id.editTextName);
        wage = findViewById(R.id.editTextSalario);
        result = findViewById(R.id.convertMessage);
        back = findViewById(R.id.backButton);

        dolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savedName = name.getText().toString();
                    savedWage = Double.parseDouble(wage.getText().toString());
                    String convertedWage = String.format("%.2f", savedWage * 5.0642);

                    result.setText( "\n" + "Prezado(a), " + savedName + "\n" + "Se você tiver um salário de $" + savedWage + "\n" + "Você irá receber R$" + convertedWage +
                            " reais por mês." );

                    euro.setChecked(false);
                    libra.setChecked(false);
                }catch (Exception e){
                    dolar.setChecked(false);
                    euro.setChecked(false);
                    libra.setChecked(false);
                    Toast.makeText(MainActivity2.this, "Não pode realizar a conversão sem dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savedName = name.getText().toString();
                    savedWage = Double.parseDouble(wage.getText().toString());
                    String convertedWage = String.format("%.2f", savedWage * 5.3087);

                    result.setText( "\n" + "Prezado(a), " + savedName + "\n" + "Se você tiver um salário de €" + savedWage + "\n" + "Você irá receber R$" + convertedWage +
                            " reais por mês." );

                    dolar.setChecked(false);
                    libra.setChecked(false);
                }catch (Exception n){
                    dolar.setChecked(false);
                    euro.setChecked(false);
                    libra.setChecked(false);
                    Toast.makeText(MainActivity2.this, "Não pode realizar a conversão sem dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        libra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savedName = name.getText().toString();
                    savedWage = Double.parseDouble(wage.getText().toString());
                    String convertedWage = String.format("%.2f", savedWage * 6.1137);

                    result.setText( "\n" + "Prezado(a), " + savedName + "\n" + "Se você tiver um salário de £" + savedWage + "\n" + "Você irá receber R$" + convertedWage +
                            " reais por mês." );

                    dolar.setChecked(false);
                    euro.setChecked(false);
                }catch (Exception p){
                    dolar.setChecked(false);
                    euro.setChecked(false);
                    libra.setChecked(false);
                    Toast.makeText(MainActivity2.this, "Não pode realizar a conversão sem dados!", Toast.LENGTH_SHORT).show();
                }
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
