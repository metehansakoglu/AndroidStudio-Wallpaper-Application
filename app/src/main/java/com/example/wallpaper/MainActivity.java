package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageModel> modelClasslist;
    private RecyclerView recyclerView;
    Adapter adapter;
    CardView mnature,mbus,mcar,mtrain,mtrending;
    EditText editText;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recylerview);
        mnature = findViewById(R.id.nature);
        mcar = findViewById(R.id.car);
        mbus = findViewById(R.id.bus);
        mtrain = findViewById(R.id.train);
        mtrending = findViewById(R.id.trending);
        editText=findViewById(R.id.edittext);
        search=findViewById(R.id.search);


        modelClasslist=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext(),modelClasslist);
        recyclerView.setAdapter(adapter);
        findphotos();

        mnature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query="nature";
                getSearcimage(query);
            }
        });
        mcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query="car";
                getSearcimage(query);
            }
        });
        mtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query="train";
                getSearcimage(query);
            }
        });
        mbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query="bus";
                getSearcimage(query);
            }
        });
        mtrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               findphotos();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query=editText.getText().toString().trim().toLowerCase();
                if(query.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Bir şeyler yazın",Toast.LENGTH_SHORT).show();

                }
                else{
                    getSearcimage(query);
                }
            }
        });
    }

    private void getSearcimage(String query) {
        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                modelClasslist.clear();
                if(response.isSuccessful()){
                    modelClasslist.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Fotoğraf bulunamadı",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void findphotos() {
        modelClasslist.clear();

        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                if(response.isSuccessful()){
                    modelClasslist.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Fotoğraf bulunamadı",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });

    }
}