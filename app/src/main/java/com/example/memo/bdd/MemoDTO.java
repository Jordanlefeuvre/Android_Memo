package com.example.memo.bdd;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "memos")
public class MemoDTO
{
    @PrimaryKey(autoGenerate = true)
    public long memoId = 0;
    public String intitule;

    // Constructeur public vide (obligatoire si autre constructeur existant) :
    public MemoDTO() {}

    // Autre constructeur :
    public MemoDTO(String intitule)
    {
        this.intitule = intitule;
    }
}