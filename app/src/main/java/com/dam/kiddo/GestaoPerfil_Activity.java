package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class GestaoPerfil_Activity extends AppCompatActivity {
    private static final String VALOR_NOME = "NOME";
    private static final String VALOR_CAMINHO_FOTO = "PHOTO_PATH";

    private static final int PERMISSÃO_LER_FICHEIROS = 1000;
    private static final int IMAGE_PICKER_SELECT = 1001;

    private String photoPath;



    //Date
    private static final String TAG = "GestaoPerfilActivitity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;




    private android.preference.PreferenceManager PreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_perfil);
        lerValores();


        Button buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarValores();
            }


        });

        //associar o método carregarImagem() no clique na imageView (prop. isClickable = true)
        ImageView imgView = findViewById(R.id.imagePhoto);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedirImagemComPermissoes();
            }
        });

        //Date
        mDisplayDate = (TextView)findViewById(R.id.txtData);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(GestaoPerfil_Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);

                String date =  day  + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


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

    public void guardarValores(){

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
        Toast.makeText(GestaoPerfil_Activity.this, getResources().getText(R.string.info_saved),
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
            Cursor cursor = GestaoPerfil_Activity.this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if(cursor == null){
                System.out.println("imagem nao carregada");
            }else{
                System.out.println("imagem carregada");
            }
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
            imgView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.avatar, null));
        }else {
            File imgFile = new File(imagePath);
            if (imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgView.setImageBitmap(myBitmap);
            }
        }
    }




    private void pedirImagemComPermissoes(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_read_file_permission)
                            .setCancelable(false)
                            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pedirPermissoes();
                                }
                            });
                builder.create().show();
            }else {
                pedirPermissoes();
            }
        }else{
            pedirImagem();
        }
    }


    private void pedirPermissoes() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSÃO_LER_FICHEIROS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[]
            grantResults) {
        switch (requestCode) {
            case PERMISSÃO_LER_FICHEIROS: {
                //Se o pedido foi cancelado, o array de resultados está vazio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permissão aceite.
                    Toast.makeText(this, R.string.toast_read_file_permission_granted,
                            Toast.LENGTH_SHORT).show();
                    //Lançar o pedido de escolha de imagem.
                    pedirImagem();
                } else {
                    //Permissão rejeitada, não poderá alterar a sua imagem de perfil.
                    Toast.makeText(this, R.string.toast_read_file_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent2);

    }



}
