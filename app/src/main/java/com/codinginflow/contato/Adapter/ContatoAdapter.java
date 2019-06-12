package com.codinginflow.contato.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codinginflow.contato.Model.Contato;
import com.codinginflow.contato.R;

public class ContatoAdapter extends ListAdapter<Contato, ContatoAdapter.ContatoHolder> {

    private onItemClickListener listener;

    public ContatoAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contato> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contato>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contato oldItem, @NonNull Contato newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contato oldItem, @NonNull Contato newItem) {
            return oldItem.getNome().equals(newItem.getNome()) &&
                    oldItem.getTelefone().equals(newItem.getTelefone()) &&
                    oldItem.getEndereco().equals(newItem.getEndereco());
        }
    };

    @NonNull
    @Override
    public ContatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contato_item, parent, false);
        return new ContatoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoHolder holder, int position) {

        Contato currentContato = getItem(position);
        holder.textViewNome.setText(currentContato.getNome());
        holder.textViewTelefone.setText(currentContato.getTelefone());
        holder.textViewEndereco.setText(currentContato.getEndereco());
    }

    public Contato getContatoAt(int position) {
        return getItem(position);
    }

    class ContatoHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewEndereco;
        private TextView textViewTelefone;

        public ContatoHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.text_view_nome);
            textViewEndereco = itemView.findViewById(R.id.text_view_telefone);
            textViewTelefone = itemView.findViewById(R.id.text_view_endereco);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Contato contato);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
