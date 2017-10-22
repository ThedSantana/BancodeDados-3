package com.example.administrator.bancodedados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProdutoDAO produtoHelper;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregarListView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        carregarListView();
    }

    public void carregarListView(){
        produtoHelper = new ProdutoDAO(this);
        listView = (ListView) findViewById(R.id.listProdutos);
        List<Produto> produtos = produtoHelper.getListaProduto();

        if (produtos.size() >= 0){
            ArrayAdapter<Produto> adaptador = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_checked, produtos);
            listView.setAdapter(adaptador);
        }else{
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCLick (View view) {
        if (view.getId() == R.id.btCadastrar) {
            Intent intent = new Intent(this, ActivityProduto.class);
            intent.putExtra("operacao", "cadastrar");
            startActivity(intent);
        } else if (view.getId() == R.id.btalterar) {

            SparseBooleanArray checked = listView.getCheckedItemPositions();
            int id = 0;

            if (checked.size() == 0) {
                Toast.makeText(this, "Por Favor selecione um registro!", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < checked.size(); i++) {
                    Produto produto = (Produto) listView.getItemAtPosition(checked.keyAt(i));
                    id = produto.getId();
                }

                Intent intent = new Intent(this, ActivityProduto.class);
                intent.putExtra("operacao", "alterar");
                intent.putExtra("id", id);
                startActivity(intent);
            }
        } else {

            SparseBooleanArray checked = listView.getCheckedItemPositions();
            int id = 0;

            if (checked.size() == 0) {
                Toast.makeText(this, "Por Favor selecione um registro!", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < checked.size(); i++) {
                    Produto produto = (Produto) listView.getItemAtPosition(checked.keyAt(i));
                    id = produto.getId();
                }

                Intent intent = new Intent(this, ActivityProduto.class);
                intent.putExtra("operacao", "detalhe");
                intent.putExtra("id", id);
                startActivity(intent);
            }

        }
    }

    public void btExcluir(View view){

        String selecionados = "";

        //Cria um array com os itens selecionados no listview
        SparseBooleanArray checked = listView.getCheckedItemPositions();

        for (int i = 0; i < checked.size(); i++){
            Produto produto = (Produto) listView.getItemAtPosition(checked.keyAt(i));
            //pega os itens marcados
            produtoHelper.apagarProduto(produto);
        }
        Toast.makeText(this, "Produto Apagado com Sucesso!", Toast.LENGTH_LONG).show();
        carregarListView();
    }

}