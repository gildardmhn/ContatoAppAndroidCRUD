package com.codinginflow.contato.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codinginflow.contato.Model.Contato;

import java.util.List;

@Dao
public interface ContatoDao {

    @Insert
    void insert(Contato contato);

    @Update
    void update(Contato contato);

    @Delete
    void delete(Contato contato);

    @Query("Delete From contato_table")
    void deleteAllContatos();

    @Query("Select * From contato_table Order By nome Asc")
    LiveData<List<Contato>> getAllContatos();
}
