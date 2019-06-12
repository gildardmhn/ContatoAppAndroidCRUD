package com.codinginflow.contato.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codinginflow.contato.Model.Contato;
import com.codinginflow.contato.Repository.ContatoRepository;

import java.util.List;

public class ContatoViewModel extends AndroidViewModel {


    private ContatoRepository contatoRepository;
    private LiveData<List<Contato>> allContatos;


    public ContatoViewModel(@NonNull Application application) {
        super(application);
        contatoRepository = new ContatoRepository(application);
        allContatos = contatoRepository.getAllContatos();
    }

    public void insert(Contato contato){
        contatoRepository.insert(contato);
    }

    public void update(Contato contato){
        contatoRepository.update(contato);
    }

    public void delete(Contato contato){
        contatoRepository.delete(contato);
    }

    public void deleteAllContatos(){
        contatoRepository.deleteAll();
    }

    public LiveData<List<Contato>> getAllContatos() {
        return allContatos;
    }
}
