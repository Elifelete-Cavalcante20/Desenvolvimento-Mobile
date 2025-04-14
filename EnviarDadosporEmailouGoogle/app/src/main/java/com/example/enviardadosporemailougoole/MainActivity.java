package com.example.enviardadosporemailougoole;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button send, clear, write;
    TextView result;
    EditText placeName, placeAge;
    String nomeSalvo;
    Integer idadeSalva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.sendButton);
        clear = findViewById(R.id.clearButton);
        write = findViewById(R.id.resultButton);
        result = findViewById(R.id.result);
        placeAge = findViewById(R.id.ageField);
        placeName = findViewById(R.id.nameField);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nomeSalvo = placeName.getText().toString();
                    idadeSalva = Integer.parseInt(placeAge.getText().toString());
                    result.setText("Nome: " + nomeSalvo + "\n" + "Idade: " + idadeSalva + " anos.");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,
                            "Insira os dados!",
                            Toast.LENGTH_SHORT).show();
//                          avisa caso nao tenha nenhum valor inserido nos campos de dados.
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    placeAge.setText("");
                    placeName.setText("");
                    result.setText("");
//                  limpa dados escritos anteriormente.

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("nome", nomeSalvo);
                intent.putExtra("idade", idadeSalva);
                startActivity(intent);
            }
        });
    }
}