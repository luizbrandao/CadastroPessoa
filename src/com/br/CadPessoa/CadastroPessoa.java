package com.br.CadPessoa;

import java.util.List;

import com.br.CadPessoa.Pessoa.Pessoas;
 
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
 
public class CadastroPessoa extends ListActivity {
    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;
 
    public static RepositorioPessoa repositorio;
 
    private List<Pessoa> pessoas;
 
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        repositorio = new RepositorioPessoaScript(this);
        atualizarLista();
    }
 
    protected void atualizarLista() {
        // Pega a lista de pessoas e exibe na tela
        pessoas = repositorio.listarPessoas();
 
        // Adaptador de lista customizado para cada linha de uma pessoa
        setListAdapter(new PessoaListAdapter(this, pessoas));
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
        menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
        return true;
    }
 
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // Clicou no menu
        switch (item.getItemId()) {
        case INSERIR_EDITAR:
            // Abre a tela com o formulário para adicionar
            startActivityForResult(new Intent(this, EditarPessoa.class), INSERIR_EDITAR);
            break;
        case BUSCAR:
            // Abre a tela para buscar a pessoa pelo nome
            startActivity(new Intent(this, BuscarPessoa.class));
            break;
        }
        return true;
    }
 
    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        editarPessoa(posicao);
    }
 
    // Recupera o id da pessoa, e abre a tela de edição
    protected void editarPessoa(int posicao) {
        // Usuário clicou em alguma pessoa da lista
        // Recupera a pessoa selecionado
        Pessoa pessoa = pessoas.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, EditarPessoa.class);
        // Passa o id da pessoa como parâmetro
        it.putExtra(Pessoas._ID, pessoa.id);
        // Abre a tela de edição
        startActivityForResult(it, INSERIR_EDITAR);
    }
 
    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
 
        // Quando a activity EditarPessoa retornar, seja se foi para adicionar vamos atualizar a lista
        if (codigoRetorno == RESULT_OK) {
            // atualiza a lista na tela
            atualizarLista();
        }
    }
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
 
        // Fecha o banco
        repositorio.fechar();
    }
 
}
