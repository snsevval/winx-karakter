package com.nexis.minioyunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class NormalOyunActivity extends AppCompatActivity {
    private TextView txtwinxBilgi, txtwinx, txtToplamPuan;
    private EditText editTxtTahmin;
    private String[] karakterler = {"Bloom", "Miusa", "Flora", "Layla", "Tecna",
            "Sky", "Brandon", "Roxy", "Tritannur", "Darcy", "Marion",
            "Griffin", "Nereus", "Niobe", "Musa", "Riven", "Helia",
            "Timmy", "Narrator", "Kiko", "Nebula", "Aisha", "Icy",
            "Stormy", "Selina", "Lucy", "Riven", "Roy", "Nex"};

    private Random rndwinx, rndHarf;
    private int rndwinxNumber, rndNumberHarf, baslangicHarfSayisi;
    private String gelenisim, isimBoyutu, editTxtGelenTahmin;
    private ArrayList<Character> isimHarfleri;
    private float maximumPuan = 100.0f, azaltilicakPuan, toplamPuan = 0, bolumToplamPuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_oyun);
        txtwinxBilgi = (TextView) findViewById(R.id.txtViewIlBilgiN);
        txtwinx = (TextView) findViewById(R.id.txtViewIlN);
        editTxtTahmin = (EditText) findViewById(R.id.editTxtTahminN);
        txtToplamPuan = (TextView) findViewById(R.id.txtViewToplamPuanN);

        rndHarf = new Random();
        randomDegerleriBelirle();
    }

    public void btnTahminEtN(View v) {
        editTxtGelenTahmin = editTxtTahmin.getText().toString();

        if (!TextUtils.isEmpty(editTxtGelenTahmin)) {
            if (editTxtGelenTahmin.equals(gelenisim)){
                bolumToplamPuan += toplamPuan;
                Toast.makeText(getApplicationContext(), "Tebrikler Doğru Tahmin!", Toast.LENGTH_SHORT).show();
                txtToplamPuan.setText(" Bölüm Puanı: " + bolumToplamPuan);

                editTxtTahmin.setText("");
                randomDegerleriBelirle();
            }
            else
                System.out.println("Yanlış Tahminde Bulundunuz!");
        } else
            System.out.println("Tahmin Değeri Boş Olamaz!");
    }

    public void btnHarfAlN(View v) {
        if (isimHarfleri.size() > 0) {
            randomHarfAl();
            toplamPuan -= azaltilicakPuan;
            Toast.makeText(getApplicationContext(), "Kalan Puan : " + toplamPuan, Toast.LENGTH_SHORT).show();
        }else
            System.out.println("Harf Kalmadı!");
    }

    private void randomDegerleriBelirle(){
        isimBoyutu = "";

        rndwinx = new Random();
        rndwinxNumber = rndwinx.nextInt(karakterler.length);
        gelenisim = karakterler[rndwinxNumber];
        System.out.println(rndwinxNumber + " = " + gelenisim);
        txtwinxBilgi.setText(gelenisim.length() + " Harfli Karakterimiz");

        if (gelenisim.length() >= 5 && gelenisim.length() <= 7)
            baslangicHarfSayisi = 1;
        else if (gelenisim.length() >= 8 && gelenisim.length() < 10)
            baslangicHarfSayisi = 2;
        else if (gelenisim.length() >= 10)
            baslangicHarfSayisi = 3;
        else
            baslangicHarfSayisi = 0;

        for (int i = 0; i < gelenisim.length(); i++) {
            if (i < gelenisim.length() - 1)
                isimBoyutu += "_ ";
            else
                isimBoyutu += "_";
        }

        txtwinx.setText(isimBoyutu);
        isimHarfleri = new ArrayList<>();

        for (char c : gelenisim.toCharArray())
            isimHarfleri.add(c);

        for (int c = 0; c < baslangicHarfSayisi; c++)
            randomHarfAl();

        azaltilicakPuan = maximumPuan / isimHarfleri.size();
        toplamPuan = maximumPuan;
    }

    private void randomHarfAl(){
        rndNumberHarf = rndHarf.nextInt(isimHarfleri.size());
        String[] txtHarfler = txtwinx.getText().toString().split(" ");
        char[] gelenwinxHarfler = gelenisim.toCharArray();

        for (int i = 0; i < gelenisim.length(); i++) {
            if (txtHarfler[i].equals("_") && gelenwinxHarfler[i] == isimHarfleri.get(rndNumberHarf)) {
                txtHarfler[i] = String.valueOf(isimHarfleri.get(rndNumberHarf));
                isimBoyutu = "";

                for (int j = 0; j < gelenisim.length(); j++) {
                    if (j == i)
                        isimBoyutu += txtHarfler[j] + " ";
                    else if (j < gelenisim.length() - 1)
                        isimBoyutu += txtHarfler[j] + " ";
                    else
                        isimBoyutu += txtHarfler[j];
                }

                break;
            }
        }

        txtwinx.setText(isimBoyutu);
        isimHarfleri.remove(rndNumberHarf);
    }
}