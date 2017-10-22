package com.example.administrator.bancodedados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityProduto extends AppCompatActivity {

    private ProdutoDAO produtoHelper;
    private EditText edtNome, edtPreco, edtQuantidade;
    String operacao;
    int idProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Intent intent = getIntent();
        operacao = intent.getStringExtra("operacao");
        idProduto = intent.getIntExtra("id",-1);
        produtoHelper = new ProdutoDAO(this);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtPreco = (EditText ) findViewById(R.id.edtPreco);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (operacao.equalsIgnoreCase("alterar")){
            Produto produto = produtoHelper.getProdutoByID(idProduto);


            edtNome.setText(produto.getNome());
            edtPreco.setText(String.valueOf(produto.getPreco()));
            edtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        }
        else if(operacao.equalsIgnoreCase("detalhe")){

            Button button = (Button) findViewById(R.id.btnSalvar);

            Produto produto = produtoHelper.getProdutoByID(idProduto);

            edtNome.setText(produto.getNome());
            edtNome.setFocusable(false);
            edtPreco.setText(String.valueOf(produto.getPreco()));
            edtPreco.setFocusable(false);
            edtQuantidade.setText(String.valueOf(produto.getQuantidade()));
            edtQuantidade.setFocusable(false);

            button.setText("Voltar");

        }


    }


    public void onClick(View view){

        if (operacao.equalsIgnoreCase("alterar")){

            Produto produto = new Produto();
            produto.setNome(edtNome.getText().toString());
            produto.setPreco(Float.parseFloat(edtPreco.getText().toString()));
            produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
            produto.setId(idProduto);
            produtoHelper.alterarProduto(produto);
            this.finish();
        }
        else if (operacao.equalsIgnoreCase("detalhe")){
            this.finish();
        }
        else{

            try{
                EditText edtNome = (EditText) findViewById(R.id.edtNome);
                EditText edtPreco = (EditText ) findViewById(R.id.edtPreco);
                EditText edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
                Produto produto = new Produto();
                produto.setNome(edtNome.getText().toString());
                produto.setPreco(Float.parseFloat(edtPreco.getText().toString()));
                produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
                produtoHelper.inserirProduto(produto);

                Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                this.finish();
            }catch (Exception e){
                e.getStackTrace();
            }

        }

    }
}