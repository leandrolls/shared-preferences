package com.example.exerccio2_agendadeanotaes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private String NomeArquivo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.NomeArquivo = getApplicationContext().getFilesDir().getPath() + "/";

            final EditText Campoparaescrever = findViewById(R.id.Campoparaescrever);
            final EditText NomeDoArquivo = findViewById(R.id.txtNomeArquivo);
            final Button LimparNota = findViewById(R.id.LimparNota);
            final Button SalvarNota = findViewById(R.id.SalvarNota);
            final Button ConsultarNota = findViewById(R.id.ConsultarNota);

        LimparNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Campoparaescrever.setText("");
            }
        });

        SalvarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NomeDoArquivo.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Título da Anotação Vazio", Toast.LENGTH_SHORT).show();
                }
                    else{
                        String completeFN  = NomeArquivo + NomeDoArquivo.getText().toString();
                        String content = Campoparaescrever.getText().toString();
                        MainActivity.this.gravaDadosArquivo(completeFN,content);
                }
            }
        });

        ConsultarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NomeDoArquivo.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Anotação Vazia", Toast.LENGTH_SHORT).show();
                }
                    else{
                        String completeFN  = NomeArquivo + NomeDoArquivo.getText().toString();
                        String content = recuperaDadosArquivo(completeFN);
                        Campoparaescrever.setText(content);
                }
            }
        });
    }

    public void gravaDadosArquivo(String fileName,String data){
        try{
            OutputStreamWriter bufferSaida = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
            bufferSaida.write(data);
            bufferSaida.close();
            Toast.makeText(this, "Anotação Salva com Sucesso!", Toast.LENGTH_SHORT).show();
            }
                catch(FileNotFoundException e){
            Toast.makeText(this, "Anotação não encontrada! ", Toast.LENGTH_SHORT).show();
            }
                catch (UnsupportedEncodingException e){
            Toast.makeText(this, "Falha na codificação", Toast.LENGTH_SHORT).show();
            }
                catch (IOException e){
            Toast.makeText(this, "Falha na escrita", Toast.LENGTH_SHORT).show();
        }
    }

    public String recuperaDadosArquivo(String fileName){

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linha = bufferedReader.readLine();

            while(linha != null){
                sb.append(linha);
                sb.append("\n");
                linha = bufferedReader.readLine();
            }

            Toast.makeText(this, "Consulta realizada com Sucesso!", Toast.LENGTH_SHORT).show();
            return sb.toString();

        }
                catch(FileNotFoundException e){
            Toast.makeText(this, "Falha na abertura do arquivo", Toast.LENGTH_SHORT).show();
            }
                catch (UnsupportedEncodingException e){
            Toast.makeText(this, "Falha na codificação", Toast.LENGTH_SHORT).show();
            }
                catch (IOException e){
            Toast.makeText(this, "Falha na leitura", Toast.LENGTH_SHORT).show();
            }

        return "";
    }

}