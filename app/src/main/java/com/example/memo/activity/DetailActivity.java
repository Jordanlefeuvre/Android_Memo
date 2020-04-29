package com.example.memo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.memo.fragments.DetailFragment;
import com.example.memoDTO.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String memo = intent.getStringExtra("memo");

        Log.i("tag",memo);

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("memo", memo);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteneur_detail, fragment, "detail");
        fragmentTransaction.commit();

    }
}
