package com.example.qafs;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public static final String QUIZ_KEY = "key";
    public static final int SAYILAR = 1;
    public static final int RENKLER = 2;
    public static final int HAYVANLAR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML dosyanızın adını kontrol edin
    }

    public void testClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        switch (v.getId()) {
            case R.id.img1:
                intent.putExtra(QUIZ_KEY, SAYILAR);
                startActivity(intent);
                break; // Burada break eklenmeli
            case R.id.img2:
                intent.putExtra(QUIZ_KEY, RENKLER);
                startActivity(intent);
                break; // Burada break eklenmeli
            case R.id.img3:
                intent.putExtra(QUIZ_KEY, HAYVANLAR);
                startActivity(intent);
                break; // Burada break eklenmeli
            default:
                Toast.makeText(this, "Geçersiz seçim", Toast.LENGTH_SHORT).show();
                break; // Geçersiz seçim için bir default durumu ekleyebilirsiniz
        }
    }
}