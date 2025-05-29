package com.example.appdesafio;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Button busca, exibir, inserir, excluir, excluirValor, pesquisa, atualizar;
    EditText buscaCep, idParaDeletar, valorPesquisado, idSeraAtualizado,bairro, uf;
    TextView resultadoBusca, exibeBanco;

    private JSONObject ultimoCepJson; // Variável global para guardar o último JSON de busca retornado pela API.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busca = findViewById(R.id.botaoBuscar);
        exibir = findViewById(R.id.botaoExibir);
        inserir = findViewById(R.id.botaoInserir);
        excluir = findViewById(R.id.botaoExcluir);
        atualizar = findViewById(R.id.botaoAtualizar);
        excluirValor = findViewById(R.id.excluirValor);


        buscaCep = findViewById(R.id.busca);
        idParaDeletar = findViewById(R.id.IdSeraDeletado);
        idSeraAtualizado = findViewById(R.id.idSeraAtualizado);
        bairro = findViewById(R.id.novoBairro);
        uf = findViewById(R.id.novoUf);

        resultadoBusca = findViewById(R.id.resutadoBusca);
        exibeBanco = findViewById(R.id.resultadoBanco);

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                String cep = buscaCep.getText().toString();
                String URLCep = "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(URLCep);
            }
        });

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimoCepJson != null) { // <- valida se a variavel esta vazia antes de começar a inserir os dadoss.
                    try {
//                      utilizaçao do variável global que armazena a ultima busco
                        String cep = ultimoCepJson.getString("cep");
                        String uf = ultimoCepJson.getString("uf");
                        String logradouro = ultimoCepJson.getString("logradouro");
                        String bairro = ultimoCepJson.getString("bairro");

//                      criaçao da tabela.
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
                        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cep (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "cep VARCHAR(11), " +
                                "uf VARCHAR(2), " +
                                "logradouro VARCHAR(35), " +
                                "bairro VARCHAR(35))");

//                      inserçao dos dados.
                        bancoDados.execSQL("INSERT INTO cep (cep, uf, logradouro, bairro) VALUES (" +
                                "'" + cep + "', '" + uf + "', '" + logradouro + "', '" + bairro + "')");

                        resultadoBusca.setText(" Inserido na tabela :) ");

                        Log.i("Banco de Dados ", "CEP inserido com sucesso.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Erro", "Nenhum dado de CEP carregado ainda.");
                }
            }
        });

        exibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                    String consulta = "SELECT * FROM cep";
                    Cursor cursor = bancoDados.rawQuery(consulta, null);

                    // Indice da tabela
                    int indiceId = cursor.getColumnIndex("id");
                    int indiceCep = cursor.getColumnIndex("cep");
                    int indiceUf = cursor.getColumnIndex("uf");
                    int indiceLogradouro = cursor.getColumnIndex("logradouro");
                    int indiceBairro = cursor.getColumnIndex("bairro");

                    String resultadoFinal = ""; // <- variavel que armazena os resultados do loop para exibir na TextView.

                    if (cursor.moveToFirst()) { // <- verifica se existe pelo menos um resultado no inidice para iniciar a funçao.
                        do {
                            String id = cursor.getString(indiceId);
                            String cep = cursor.getString(indiceCep);
                            String uf = cursor.getString(indiceUf);
                            String logradouro = cursor.getString(indiceLogradouro);
                            String bairro = cursor.getString(indiceBairro);

                            String resultado = "ID: " + id + " | CEP: " + cep + " | UF: " + uf + " | Logradouro: " + logradouro + " | Bairro: " + bairro + "\n";
                            resultadoFinal += resultado;
                        } while (cursor.moveToNext());
                    }
                    exibeBanco.setText(resultadoFinal); // <- exibe o resultado final na TextView.
                    Log.i("Banco de Dados ", resultadoFinal);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Não há nada para mostrar :( ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int idParaAtualizar = Integer.parseInt(idSeraAtualizado.getEditableText().toString());
                    String bairroAtualizado = bairro.getEditableText().toString();
                    String ufAtualizada = uf.getEditableText().toString();

                    SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
                    bancoDados.execSQL("UPDATE cep SET bairro = '" + bairroAtualizado + "', uf = '" + ufAtualizada + "' WHERE id = " + idParaAtualizar);

                    // Mostrar os dados atualizados
                    String consulta = "SELECT * FROM cep";
                    Cursor cursor = bancoDados.rawQuery(consulta, null);

                    // Indice da tasbela
                    int indiceId = cursor.getColumnIndex("id");
                    int indiceCep = cursor.getColumnIndex("cep");
                    int indiceUf = cursor.getColumnIndex("uf");
                    int indiceLogradouro = cursor.getColumnIndex("logradouro");
                    int indiceBairro = cursor.getColumnIndex("bairro");

                    String resultadoFinal = "";

                    if (cursor.moveToFirst()) {
                        do {
                            String id = cursor.getString(indiceId);
                            String cep = cursor.getString(indiceCep);
                            String uf = cursor.getString(indiceUf);
                            String logradouro = cursor.getString(indiceLogradouro);
                            String bairro = cursor.getString(indiceBairro);

                            String resultado = "ID: " + id + " | CEP: " + cep + " | UF: " + uf + " | Logradouro: " + logradouro + " | Bairro: " + bairro + "\n";
                            resultadoFinal += resultado;
                        } while (cursor.moveToNext());
                    }
                    exibeBanco.setText(resultadoFinal);
                    Log.i("Banco de Dados", resultadoFinal);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ID já atualizado ou não existe! ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        excluirValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSeraDeletado = Integer.parseInt(idParaDeletar.getEditableText().toString());
                String resultadoFinal = "";
                try {
                    SQLiteDatabase bancoDados  = openOrCreateDatabase("app", MODE_PRIVATE, null);

                    bancoDados.execSQL("DELETE FROM cep WHERE id = '" + idSeraDeletado + "'");

                    String consulta = "SELECT * FROM cep";
                    Cursor cursor = bancoDados.rawQuery(consulta, null);

                    int indiceId = cursor.getColumnIndex("id");
                    int indiceCep = cursor.getColumnIndex("cep");
                    int indiceUf = cursor.getColumnIndex("uf");
                    int indiceLogradouro = cursor.getColumnIndex("logradouro");
                    int indiceBairro = cursor.getColumnIndex("bairro");

                    if (cursor.moveToFirst()) { // <- verifica se existe pelo menos um resultado no inidice para iniciar a funçao.
                        do {
                            String id = cursor.getString(indiceId);
                            String cep = cursor.getString(indiceCep);
                            String uf = cursor.getString(indiceUf);
                            String logradouro = cursor.getString(indiceLogradouro);
                            String bairro = cursor.getString(indiceBairro);

                            String resultado = "ID: " + id + " | CEP: " + cep + " | UF: " + uf + " | Logradouro: " + logradouro + " | Bairro: " + bairro + "\n";
                            resultadoFinal += resultado;
                        } while (cursor.moveToNext());
                    }
                    exibeBanco.setText(resultadoFinal); // <- exibe o resultado final na TextView.
                    Log.i("Banco de Dados ", resultadoFinal);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Item já foi deletado ou não existe!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                    bancoDados.execSQL("DROP TABLE cep");
                    exibeBanco.setText("Tabela deletada com sucesso!");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Tabela já foi deletada ou não existe!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuilder buffer = new StringBuilder();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                inputStream = conexao.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

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

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                ultimoCepJson = jsonObject; // <- variavel global para armazenar o resultado de buscas de CEP.

                String cep = jsonObject.getString("cep");
                String uf = jsonObject.getString("uf");
                String logradouro = jsonObject.getString("logradouro");
                String bairro = jsonObject.getString("bairro");
                String localidade = jsonObject.getString("localidade");

                resultadoBusca.setText("Resultado do CEP informado: " + "\nRua: " + logradouro + "\n" + "Bairro: " + bairro + "\n" + "Cidade: " + localidade + "\n" + "Estado: " + uf + "\n" + "CEP: " + cep);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
