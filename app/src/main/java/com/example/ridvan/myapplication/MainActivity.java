package com.example.ridvan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    GridLayout harflyt,kelimelyt;
    String secilenKelime;
    Button[] harfbutton  = new Button[23];
    Button[] kelimebutton  = new Button[10];
    ArrayList<String> sehirler= new ArrayList<String>();
    int hak=3;
    View.OnClickListener onclick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String harf = ((Button) v).getText().toString();
            ArrayList<Integer> sira = getHarfSira(harf);
            TextView haktxt = findViewById(R.id.txt_hak);
            if (sira.size() !=0)
                for (Integer index : sira)
                    kelimebutton[index].setText(harf);

            else{
                hak--;
                haktxt.setText(hak + " Hakkınız kaldi");
            }
            String tahminKelime= "";
            for(Button btn: kelimebutton)
                if( btn != null){
                tahminKelime += btn.getText();
                }
            if(tahminKelime.equals(secilenKelime))
                haktxt.setText("Tebrikler");
            if(hak==0){
                haktxt.setText("Kaybettiniz");
                for(int i=0;i<secilenKelime.length();i++){
                    kelimebutton[i].setText(secilenKelime.substring(i,i+1));
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        harflyt = findViewById(R.id.harf_layout);
        kelimelyt = findViewById(R.id.kelime_layout);
        buttonOlustur();
        dosyaOku();
    }
    public ArrayList<Integer>getHarfSira(String harf){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<secilenKelime.length();i++)
            if(secilenKelime.substring(i,i+1).equals(harf))
            result.add(i);
        return result;
    }

    public void oyunaBasla(View view) {
        Random rnd = new Random();
        int rastgele = rnd.nextInt(sehirler.size());
        secilenKelime = sehirler.get(rastgele);
        kelimelyt.removeAllViews();
        for(int i = 0;i<secilenKelime.length();i++){
            kelimebutton[i] = new Button(this);
            kelimebutton[i].setText("__");
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height=140;
            params.width=80;
            params.columnSpec=GridLayout.spec(i);
            params.rowSpec=GridLayout.spec(0);

            kelimebutton[i].setLayoutParams(params);
            kelimelyt.addView(kelimebutton[i]);
        }
    }

    private void dosyaOku() {
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("sehirler.txt"));
            BufferedReader br = new BufferedReader(inputStreamReader);

            String st;
            while((st = br.readLine()) != null){
                sehirler.add(st);
            }
        }
        catch (IOException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    int row = 0, col = 0;
    private void buttonOlustur() {
        String[] harfler = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","R","S","T","U","V","Y","Z"};
        for(int i=0;i<harfbutton.length;i++){
            harfbutton[i] = new Button(this);
            harfbutton[i].setText(harfler[i]);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = 115;
            params.height = 140;

            params.columnSpec = GridLayout.spec(col);
            params.rowSpec = GridLayout.spec(row);

            harfbutton[i].setLayoutParams(params);
            harfbutton[i].setOnClickListener(onclick);

            harflyt.addView(harfbutton[i]);
            col++;
            if(col == 4 && row == 5){
                break;
            }
            if(col == 6){
                row++;
                col = 0;
            }
        }
    }

}
