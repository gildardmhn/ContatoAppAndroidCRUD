package com.codinginflow.contato.Repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codinginflow.contato.Dao.ContatoDao;
import com.codinginflow.contato.Database.ContatoDatabase;
import com.codinginflow.contato.Model.Contato;

import java.util.List;

public class ContatoRepository {

    private ContatoDao contatoDao;
    private LiveData<List<Contato>> allContatos;

    public ContatoRepository(Application application){
        ContatoDatabase database = ContatoDatabase.getInstance(application);
        contatoDao = database.contatoDao();
        allContatos = contatoDao.getAllContatos();
    }

    public void insert(Contato contato){
        new InsertContatoAsyncTask(contatoDao).execute(contato);
    }

    public void update(Contato contato){
        new UpdateContatoAsyncTask(contatoDao).execute(contato);
    }

    public void delete(Contato contato){
        new DeleteContatoAsyncTask(contatoDao).execute(contato);
    }

    public void deleteAll(){
        new DeleteAllContatosAsyncTask(contatoDao).execute();
    }

    public LiveData<List<Contato>> getAllContatos(){
        return allContatos;
    }

    private static class InsertContatoAsyncTask extends AsyncTask<Contato, Void, Void>{

        private ContatoDao contatoDao;

        private InsertContatoAsyncTask(ContatoDao contatoDao){
            this.contatoDao = contatoDao;
        }

        @Override
        protected Void doInBackground(Contato... contatos) {
            contatoDao.insert(contatos[0]);
            return null;
        }
    }

    private static class UpdateContatoAsyncTask extends AsyncTask<Contato, Void, Void>{

        private ContatoDao contatoDao;

        private UpdateContatoAsyncTask(ContatoDao contatoDao){
            this.contatoDao = contatoDao;
        }

        @Override
        protected Void doInBackground(Contato... contatos) {
            contatoDao.update(contatos[0]);
            return null;
        }
    }

    private static class DeleteContatoAsyncTask extends AsyncTask<Contato, Void, Void>{

        private ContatoDao contatoDao;

        private DeleteContatoAsyncTask(ContatoDao contatoDao){
            this.contatoDao = contatoDao;
        }

        @Override
        protected Void doInBackground(Contato... contatoes) {
            contatoDao.delete(contatoes[0]);
            return null;
        }
    }

    private static class DeleteAllContatosAsyncTask extends AsyncTask<Void, Void, Void>{

        private ContatoDao contatoDao;

        private DeleteAllContatosAsyncTask(ContatoDao contatoDao){
            this.contatoDao = contatoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contatoDao.deleteAllContatos();
            return null;
        }
    }
}
