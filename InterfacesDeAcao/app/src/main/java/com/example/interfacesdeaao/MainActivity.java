package com.example.interfacesdeaao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button clear, reset, save;
    TextView toggleResult, switchSurprise, recievedData;
    EditText name, age;
    CheckBox soberano, mane;
    ToggleButton toggleButton;
    Switch surprise;
    RadioGroup radioGroupSexo;
    RadioButton radioButtonSexo;
    String savedName;
    Integer savedAge;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameField);
        age = findViewById(R.id.ageField);
        clear = findViewById(R.id.clearButton);
        reset = findViewById(R.id.resetButton);
        save = findViewById(R.id.saveButton);
        toggleResult = findViewById(R.id.toggleResults);
        surprise = findViewById(R.id.surprise);
        recievedData = findViewById(R.id.resultsSaved);
        soberano = findViewById(R.id.tricolorSoberano);
        mane = findViewById(R.id.rivalMane);
        switchSurprise = findViewById(R.id.supriseResult);
        toggleButton = findViewById(R.id.toggleButton);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);


//      funçao para receber os dados base (nome, idade, sexo e time)
        save.setOnClickListener(view -> {
//                  bloco de manipulaçao de nome e idade
                savedName = name.getText().toString();
                savedAge = Integer.parseInt(age.getText().toString());
                String nomeIdade = savedName + ", " + savedAge + " anos";

//                  bloco de manipulaçao para seleçao de time
                String time = "";

                if (soberano.isChecked()) {
                    String timeSelecionado = soberano.getText().toString();
                    time = " Torce para o " + timeSelecionado;
                }
                if (mane.isChecked()) {
                    String timeSelecionado = mane.getText().toString();
                    time = " É um " + timeSelecionado;
                }

//                  bloco de manipulaçao para seleçao de sexo
                String sexo = "";

                int radioButtonSelected = radioGroupSexo.getCheckedRadioButtonId();
                if (radioButtonSelected != -1){
                    radioButtonSexo = findViewById(radioButtonSelected);
                    String sexoSelecionado = radioButtonSexo.getText().toString();
                    sexo = "Sexo: " + sexoSelecionado;
                }else {
                    Toast.makeText(MainActivity.this, "Sexo de ser selecionado", Toast.LENGTH_SHORT).show();
                }
                recievedData.setText(nomeIdade + "\n" + sexo + "\n" + time + "\n");
        });

//      funçao de limpar campos
        clear.setOnClickListener(view -> {
            name.setText("");
            age.setText("");
            soberano.setChecked(false);
            mane.setChecked(false);
            radioButtonSexo.setChecked(false);
            recievedData.setText("");
        });

//      funçao para utilizaçao do Switch
        surprise.setOnClickListener(view -> {
            if (surprise.isChecked()){
                switchSurprise.setText("Palmeiras nao tem mundial! Corinthians o menor do estado!");
            }else{
                switchSurprise.setText("Tem Supresa ಠﭛಠ");
            }
        });

//      funçao para manipular Togggle Button
        toggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (toggleButton.isChecked()){
                toggleResult.setText("Esqueci o Santos...mas quem liga?");
            }else {
                toggleResult.setText("Resultados Toggle");
            }
        });
    }
}