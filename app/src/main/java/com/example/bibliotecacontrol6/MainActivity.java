package com.example.bibliotecacontrol6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText codigo,nombre,clasificacion,ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = (EditText)findViewById(R.id.txtCod);
        nombre = (EditText)findViewById(R.id.txtNombre);
        clasificacion = (EditText)findViewById(R.id.txtClasificacion);
        ubicacion = (EditText)findViewById(R.id.txtUbicacion);
    }

    public void registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigoT = codigo.getText().toString();
        String nombreT = nombre.getText().toString();
        String clasificacionT = clasificacion.getText().toString();
        String ubicacionT = ubicacion.getText().toString();

        if ((!codigoT.isEmpty()) && (!nombreT.isEmpty()) && (!clasificacionT.isEmpty()) && (!ubicacionT.isEmpty()) ){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigoT);
            registro.put("nombre",nombreT);
            registro.put("clasificacion",clasificacionT);
            registro.put("ubicacion",ubicacionT);
            bd.insert("libros",null,registro);
            bd.close();
            Toast.makeText(this,"Guardado con exito!!!",Toast.LENGTH_SHORT).show();
            codigo.setText("");
            nombre.setText("");
            clasificacion.setText("");
            ubicacion.setText("");




        }
        Toast.makeText(this,"Debes llenar todos los campos !!!",Toast.LENGTH_SHORT).show();

    }

    public void consultar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String codigoT = codigo.getText().toString();
        String nombreT = nombre.getText().toString();
        String clasificacionT = clasificacion.getText().toString();
        String ubicacionT = ubicacion.getText().toString();

        if (!codigoT.isEmpty()){
            Cursor fila = bd.rawQuery("select nombre,clasificacion,ubicacion from libros where codigo ='"+codigoT+"'",null);
            if (fila.moveToFirst()){
                nombre.setText(fila.getString(0));
                clasificacion.setText(fila.getString(1));
                ubicacion.setText(fila.getString(2));

                bd.close();
            }else{
                Toast.makeText(this,"No se encontro nada!!",Toast.LENGTH_SHORT).show();
                bd.close();


            }

        }else{
            Toast.makeText(this,"Debes ingresar un codigo para buscar",Toast.LENGTH_SHORT).show();
        }


    }

    public void eliminar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String codigoT = codigo.getText().toString();

        if (!codigoT.isEmpty()){

            int cantidad = bd.delete("libros","codigo='" +codigoT+"'",null);
            bd.close();

            codigo.setText("");
            nombre.setText("");
            clasificacion.setText("");
            ubicacion.setText("");

            if (cantidad==1){
                Toast.makeText(this,"Se elimino el libro",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"No existe el libro",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Debes ingresar un codigo para buscar",Toast.LENGTH_SHORT).show();
        }

    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        String codigoT = codigo.getText().toString();
        String nombreT = nombre.getText().toString();
        String clasificacionT = clasificacion.getText().toString();
        String ubicacionT = ubicacion.getText().toString();






        if ((!codigoT.isEmpty()) && (!nombreT.isEmpty()) && (!clasificacionT.isEmpty()) && (!ubicacionT.isEmpty())){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigoT);
            registro.put("nombre",nombreT);
            registro.put("clasificacion",clasificacionT);
            registro.put("ubicacion",ubicacionT);

            int cantidad = bd.update("libros",registro,"codigo='" +codigoT+"'",null);
            bd.close();

            if (cantidad == 1){
                Toast.makeText(this,"Editado Correctamente",Toast.LENGTH_SHORT).show();
                codigo.setText("");
                nombre.setText("");
                clasificacion.setText("");
                ubicacion.setText("");
            }else{
                Toast.makeText(this,"Libro No existe!!",Toast.LENGTH_SHORT).show();

            }


        }else{
            Toast.makeText(this,"Debes llenar todos los campos !!!",Toast.LENGTH_SHORT).show();
        }

    }
}