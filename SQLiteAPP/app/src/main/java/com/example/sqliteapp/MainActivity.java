package com.example.sqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    EditText idade;
    Button botaoInserir;

    EditText pesquisar;
    Button botaoPesquisar;

    EditText idAtualizar;
    EditText novoNome;
    EditText novaIdade;
    Button botaoAtualizar;

    EditText idDeletar;
    Button botaoDeletar;
    Button botaoVer;

    TextView verTabela;
    Button droparTabela;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            nome = findViewById(R.id.insertName);
            idade = findViewById(R.id.insertAge);
            botaoInserir = findViewById(R.id.insertButton);

            pesquisar = findViewById(R.id.searchField);
            botaoPesquisar = findViewById(R.id.findButton);

            idAtualizar = findViewById(R.id.updateId);
            novoNome = findViewById(R.id.newNameField);
            novaIdade = findViewById(R.id.newAgeField);
            botaoAtualizar = findViewById(R.id.updateButton);

            idDeletar = findViewById(R.id.idDeletar);
            botaoDeletar = findViewById(R.id.botaoDeletar);

            botaoVer = findViewById(R.id.viewButton);
            verTabela = findViewById(R.id.viewTable);
            droparTabela = findViewById(R.id.dropTable);


            botaoInserir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nomeInserido = nome.getEditableText().toString();
                    int idadeInserida = Integer.parseInt(idade.getEditableText().toString());

                    try {
                        //criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                        //criar tabela / remover tabale
                        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tabela " +
                                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3))");
                        //bancoDados.execSQL("DROP TABLE pessoas");

                        //inserindo registros

                        bancoDados.execSQL("INSERT INTO tabela(nome, idade) " +
                                "VALUES('" + nomeInserido + "', '" + idadeInserida + "')");

                        //mostrar o dados
                        String consulta = "SELECT * FROM tabela";
                        Cursor cursor = bancoDados.rawQuery(consulta, null);

                        //indice da tabela
                        int indiceId = cursor.getColumnIndex("id");
                        int indiceNome = cursor.getColumnIndex("nome");
                        int indiceIdade = cursor.getColumnIndex("idade");

//                       visualizar dados
                        String resultado = "";
                        if (cursor.moveToFirst()){
                            do {
                                String id = cursor.getString(indiceId);
                                String nome = cursor.getString(indiceNome);
                                String idade = cursor.getString(indiceIdade);

                            resultado += "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + "\n";

                            } while (cursor.moveToNext());
                        }

                        verTabela.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Nao foi possivel inserir dados", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fim do botao inserir

            botaoPesquisar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String valorPesquisado = pesquisar.getEditableText().toString();

                    try {

                        //criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                        //filtrar/recuperar o dados

                        String consulta = "SELECT * FROM tabela WHERE nome LIKE '%" + valorPesquisado + "%'";
                        Cursor cursor = bancoDados.rawQuery(consulta, null);


                        //indice da tabela
                        int indiceId = cursor.getColumnIndex("id");
                        int indiceNome = cursor.getColumnIndex("nome");
                        int indiceIdade = cursor.getColumnIndex("idade");

//                       visualizar dados
                        String resultado = "";
                        if (cursor.moveToFirst()){
                            do {
                                String id = cursor.getString(indiceId);
                                String nome = cursor.getString(indiceNome);
                                String idade = cursor.getString(indiceIdade);

                                resultado += "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + "\n";

                            } while (cursor.moveToNext());
                        }

                        verTabela.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Dados nao encontrados", Toast.LENGTH_SHORT).show();
                    }

                }
            }); // fim do botao pesquisar

            botaoAtualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int idSeraAtualizado = Integer.parseInt(idAtualizar.getEditableText().toString());
                    String nomeAtualizado = novoNome.getEditableText().toString();
                    int idadeAtualizada = Integer.parseInt(novaIdade.getEditableText().toString());

                    try {

                        //criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                        //atualizar registro
                        bancoDados.execSQL("UPDATE tabela SET nome = '" + nomeAtualizado + "', idade = " + idadeAtualizada + " WHERE id = '" + idSeraAtualizado + "'");

                        //mostrar o dados
                        String consulta = "SELECT * FROM tabela";
                        Cursor cursor = bancoDados.rawQuery(consulta, null);

                        //indice da tabela
                        int indiceId = cursor.getColumnIndex("id");
                        int indiceNome = cursor.getColumnIndex("nome");
                        int indiceIdade = cursor.getColumnIndex("idade");

//                       visualizar dados
                        String resultado = "";
                        if (cursor.moveToFirst()){
                            do {
                                String id = cursor.getString(indiceId);
                                String nome = cursor.getString(indiceNome);
                                String idade = cursor.getString(indiceIdade);

                                resultado += "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + "\n";

                            } while (cursor.moveToNext());
                        }

                        verTabela.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Nao foi possivel deletar", Toast.LENGTH_SHORT).show();
                    }
                }
            });// fim do botao atualizar

            botaoDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int idSeraDeletado = Integer.parseInt(idDeletar.getEditableText().toString());

                    try {

                        //criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                        //deletar item
                        bancoDados.execSQL("DELETE FROM tabela WHERE id = '" + idSeraDeletado + "'");

                        //mostrar o dados
                        String consulta = "SELECT * FROM pessoas";
                        Cursor cursor = bancoDados.rawQuery(consulta, null);

                        //indice da tabela
                        int indiceId = cursor.getColumnIndex("id");
                        int indiceNome = cursor.getColumnIndex("nome");
                        int indiceIdade = cursor.getColumnIndex("idade");

//                       visualizar dados
                        String resultado = "";
                        if (cursor.moveToFirst()){
                            do {
                                String id = cursor.getString(indiceId);
                                String nome = cursor.getString(indiceNome);
                                String idade = cursor.getString(indiceIdade);

                                resultado += "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + "\n";

                            } while (cursor.moveToNext());
                        }

                        verTabela.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Nenhum dado praa deletar", Toast.LENGTH_SHORT).show();
                    }

                }
            });// fim do botao deletar

            botaoVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

                        //mostrar o dados
                        String consulta = "SELECT * FROM tabel";
                        Cursor cursor = bancoDados.rawQuery(consulta, null);

                        //indice da tabela
                        int indiceId = cursor.getColumnIndex("id");
                        int indiceNome = cursor.getColumnIndex("nome");
                        int indiceIdade = cursor.getColumnIndex("idade");

//                       visualizar dados
                        String resultado = "";
                        if (cursor.moveToFirst()){
                            do {
                                String id = cursor.getString(indiceId);
                                String nome = cursor.getString(indiceNome);
                                String idade = cursor.getString(indiceIdade);

                                resultado += "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + "\n";

                            } while (cursor.moveToNext());
                        }

                        verTabela.setText(resultado);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Nada para visualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            droparTabela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//                        criar/abrir banco dados
                        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
//                       deletar tabela
                        bancoDados.execSQL("DROP TABLE IF EXISTS tabela");

//                       limpar visualizaçao
                        verTabela.setText("Tabela deletada com sucesso.");
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Impossível deletar tabela", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }