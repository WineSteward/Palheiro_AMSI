package com.example.books.modelo;

import android.content.Context;

import java.util.ArrayList;

public class SingletonGestorLivros
{
    private ArrayList<Livro> livros;
    private static SingletonGestorLivros instance = null;
    private LivroBDHelper livrosBD = null;

    public static synchronized SingletonGestorLivros getInstance(Context context)
    {
        if(instance == null)
            instance = new SingletonGestorLivros(context);

        return instance;
    }

    private SingletonGestorLivros(Context context)
    {
        livros = new ArrayList<Livro>();
        livrosBD = new LivroBDHelper(context);
    }

    public Livro getLivro(int id)
    {
        for (Livro livro: livros)
        {
            if(livro.getId() == id)
                return livro;
        }

        return null;
    }


    public ArrayList<Livro> getLivrosBD()
    {
        livros = livrosBD.getAllLivrosBD();

        return new ArrayList<>(livros);
    }

    public void adicionarLivroBD(Livro livro)
    {

        Livro livroAux = livrosBD.addLivroBD(livro);

        if(livroAux != null)
        {
            livros.add(livro);
        }
    }

    public void editarLivroBD(Livro livro)
    {
        Livro updatedLivro = getLivro(livro.getId());

        if(updatedLivro != null)
        {
            livrosBD.updateLivroBD(updatedLivro);
        }
    }

    public void removerLivroBD(int id)
    {
        Livro livro = getLivro(id);

        if(livro != null)
        {
            if(livrosBD.deleteLivroBD(id))
                livros.remove(livro);
        }
    }
}













