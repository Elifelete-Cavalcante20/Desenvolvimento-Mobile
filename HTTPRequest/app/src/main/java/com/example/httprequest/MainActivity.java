package com.example.httprequest;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText textoDigitado;
    Button botaoRecuperar;
    TextView textoResultado;
    RadioGroup radioGoupBusca;
    RadioButton radioButtonBusca;
    String busca = "";
    String buscaMoeda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoDigitado = findViewById(R.id.textoDigitado);
        botaoRecuperar = findViewById(R.id.botaoRecuperar);
        textoResultado = findViewById(R.id.textoResultado);
        radioGoupBusca = findViewById(R.id.radioGroup);

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonSelected = radioGoupBusca.getCheckedRadioButtonId();
                if (radioButtonSelected != -1) {
                    radioButtonBusca = findViewById(radioButtonSelected);
                    String buscaSelecionada = radioButtonBusca.getText().toString();
                    busca = buscaSelecionada;
                } else {
                    Toast.makeText(MainActivity.this, "Selecione o meio de busca", Toast.LENGTH_SHORT).show();
                    return;
                }

                String entradaUsuario = textoDigitado.getText().toString().trim();

                if (busca.equals("CEP")) {
                    if (entradaUsuario.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Digite um CEP válido.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String urlCep = "https://viacep.com.br/ws/" + entradaUsuario + "/json/";
                    new MyTask().execute(urlCep);
                } else if (busca.equals("Moeda")) {
                    if (entradaUsuario.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Digite o código da moeda (ex: USD, EUR).", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    buscaMoeda = entradaUsuario.toUpperCase();
                    String urlMoeda = "https://blockchain.info/ticker";
                    new MyTask().execute(urlMoeda);
                }
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            StringBuilder buffer = new StringBuilder();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conexao.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            if (busca.equals("CEP")) {
                try {
                    JSONObject jsonObject = new JSONObject(resultado);

                    if (jsonObject.has("erro")) {
                        Toast.makeText(MainActivity.this, "CEP não encontrado!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String cep = jsonObject.optString("cep");
                    String uf = jsonObject.optString("uf");
                    String logradouro = jsonObject.optString("logradouro");
                    String bairro = jsonObject.optString("bairro");
                    String localidade = jsonObject.optString("localidade");

                    textoResultado.setText("Informações encontradas no CEP:\n\n"
                            + "Logradouro: " + logradouro + "\n"
                            + "Bairro: " + bairro + "\n"
                            + "Cidade: " + localidade + "\n"
                            + "Estado: " + uf + "\n"
                            + "CEP Informado: " + cep);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Erro ao ler resposta do servidor!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else if (busca.equals("Moeda")) {
                try {
                    JSONObject jsonObject = new JSONObject(resultado);

                    if (!jsonObject.has(buscaMoeda)) {
                        Toast.makeText(MainActivity.this, "Moeda inválida ou não disponível!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONObject moedaJson = jsonObject.getJSONObject(buscaMoeda);

                    String buy = moedaJson.optString("buy");
                    String sell = moedaJson.optString("sell");
                    String symbol = moedaJson.optString("symbol");

                    textoResultado.setText("Informações encontradas sobre a moeda:\n\n"
                            + "Moeda: " + buscaMoeda + "\n"
                            + "Compra: " + buy + "\n" + "Venda: " + sell + "\n"
                            + "Sígla: " + symbol);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Erro ao processar moeda!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
}
