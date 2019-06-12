package com.codinginflow.contato.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codinginflow.contato.Adapter.ContatoAdapter;
import com.codinginflow.contato.Model.Contato;
import com.codinginflow.contato.R;
import com.codinginflow.contato.ViewModel.ContatoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CONTATO_REQUEST = 1;
    public static final int EDIT_CONTATO_REQUEST = 2;
    private ContatoViewModel contatoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddContato = findViewById(R.id.button_add_contato);
        buttonAddContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditContatoActivity.class);
                startActivityForResult(intent, ADD_CONTATO_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContatoAdapter contatoAdapter = new ContatoAdapter();
        recyclerView.setAdapter(contatoAdapter);

        contatoViewModel = ViewModelProviders.of(this).get(ContatoViewModel.class);
        contatoViewModel.getAllContatos().observe(this, new Observer<List<Contato>>() {
            @Override
            public void onChanged(List<Contato> contatos) {
                contatoAdapter.submitList(contatos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contatoViewModel.delete(contatoAdapter.getContatoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Contato removida", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        contatoAdapter.setOnItemClickListener(new ContatoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(MainActivity.this, AddEditContatoActivity.class);
                intent.putExtra(AddEditContatoActivity.EXTRA_ID, contato.getId());
                intent.putExtra(AddEditContatoActivity.EXTRA_NOME, contato.getNome());
                intent.putExtra(AddEditContatoActivity.EXTRA_TELEFONE, contato.getTelefone());
                intent.putExtra(AddEditContatoActivity.EXTRA_ENDERECO, contato.getEndereco());
                startActivityForResult(intent, EDIT_CONTATO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTATO_REQUEST && resultCode == RESULT_OK) {
            String nome = data.getStringExtra(AddEditContatoActivity.EXTRA_NOME);
            String telefone = data.getStringExtra(AddEditContatoActivity.EXTRA_TELEFONE);
            String endereco = data.getStringExtra(AddEditContatoActivity.EXTRA_ENDERECO);

            Contato contato = new Contato(nome, telefone, endereco);
            contatoViewModel.insert(contato);
            Toast.makeText(this, "Contato salvo com sucesso", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_CONTATO_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditContatoActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Contato não pode ser atualizado", Toast.LENGTH_SHORT).show();
                return;
            }

            String nome = data.getStringExtra(AddEditContatoActivity.EXTRA_NOME);
            String endereco = data.getStringExtra(AddEditContatoActivity.EXTRA_ENDERECO);
            String telefone = data.getStringExtra(AddEditContatoActivity.EXTRA_TELEFONE);

            Contato contato = new Contato(nome, telefone, endereco);
            contato.setId(id);
            contatoViewModel.update(contato);

            Toast.makeText(this, "Contato atualizado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Contato não salvo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_contatos:
                contatoViewModel.deleteAllContatos();
                Toast.makeText(this, "Todos os contatos foram removidos", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
