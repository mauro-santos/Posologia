package br.com.maurosantos.android.posologia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.maurosantos.android.posologia.dominio.entidades.Medicamento;

/**
 * Created by Mauro on 12/11/2016.
 */

public class ArrayAdapterMedicamento extends ArrayAdapter<Medicamento> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public ArrayAdapterMedicamento(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ArrayAdapterMedicamento.ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtCor = (TextView) view.findViewById(R.id.txtCor);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            viewHolder.txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);

            view.setTag(viewHolder);

            convertView = view;
        } else {
            viewHolder = (ArrayAdapterMedicamento.ViewHolder) convertView.getTag();
            view = convertView;
        }

        Medicamento medicamento = getItem(position);

        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.txtCor));
        viewHolder.txtNome.setText(medicamento.getNome());
        viewHolder.txtDescricao.setText(medicamento.getDescricao());

        return view;
    }

    static class ViewHolder {
        TextView txtCor;
        TextView txtNome;
        TextView txtDescricao;
    }
}
