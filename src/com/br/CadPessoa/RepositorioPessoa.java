package com.br.CadPessoa;

import java.util.ArrayList;
import java.util.List;
 
import com.br.CadPessoa.Pessoa.Pessoas;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
 
public class RepositorioPessoa {
 
    private static final String CATEGORIA = "dados";
 
    // Nome do banco
    private static final String NOME_BANCO = "dados_android";
    // Nome da tabela
    public static final String NOME_TABELA = "pessoa";
 
    protected SQLiteDatabase db;
 
    public RepositorioPessoa(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }
 
    protected RepositorioPessoa() {
        // Apenas para criar uma subclasse...
    }
 
    // Salva a pessoa, insere um novo ou atualiza
    public long salvar(Pessoa pessoa) {
        long id = pessoa.id;
 
        if (id != 0) {
            atualizar(pessoa);
        } else {
            // Insere novo
            id = inserir(pessoa);
        }
 
        return id;
    }
 
    // Insere uma nova pessoa
    public long inserir(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(Pessoas.NOME, pessoa.nome);
        values.put(Pessoas.CPF, pessoa.cpf);
        values.put(Pessoas.IDADE, pessoa.idade);
 
        long id = inserir(values);
        return id;
    }
 
    // Insere uma nova pessoa
    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }
 
    // Atualiza a pessoa no banco. O id da pessoa é utilizado.
    public int atualizar(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(Pessoas.NOME, pessoa.nome);
        values.put(Pessoas.CPF, pessoa.cpf);
        values.put(Pessoas.IDADE, pessoa.idade);
 
        String _id = String.valueOf(pessoa.id);
 
        String where = Pessoas._ID + "=?";
        String[] whereArgs = new String[] { _id };
 
        int count = atualizar(values, where, whereArgs);
 
        return count;
    }
 
    // Atualiza a pessoa com os valores abaixo
    // A cláusula where é utilizada para identificar a pessoa a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }
 
    // Deleta a pessoa com o id fornecido
    public int deletar(long id) {
        String where = Pessoas._ID + "=?";
 
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
 
        int count = deletar(where, whereArgs);
 
        return count;
    }
 
    // Deleta a pessoa com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }
 
    // Busca a pessoa pelo id
    public Pessoa buscarPessoa(long id) {
        // select * from pessoa where _id=?
        Cursor c = db.query(true, NOME_TABELA, Pessoa.colunas, Pessoas._ID + "=" + id, null, null, null, null, null);
 
        if (c.getCount() > 0) {
 
            // Posicinoa no primeiro elemento do cursor
            c.moveToFirst();
 
            Pessoa pessoa = new Pessoa();
 
            // Lê os dados
            pessoa.id = c.getLong(0);
            pessoa.nome = c.getString(1);
            pessoa.cpf = c.getString(2);
            pessoa.idade = c.getInt(3);
 
            return pessoa;
        }
 
        return null;
    }
 
    // Retorna um cursor com todas as pessoas
    public Cursor getCursor() {
        try {
            // select * from pessoas
            return db.query(NOME_TABELA, Pessoa.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar as pessoas: " + e.toString());
            return null;
        }
    }
 
    // Retorna uma lista com todas as pessoas
    public List<Pessoa> listarPessoas() {
        Cursor c = getCursor();
 
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
 
        if (c.moveToFirst()) {
 
            // Recupera os índices das colunas
            int idxId = c.getColumnIndex(Pessoas._ID);
            int idxNome = c.getColumnIndex(Pessoas.NOME);
            int idxCpf = c.getColumnIndex(Pessoas.CPF);
            int idxidade = c.getColumnIndex(Pessoas.IDADE);
 
            // Loop até o final
            do {
                Pessoa pessoa = new Pessoa();
                pessoas.add(pessoa);
 
                // recupera os atributos da pessoa
                pessoa.id = c.getLong(idxId);
                pessoa.nome = c.getString(idxNome);
                pessoa.cpf = c.getString(idxCpf);
                pessoa.idade = c.getInt(idxidade);
 
            } while (c.moveToNext());
        }
 
        return pessoas;
    }
 
    // Busca a pessoa pelo nome "select * from pessoa where nome=?"
    public Pessoa buscarPessoaPorNome(String nome) {
        Pessoa pessoa = null;
 
        try {
            // Idem a: SELECT _id,nome,cpf,idade from pessoa where nome = ?
            Cursor c = db.query(NOME_TABELA, Pessoa.colunas, Pessoas.NOME + "='" + nome + "'", null, null, null, null);
 
            // Se encontrou...
            if (c.moveToNext()) {
 
                pessoa = new Pessoa();
 
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                pessoa.id = c.getLong(0);
                pessoa.nome = c.getString(1);
                pessoa.cpf = c.getString(2);
                pessoa.idade = c.getInt(3);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar a pessoa pelo nome: " + e.toString());
            return null;
        }
 
        return pessoa;
    }
 
    // Busca uma pessoa utilizando as configurações definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de pessoa
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
            String groupBy, String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }
 
    // Fecha o banco
    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }
     
}
