package com.example.memo.memo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.activity.DetailActivity;
import com.example.memo.bdd.MemoDTO;
import com.example.memo.fragments.DetailFragment;
import com.example.memo.ws.RetourWS;
import com.example.memoDTO.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MemosAdapter extends RecyclerView.Adapter<MemosAdapter.MemosViewHolder> {

    private List<MemoDTO> listeMemoDTOS;
    private AppCompatActivity activity;


    public MemosAdapter(List<MemoDTO> listMemoDTOS, AppCompatActivity activity) {
        this.activity = activity;
        this.listeMemoDTOS = listMemoDTOS;
    }

    public MemosAdapter(List<MemoDTO> listMemoDTOS) {
        this.listeMemoDTOS = listMemoDTOS;
    }

    @NonNull
    @Override
    public MemosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewMemo = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_item_liste, parent, false);
        return new MemosViewHolder(viewMemo);
    }

    @Override
    public void onBindViewHolder(@NonNull MemosViewHolder holder, int position) {
        holder.textViewLibelleMemo.setText(listeMemoDTOS.get(position).intitule);
    }

    @Override
    public int getItemCount() {
        return listeMemoDTOS.size();
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    public boolean onItemMove(int positionDebut, int positionFin)
    {
        Collections.swap(listeMemoDTOS, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }

    // Appelé une fois à la suppression.
    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            listeMemoDTOS.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class MemosViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewLibelleMemo;

        public MemosViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLibelleMemo = itemView.findViewById(R.id.libelle_memo);



            textViewLibelleMemo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View view)
                {
                    MemoDTO memoDTO = listeMemoDTOS.get(getAdapterPosition());

                    //Sauvegarde de la position
                    /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("positionMemo", getAdapterPosition());
                    editor.apply();

                    // client HTTP :
                    AsyncHttpClient client = new AsyncHttpClient();
                    // paramètres :
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("memo", memoDTO.intitule);*/
                    if (activity.findViewById(R.id.conteneur_detail) == null) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("memo", memoDTO.intitule);
                        view.getContext().startActivity(intent);
                    }  else {
                        DetailFragment fragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("memo", memoDTO.intitule);
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = activity.getSupportFragmentManager();

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.conteneur_detail, fragment, "detail");
                        fragmentTransaction.commit();
                    }

                    // appel :
                    /*client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              byte[] response)
                        {
                            String retour = new String(response);
                            // conversion en un objet Java (à faire!) ayant le même format que le JSON :
                            Gson gson = new Gson();
                            RetourWS retourWS = gson.fromJson(retour, RetourWS.class);
                            Log.i("TAG", retour);
                            Toast.makeText(view.getContext(), retourWS.form.memo, Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] errorResponse, Throwable e)
                        {
                            Log.e("TAG", e.toString());
                            Toast.makeText(view.getContext(), "Cheh", Toast.LENGTH_LONG).show();
                        }
                    });*/


                }
            });
        }
    }
}
