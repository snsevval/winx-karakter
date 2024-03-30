package com.nexis.minioyunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class SureliOyunActivity extends AppCompatActivity {

    private TextView txtwinxBilgi, txtwinx, txtToplamPuan, txtSure;
    private EditText editTxtTahmin;
    private Button btnHarfAl, btnTahminEt, btnTekrarOyna;
    private String[] karakterler = {"Bloom", "Miusa", "Flora", "Layla", "Tecna",
            "Sky", "Brandon", "Roxy", "Tritannur", "Darcy", "Marion",
            "Griffin", "Nereus", "Niobe", "Musa", "Riven", "Helia",
            "Timmy", "Narrator", "Kiko", "Nebula", "Aisha", "Icy",
            "Stormy", "Selina", "Lucy", "Riven", "Roy", "Nex"};

    private Random rndwinx, rndHarf;
    private int rndwinxNumber, rndNumberHarf, baslangicHarfSayisi, toplamSure = 180000;
    private String gelenwinx, winxBoyutu, editTxtGelenTahmin;
    private ArrayList<Character> isimHarfleri;
    private float maximumPuan = 100.0f, azaltilicakPuan, toplamPuan = 0, bolumToplamPuan = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sureli_oyun);
        txtwinxBilgi = (TextView) findViewById(R.id.txtViewIlBilgiS);
        txtwinx = (TextView) findViewById(R.id.txtViewIlN);
        editTxtTahmin = (EditText) findViewById(R.id.editTxtTahminS);
        txtToplamPuan = (TextView) findViewById(R.id.txtViewToplamPuanS);
        txtSure = (TextView)findViewById(R.id.txtViewSureS);
        btnHarfAl = (Button)findViewById(R.id.btnHarfAlS);
        btnTahminEt = (Button)findViewById(R.id.btnTahminEtS);
        btnTekrarOyna = (Button)findViewById(R.id.btnTekrarOynaS);

        //1000 = 1saniye
        //60000 = 60saniye
        //180000 = 180 saniye = 3 dakikaka

        new CountDownTimer(toplamSure, 1000) {
            @Override
            public void onTick(long l) {
                txtSure.setText((l / 60000) + ":" + ((l % 60000) / 1000));
            }

            @Override
            public void onFinish() {
                txtSure.setText("0:00");
                editTxtTahmin.setEnabled(false);
                btnHarfAl.setEnabled(false);
                btnTahminEt.setEnabled(false);
                btnTekrarOyna.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Oyun Bitti\nToplam Puanınız: "
                        + bolumToplamPuan
                        + "\nTekrar Oynamak İçin Butona Basın.", Toast.LENGTH_LONG).show();
            }
        }.start();

        rndHarf = new Random();
        randomDegerleriBelirle();
    }

    public void btnHarfAlS(View v){
        if (isimHarfleri.size() > 0) {
            randomHarfAl();
            toplamPuan -= azaltilicakPuan;
            Toast.makeText(getApplicationContext(), "Kalan Puan = " + toplamPuan, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(), "Alınabilecek Harf Kalmadı.", Toast.LENGTH_SHORT).show();
    }

    public void btnTekrarOynaS(View v){
        Intent tekrarOyna = new Intent(this, SureliOyunActivity.class);
        finish();
        startActivity(tekrarOyna);
    }

    public void btnTahminEtS(View v){
        editTxtGelenTahmin = editTxtTahmin.getText().toString();

        if (!TextUtils.isEmpty(editTxtGelenTahmin)) {
            if (editTxtGelenTahmin.equals(gelenwinx)){
                bolumToplamPuan += toplamPuan;
                Toast.makeText(getApplicationContext(), "Tebrikler Doğru Tahminde Bulundunuz.", Toast.LENGTH_SHORT).show();
                txtToplamPuan.setText("Toplam Bölüm Puanı: " + bolumToplamPuan);

                editTxtTahmin.setText("");
                randomDegerleriBelirle();
            }
            else
                Toast.makeText(getApplicationContext(), "Yanlış Tahminde Bulundunuz.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Tahmin Değeri Boş Olamaz.", Toast.LENGTH_SHORT).show();
    }

    private void randomDegerleriBelirle(){
        winxBoyutu = "";

        rndwinx = new Random();
        rndwinxNumber = rndwinx.nextInt(karakterler.length);
        gelenwinx = karakterler[rndwinxNumber];
        System.out.println(rndwinxNumber + " = " + gelenwinx);
        txtwinxBilgi.setText(gelenwinx.length() + " Harfli İlimiz");

        if (gelenwinx.length() >= 5 && gelenwinx.length() <= 7)
            baslangicHarfSayisi = 1;
        else if (gelenwinx.length() >= 8 && gelenwinx.length() < 10)
            baslangicHarfSayisi = 2;
        else if (gelenwinx.length() >= 10)
            baslangicHarfSayisi = 3;
        else
            baslangicHarfSayisi = 0;

        for (int i = 0; i < gelenwinx.length(); i++) {
            if (i < gelenwinx.length() - 1)
                winxBoyutu += "_ ";
            else
                winxBoyutu += "_";
        }

        txtwinx.setText(winxBoyutu);
        isimHarfleri = new ArrayList<>();

        for (char c : gelenwinx.toCharArray())
            isimHarfleri.add(c);

        for (int c = 0; c < baslangicHarfSayisi; c++)
            randomHarfAl();

        azaltilicakPuan = maximumPuan / isimHarfleri.size();
        toplamPuan = maximumPuan;
    }

    private void randomHarfAl(){
        rndNumberHarf = rndHarf.nextInt(isimHarfleri.size());
        String[] txtHarfler = txtwinx.getText().toString().split(" ");
        char[] gelenIlHarfler = gelenwinx.toCharArray();

        for (int i = 0; i < gelenwinx.length(); i++) {
            if (txtHarfler[i].equals("_") && gelenIlHarfler[i] == isimHarfleri.get(rndNumberHarf)) {
                txtHarfler[i] = String.valueOf(isimHarfleri.get(rndNumberHarf));
                winxBoyutu = "";

                for (int j = 0; j < gelenwinx.length(); j++) {
                    if (j == i)
                        winxBoyutu += txtHarfler[j] + " ";
                    else if (j < gelenwinx.length() - 1)
                        winxBoyutu += txtHarfler[j] + " ";
                    else
                        winxBoyutu += txtHarfler[j];
                }

                break;
            }
        }

        txtwinx.setText(winxBoyutu);
        isimHarfleri.remove(rndNumberHarf);
    }
}