package com.example.palheiro.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListaComprasBDHelper extends SQLiteOpenHelper
{
    //nome da base de dados
    private static final String DB_NAME = "dbListaCompras";
    //nome da tabela
    private static final String TABLE_NAME = "listasCompras";
    //nome dos campos da tabela
    private static final String ID="id", TITULO ="titulo", DESCRICAO = "descricao";
    private final SQLiteDatabase db;

    public ListaComprasBDHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String sqlTabelaListaCompras = "CREATE TABLE "+TABLE_NAME+" ( "+ ID +" INTEGER PRIMARY KEY, "+
                                TITULO+" TEXT NOT NULL, "+
                                DESCRICAO+" TEXT NOT NULL );";
        sqLiteDatabase.execSQL(sqlTabelaListaCompras);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String sqlDelTableListaCompras = "DROP TABLE IF EXISTS "+TABLE_NAME;

        sqLiteDatabase.execSQL(sqlDelTableListaCompras);

        this.onCreate(sqLiteDatabase);
    }


    //Metodos CRUD

    public ArrayList<ListaCompras> getAllListasComprasBD()
    {
        ArrayList<ListaCompras> listaCompras = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME,new String[]{ID, TITULO, DESCRICAO}, null,null, null, null, null);

        if(cursor.moveToFirst())
        {
            do
            {
                ListaCompras auxListaCompras = new ListaCompras(cursor.getInt(0), cursor.getString(1), cursor.getString(2));

                listaCompras.add(auxListaCompras);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return listaCompras;
    }


    public ListaCompras addListaComprasBD(ListaCompras listaCompras)
    {

        ContentValues values = new ContentValues();

        values.put(ID, listaCompras.getId());
        values.put(TITULO, listaCompras.getTitulo());
        values.put(DESCRICAO, listaCompras.getDescricao());

        long id = (int) this.db.insert(TABLE_NAME, null, values);

        //se o id for = -1 entao nao foi inserido nenhum registo

        if(id != -1)
        {
            listaCompras.setId((int) id);
            return listaCompras;
        }

        return null;
    }


    public boolean updateListaComprasBD(ListaCompras listaCompras)
    {
        ContentValues values = new ContentValues();

        values.put(TITULO, listaCompras.getTitulo());
        values.put(DESCRICAO, listaCompras.getDescricao());

        int numLinhasEdit = this.db.update(TABLE_NAME,values,ID+"=?",new String[]{listaCompras.getId()+""});

        return numLinhasEdit == 1;
    }


    public boolean deleteListaComprasBD(int id)
    {
        int numLinhaDel = this.db.delete(TABLE_NAME,ID+"=?", new String[]{id+""});

        return numLinhaDel == 1;
    }

    public void deleteAllListasComprasBD()
    {
        this.db.delete(TABLE_NAME, null, null);
    }

}







