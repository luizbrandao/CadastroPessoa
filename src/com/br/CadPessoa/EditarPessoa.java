package com.br.CadPessoa;

import com.br.CadPessoa.Pessoa.Pessoas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
 
public class EditarPessoa extends Activity {
 
    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
 
    // Campos texto
    private EditText campoNome;
    private EditText campoCpf;
    private EditText campoIdade;
    private Long id;
 
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
 
        setContentView(R.layout.form_editar_pessoa);
 
        campoNome = (EditText) findViewById(R.id.campoNome);
        campoCpf = (EditText) findViewById(R.id.campoCpf);
        campoIdade = (EditText) findViewById(R.id.campoIdade);
 
        id = null;
 
        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Pessoas._ID);
 
            if (id != null) {
                // é uma edição, busca o pessoa...
                Pessoa p = buscarPessoa(id);
                campoNome.setText(p.nome);
                campoCpf.setText(p.cpf);
                campoIdade.setText(String.valueOf(p.idade));
            }
        }
 
        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                // Fecha a tela
                finish();
            }
        });
 
        // Listener para salvar a pessoa
        ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                salvar();
            }
        });
 
        ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir);
 
        if (id == null) {
            // Se id está nulo, não pode excluir
            btExcluir.setVisibility(View.INVISIBLE);
        } else {
            // Listener para excluir a pessoa
            btExcluir.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para não ficar nada na tela pendente
        setResult(RESULT_CANCELED);
 
        // Fecha a tela
        finish();
    }
 
    public void salvar() {
 
        int idade = 0;
        try {
            idade = Integer.parseInt(campoIdade.getText().toString());
        } catch (NumberFormatException e) {
        }
 
        Pessoa pessoa = new Pessoa();
        if (id != null) {
            // É uma atualização
            pessoa.id = id;
        }
        pessoa.nome = campoNome.getText().toString();
        pessoa.cpf = campoCpf.getText().toString();
        pessoa.idade = idade;
 
        // Salvar
        salvarPessoa(pessoa);
 
        // OK
        setResult(RESULT_OK, new Intent());
 
        // Fecha a tela
        finish();
    }
 
    public void excluir() {
        if (id != null) {
            excluirPessoa(id);
        }
 
        // OK
        setResult(RESULT_OK, new Intent());
 
        // Fecha a tela
        finish();
    }
 
    // Buscar a pessoa pelo id
    protected Pessoa buscarPessoa(long id) {
        return CadastroPessoa.repositorio.buscarPessoa(id);
    }
 
    // Salvar a pessoa
    protected void salvarPessoa(Pessoa pessoa) {
        CadastroPessoa.repositorio.salvar(pessoa);
    }
 
    // Excluir a pessoa
    protected void excluirPessoa(long id) {
        CadastroPessoa.repositorio.deletar(id);
    }
 
     
}
