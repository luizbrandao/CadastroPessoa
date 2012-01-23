package com.br.CadPessoa;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class PessoaListAdapter extends BaseAdapter {
     
    private Context context;
    private List<Pessoa> lista;
 
    public PessoaListAdapter(Context context, List<Pessoa> lista) {
        this.context = context;
        this.lista = lista;
    }
 
    public int getCount() {
        return lista.size();
    }
 
    public Object getItem(int position) {
        return lista.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recupera a pessoa da posição atual
        Pessoa p = lista.get(position);
 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pessoa_linha_tabela, null);
 
        // Atualiza o valor do TextView
        TextView nome = (TextView) view.findViewById(R.id.nome);
        nome.setText(p.nome);
 
        TextView cpf = (TextView) view.findViewById(R.id.cpf);
        cpf.setText(p.cpf);
 
        TextView idade = (TextView) view.findViewById(R.id.idade);
        idade.setText(String.valueOf(p.idade));
 
        return view;
    }
 
}
