package com.br.CadPessoa;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
 
public class Pessoa {
 
    public static String[] colunas = new String[] { Pessoas._ID, Pessoas.NOME, Pessoas.CPF, Pessoas.IDADE };
 
    public static final String AUTHORITY = "com.br.CadPessoa.provider.pessoa";
     
    public long id;
    public String nome;
    public String cpf;
    public int idade;
 
    public Pessoa() {
         
    }
 
    public Pessoa(String nome, String cpf, int idade) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }
 
    public Pessoa(long id, String nome, String cpf, int idade) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }
 
    /**
     * Classe interna para representar as colunas e ser utilizada por um Content
     * Provider
     * 
     * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
     * Android
     */
    public static final class Pessoas implements BaseColumns {
     
        // Não pode instanciar esta Classe
        private Pessoas() {
        }
     
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pessoas");
     
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.pessoas";
     
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.pessoas";
     
        public static final String DEFAULT_SORT_ORDER = "_id ASC";
     
        public static final String NOME = "nome";
        public static final String CPF = "cpf";
        public static final String IDADE = "idade";
     
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /pessoas
            Uri uriPessoas = ContentUris.withAppendedId(Pessoas.CONTENT_URI, id);
            return uriPessoas;
        }
    }
 
    @Override
    public String toString() {
        return "Nome: " + nome + ", cpf: " + cpf + ", Idade: " + idade;
    }
 
     
}