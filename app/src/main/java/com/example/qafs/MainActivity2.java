package com.example.qafs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private List<String> genelListe, soruListesi;
    private RelativeLayout soruContainer;
    private LinearLayout butonContainer;
    private TextView ilerleme_tv, soru_tv;
    private Random random;
    private String dogruCevap;
    private int toplamCevapSayisi;
    private final int TOPLAM_SORU_SAYISI = 10;
    private int quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        quiz = getIntent().getIntExtra(MainActivity.QUIZ_KEY, MainActivity.SAYILAR);
        genelListe = new ArrayList<>();
        soruListesi = new ArrayList<>();
        random = new Random();

        soruContainer = findViewById(R.id.soruContainer);
        butonContainer = findViewById(R.id.butonContainer);
        ilerleme_tv = findViewById(R.id.ilerleme_tv);
        soru_tv = findViewById(R.id.soru_tv);

        for (int i = 0; i < butonContainer.getChildCount(); i++) {
            Button button = (Button) butonContainer.getChildAt(i);
            button.setOnClickListener(this::onAnswerSelected);
        }

        loadQuestions();
        resetQuiz();
    }

    private void loadQuestions() {
        String[] dizi;
        switch (quiz) {
            case MainActivity.SAYILAR:
                dizi = getResources().getStringArray(R.array.sayilar);
                break;
            case MainActivity.RENKLER:
                dizi = getResources().getStringArray(R.array.colors);
                break;
            case MainActivity.HAYVANLAR:
                dizi = getResources().getStringArray(R.array.animals);
                break;
            default:
                dizi = new String[0];
        }

        for (String s : dizi) {
            genelListe.add(s.split("-")[0]); // Sadece Türkçe kısmı
        }
    }

    private void resetQuiz() {
        toplamCevapSayisi = 0;
        soruListesi.clear();

        while (soruListesi.size() < TOPLAM_SORU_SAYISI) {
            int index = random.nextInt(genelListe.size());
            String soru = genelListe.get(index);
            if (!soruListesi.contains(soru)) {
                soruListesi.add(soru);
            }
        }
        sonrakiSoru();
    }

    private void sonrakiSoru() {
        if (soruListesi.isEmpty()) {
            // Quiz tamamlandı, sonuçları göster
            return;
        }

        String soru = soruListesi.remove(0);
        soru_tv.setText(soru);

        int index = genelListe.indexOf(soru);
        dogruCevap = getCorrectAnswer(index);

        List<String> cevaplar = new ArrayList<>();
        cevaplar.add(dogruCevap);
        addWrongAnswers(cevaplar, index);
        Collections.shuffle(cevaplar);

        for (int i = 0; i < butonContainer.getChildCount(); i++) {
            Button button = (Button) butonContainer.getChildAt(i);
            button.setText(cevaplar.get(i));
        }

        toplamCevapSayisi++;
        ilerleme_tv.setText(toplamCevapSayisi + " / " + TOPLAM_SORU_SAYISI);
    }

    private void onAnswerSelected(View v) {
        Button clickedButton = (Button) v;
        if (clickedButton.getText().equals(dogruCevap)) {
            // Doğru cevap
            soruContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            // Yanlış cevap
            soruContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }

        // Bir süre bekledikten sonra arka plan rengini sıfırlamak için bir Handler kullanabilirsiniz
        soruContainer.postDelayed(() -> {
            soruContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            sonrakiSoru();
        }, 1000); // 1 saniye bekle
    }

    private String getCorrectAnswer(int index) {
        String[] dizi;
        switch (quiz) {
            case MainActivity.SAYILAR:
                dizi = getResources().getStringArray(R.array.sayilar);
                break;
            case MainActivity.RENKLER:
                dizi = getResources().getStringArray(R.array.colors);
                break;
            case MainActivity.HAYVANLAR:
                dizi = getResources().getStringArray(R.array.animals);
                break;
            default:
                return "";
        }
        return dizi[index].split("-")[1]; // İngilizce kısmı döndür
    }

    private void addWrongAnswers(List<String> cevaplar, int correctIndex) {
        while (cevaplar.size() < butonContainer.getChildCount()) {
            int wrongIndex = random.nextInt(genelListe.size());
            String wrongAnswer = getCorrectAnswer(wrongIndex);
            if (!cevaplar.contains(wrongAnswer) && wrongIndex != correctIndex) {
                cevaplar.add(wrongAnswer);
            }
        }
    }
}
