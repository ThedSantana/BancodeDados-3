package com.example.administrator.bancodedados;

/**
 * Created by Administrator on 22/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "produto";
    private static final String DATABASE = "AppBanco";

    public ProdutoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE IF NOT EXISTS "+ TABELA +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome TEXT, " +
                "preco REAL , " +
                "quantidade INTEGER);";
        db.execSQL(ddl);
    }

    public void inserirProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("preco", produto.getPreco());
        values.put("quantidade", produto.getQuantidade());
        getWritableDatabase().insert(TABELA, null, values);
    }
    public void apagarProduto(Produto produto){
        String args[] = {Integer.toString(produto.getId())};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public void alterarProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("preco", produto.getPreco());
        values.put("quantidade", produto.getQuantidade());
        String args[] = {Integer.toString(produto.getId())};
        getWritableDatabase().update(TABELA, values, "id=?", args);
    }

    public Cursor getCursorListaProduto(){
        List<Produto> lista = new ArrayList<Produto>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);
        return cursor;
    }

    public List<Produto> getListaProduto(){
        List<Produto> lista = new ArrayList<Produto>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("id")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            produto.setPreco(cursor.getFloat(cursor.getColumnIndex("preco")));
            produto.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            lista.add(produto);
        }
        cursor.close();
        return lista;
    }

    public Produto getProdutoByID(int id){
        Cursor cursor = getReadableDatabase().query(TABELA, new String[]{"id","nome","preco","quantidade"}, "id=" + id, null,null, null, null);
        cursor.moveToFirst();
        Produto produto = new Produto();
        produto.setId(cursor.getInt(cursor.getColumnIndex("id")));
        produto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        produto.setPreco(cursor.getFloat(cursor.getColumnIndex("preco")));
        produto.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
        cursor.close();
        return produto;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
