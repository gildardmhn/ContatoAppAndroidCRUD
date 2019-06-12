package com.codinginflow.contato.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.codinginflow.contato.Dao.ContatoDao;
import com.codinginflow.contato.Model.Contato;

@Database(entities = {Contato.class}, version = 1, exportSchema = false)
public abstract class ContatoDatabase extends RoomDatabase {

    private static ContatoDatabase instanceDatabase;

    public abstract ContatoDao contatoDao();

    public static synchronized ContatoDatabase getInstance(Context context){
        if (instanceDatabase == null){
            instanceDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    ContatoDatabase.class, "contato_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instanceDatabase;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instanceDatabase).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContatoDao contatoDao;

        private PopulateDbAsyncTask(ContatoDatabase db){
            contatoDao = db.contatoDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            contatoDao.insert(new Contato("Gildard", "888888", "Quixada"));
            contatoDao.insert(new Contato("Ebbon", "888888", "Rio"));
            contatoDao.insert(new Contato("Gilo", "888888", "Cotonou"));
            return null;
        }
    }


}
