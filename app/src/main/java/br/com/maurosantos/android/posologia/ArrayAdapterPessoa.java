package br.com.maurosantos.android.posologia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;
import br.com.maurosantos.android.posologia.util.DateUtils;

/**
 * Created by Mauro on 12/11/2016.
 */

public class ArrayAdapterPessoa extends ArrayAdapter<Pessoa> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public ArrayAdapterPessoa(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtCor = (TextView) view.findViewById(R.id.txtCor);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            viewHolder.txtDataRegistro = (TextView) view.findViewById(R.id.txtDataRegistro);

            view.setTag(viewHolder);

            convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Pessoa pessoa = getItem(position);

        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.txtCor));
        viewHolder.txtNome.setText(pessoa.getNome());
        viewHolder.txtDataRegistro.setText(context.getResources().getString(R.string.lbl_data_registro) + ": " + DateUtils.dateToString(pessoa.getDataRegistro()));

        return view;
    }

    static class ViewHolder {
        TextView txtCor;
        TextView txtNome;
        TextView txtDataRegistro;
    }
}
