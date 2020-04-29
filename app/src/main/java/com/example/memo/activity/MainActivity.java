package com.example.memo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memo.bdd.AppDatabaseHelper;
import com.example.memo.memo.ItemTouchHelperCallback;
import com.example.memo.bdd.MemoDTO;
import com.example.memo.memo.MemosAdapter;
import com.example.memoDTO.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MemosAdapter memosAdapter;
    private List<MemoDTO> listMemoDTO = new ArrayList<MemoDTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.liste_memos);

        // à ajouter pour de meilleures performances :
        recyclerView.setHasFixedSize(true);

        // layout manager, décrivant comment les items sont disposés :
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // contenu d'exemple :
        /*final List<MemoDTO> listMemoDTOS = new ArrayList<>();
        listMemoDTOS.add(new MemoDTO("Faire les courses"));
        for (int i = 0; i <= 10; i++){
            listMemoDTOS.add(new MemoDTO("Sortir "+i));
        }*/

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        int valeur = preferences.getInt("positionMemo", -1);
        if ( valeur > -1) {
            Toast.makeText(this, "La derniere valeur cliquée est " + valeur, Toast.LENGTH_SHORT ).show();
        }

        listMemoDTO = AppDatabaseHelper.getDatabase(this).MemosDAO().getListeMemos();
        memosAdapter = new MemosAdapter(listMemoDTO, this);

        Button AddMemo = (Button) findViewById(R.id.addMemo);

        AddMemo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText editTextMemo = (EditText) findViewById(R.id.editTextMemo);
                MemoDTO memoDTO = new MemoDTO(editTextMemo.getText().toString());
                AppDatabaseHelper.getDatabase(view.getContext()).MemosDAO().insert(memoDTO);
                editTextMemo.setText("");
                listMemoDTO.add(memoDTO);
                memosAdapter.notifyItemInserted(listMemoDTO.size());
            }
        });

        // adapter :
        MemosAdapter memosAdapter = new MemosAdapter(listMemoDTO, this);
        recyclerView.setAdapter(memosAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(memosAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}
