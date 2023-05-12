package com.example.listusersapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListUsers extends AppCompatActivity {
    List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        recyclerView = findViewById(R.id.recycView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers(Integer.parseInt(getIntent().getStringExtra("pageNumber")));
    }

    public void getUsers(int pageNum){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        userList = new ArrayList<User>();
        Request request = new Request.Builder()
                .url("https://reqres.in/api/users?page="+pageNum)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("FailCallBack","Bir hata meydana geldi");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    Log.d("response",responseBody);
                    Gson gson = new Gson();
                    UserResponse userResponse = gson.fromJson(responseBody, UserResponse.class);
                    userList = userResponse.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (userList.size()>0){
                                userAdapter = new UserAdapter(userList);
                                recyclerView.setAdapter(userAdapter);
                            }else {
                                System.out.println("ELEMAN BULUNAMADI");
                            }


                        }
                    });
                }else {
                    Log.d("ResponseFailed","Response başarısız");
                }
            }
        });
    }
}