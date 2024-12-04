package com.example.books.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LivroBDHelper extends SQLiteOpenHelper
{
    //nome da base de dados
    private static final String DB_NAME = "dblivros";
    //nome da tabela
    private static final String TABLE_NAME = "livros";
    //nome dos campos da tabela
    private static final String ID="id", TITULO ="titulo", SERIE = "serie", AUTOR = "autor", ANO = "ano", CAPA = "capa";
    private final SQLiteDatabase db;

    public LivroBDHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String sqlTabelaLivro = "CREATE TABLE "+TABLE_NAME+" ( "+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                TITULO+" TEXT NOT NULL, "+
                                SERIE+" TEXT NOT NULL, "+
                                AUTOR+" TEXT NOT NULL, "+
                                ANO+" INTEGER NOT NULL, "+
                                CAPA+" INTEGER );";
        sqLiteDatabase.execSQL(sqlTabelaLivro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String sqlDelTableLivro = "DROP TABLE IF EXISTS "+TABLE_NAME;

        sqLiteDatabase.execSQL(sqlDelTableLivro);

        this.onCreate(sqLiteDatabase);
    }


    //Metodos CRUD

    public ArrayList<Livro> getAllLivrosBD()
    {
        ArrayList<Livro> livros = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME,new String[]{ID, TITULO, SERIE, AUTOR, ANO, CAPA}, null,null, null, null, null);

        if(cursor.moveToFirst())
        {
            do
            {
                //TODO completar

                Livro auxLivro = new Livro(cursor.getInt(0), cursor.getInt(5),cursor.getInt(4), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                livros.add(auxLivro);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return livros;
    }


    public Livro addLivroBD(Livro livro)
    {

        ContentValues values = new ContentValues();

        values.put(TITULO, livro.getTitulo());
        values.put(SERIE, livro.getSerie());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, livro.getAno());
        values.put(CAPA, livro.getCapa());


        long id = (int) this.db.insert(TABLE_NAME, null, values);

        //se o id for = -1 entao nao foi inserido nenhum registo

        if(id != -1)
        {
            livro.setId((int) id);
            return livro;
        }

        return null;
    }


    public boolean updateLivroBD(Livro livro)
    {
        ContentValues values = new ContentValues();

        values.put(TITULO, livro.getTitulo());
        values.put(SERIE, livro.getSerie());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, livro.getAno());
        values.put(CAPA, livro.getCapa());


        int numLinhasEdit = this.db.update(TABLE_NAME,values,ID+"=?",new String[]{livro.getId()+""});

        return numLinhasEdit == 1;
    }


    public boolean deleteLivroBD(int id)
    {
        int numLinhasDel = this.db.delete(TABLE_NAME,ID+"=?",new String[]{id+""});

        return numLinhasDel == 1;
    }




}







