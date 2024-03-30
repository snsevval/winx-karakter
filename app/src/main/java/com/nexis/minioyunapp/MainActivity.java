package com.nexis.minioyunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //ana ekranda basılan tuşta sayfaya yönlendirmek içim
    public void btnAnasayfaAyar(View view){
        if(view.getId() == R.id.btnNormalOyun){
            sayfadegis("NormalOyun");
        } else if(view.getId() == R.id.btnSureliOyun){
            sayfadegis("SureliOyun");
        } else if(view.getId() == R.id.btnCikis){
            // Çıkış işlemleri buraya eklenebilir.
        }
    }

    private void sayfadegis(String aktiviteIsmi){
        if(aktiviteIsmi.equals("NormalOyun"))
            intent=new Intent(this, NormalOyunActivity.class);
        else if(aktiviteIsmi.equals("SureliOyun"))
            intent=new Intent(this, SureliOyunActivity.class);
        else{
            Toast.makeText(this, "Bilinmeyen aktivite: " + aktiviteIsmi, Toast.LENGTH_SHORT).show();
        }

        startActivity(intent);

    }

    public void cikisYap(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }




}