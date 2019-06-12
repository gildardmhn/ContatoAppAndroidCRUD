package com.codinginflow.contato.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.codinginflow.contato.R;


public class AddEditContatoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.codinginflow.contato.EXTRA_ID";
    public static final String EXTRA_NOME = "com.codinginflow.contato.EXTRA_NOME";
    public static final String EXTRA_TELEFONE = "com.codinginflow.contato.EXTRA_TELEFONE";
    public static final String EXTRA_ENDERECO = "com.codinginflow.contato.EXTRA_ENDERECO";

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contato);

        editTextNome = findViewById(R.id.edit_text_nome);
        editTextTelefone = findViewById(R.id.edit_text_telefone);
        editTextEndereco = findViewById(R.id.edit_text_endereco);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Editar Contato");
            editTextNome.setText(intent.getStringExtra(EXTRA_NOME));
            editTextTelefone.setText(intent.getStringExtra(EXTRA_TELEFONE));
            editTextEndereco.setText(intent.getStringExtra(EXTRA_ENDERECO));
        } else {
            setTitle("Cadastrar Contato");
        }

    }

    private void saveContato() {
        String nome = editTextNome.getText().toString();
        String telefone = editTextTelefone.getText().toString();
        String endereco = editTextEndereco.getText().toString();

        if (nome.trim().isEmpty() || telefone.trim().isEmpty() || endereco.trim().isEmpty()){
            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOME, nome);
        data.putExtra(EXTRA_TELEFONE, telefone);
        data.putExtra(EXTRA_ENDERECO, endereco);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contato_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contato:
                saveContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
