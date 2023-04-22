package com.example.actividadparcial2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    // Objetos a necesitar con sus variables
    ImageView View_Foto;
    ImageButton Open_Photos, Enviar_ToEmail, Enviar_ToWhat;
    private static final int REQUEST_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se referencian los objetos.
        View_Foto = findViewById(R.id.Ver_Foto);
        Open_Photos = findViewById(R.id.Abri_Ga);
        Enviar_ToEmail = findViewById(R.id.Send_Ema);
        Enviar_ToWhat = findViewById(R.id.Send_Wha);
        // Función del botón para abrir la galería de mi teléfono.
        Open_Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrirá la galería de mi teléfono.
                Intent Open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Open, REQUEST_LOAD_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK){
            // Seleecionaremos una foto de la galería.
            Uri seleccionar = data.getData();
            View_Foto.setImageURI(seleccionar);
        }
        // Función del botón del Email.
        Enviar_ToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se llama al método para enviarla por Correo.
                Email();
            }
        });
        // función del botón del Whatsapp.
        Enviar_ToWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se llama al método para enviarla por Whatsapp.
                WHat_APP();
            }
        });
    }
    // Método para enviar la foto por Correo.
    public void Email(){
            View_Foto = findViewById(R.id.Ver_Foto);
            View_Foto.buildDrawingCache();
            Bitmap dato = View_Foto.getDrawingCache();
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("image/*");
            Email.putExtra(Intent.EXTRA_SUBJECT, "Mi Fotografía Del Parcial CII");
            Email.putExtra(Intent.EXTRA_TEXT, " ");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            dato.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), dato, "", "");
            Uri uri = Uri.parse(path);
            Email.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(Email, ""));
    }
    // Método para enviar la foto por Whatsapp.
    public void WHat_APP(){
        View_Foto = findViewById(R.id.Ver_Foto);
        View_Foto.buildDrawingCache();
        Bitmap dato2 = View_Foto.getDrawingCache();
        Intent Whatapp = new Intent(Intent.ACTION_SEND);
        Whatapp.setType("image/*");
        Whatapp.setPackage("com.whatsapp");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        dato2.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), dato2, " ", null);
        Uri uri = Uri.parse(path);
        Whatapp.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Whatapp);
    }
}