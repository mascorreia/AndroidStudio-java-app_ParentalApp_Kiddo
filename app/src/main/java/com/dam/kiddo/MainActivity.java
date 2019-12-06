package com.dam.kiddo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

    private static final String VALOR_NOME = "NOME";
    private static final String VALOR_CAMINHO_FOTO = "PHOTO_PATH";

    private static final int PERMISSÃO_LER_FICHEIROS = 1000;
    private static final int IMAGE_PICKER_SELECT = 1001;

    private String photoPath;
    private String imagePath;

    /*Button buttonGuardar = (Button)findViewById(R.id.buttonSave);
    buttonGuardar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            guardarValores();
        }
    });*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lerValores();
    }

    public void DefinitionsActivity(View view){
        Intent intent1 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent1);
    }



    @Override
    public void onBackPressed(){

    }

    private void lerValores(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String nomeGuardado = sharedPref.getString(VALOR_NOME, "");

        photoPath = sharedPref.getString(VALOR_CAMINHO_FOTO, "");

        EditText editNome = findViewById(R.id.editNome);

        editNome.setText(nomeGuardado);
        carregarImagem(photoPath);
    }

    private void guardarValores(){

        EditText editNome = findViewById(R.id.editNome);
        String nome = editNome.getText().toString();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //guardar valores
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALOR_NOME, nome);
        edit.putString(VALOR_CAMINHO_FOTO, photoPath);
        //Instrução é que vai guardar os valores
        edit.commit();
        //notificar utilizador da concretizacao da operacao
        Toast.makeText(MainActivity.this, getResources().getText(R.string.info_saved),
                Toast.LENGTH_SHORT).show();
    }

    private void pedirImagem(){
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICKER_SELECT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = MainActivity.this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            photoPath = cursor.getString(columnIndex);
            cursor.close();
            carregarImagem(photoPath);
        }
    }

        private void carregarImagem(String imagePath){
            ImageView imgView = findViewById(R.id.imagePhoto);
            if(imagePath.length() == 0){
                imgView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.background, null));
            }else {
                File imgFile = new File(imagePath);
                if (imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imgView.setImageBitmap(myBitmap);
                }
            }
        }
}




