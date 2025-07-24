package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "clientes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLIENTES = "clientes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TELEFONE = "telefone";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLIENTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL UNIQUE," +
                    COLUMN_TELEFONE + " TEXT NOT NULL);"
            ;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        onCreate(db);
    }

    public long adicionarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, cliente.getNome());
        values.put(COLUMN_EMAIL, cliente.getEmail());
        values.put(COLUMN_TELEFONE, cliente.getTelefone());
        long newRowId = db.insert(TABLE_CLIENTES, null, values);
        db.close();
        return newRowId;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONE)));
                clientesList.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return clientesList;
    }

    public int atualizarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, cliente.getNome());
        values.put(COLUMN_EMAIL, cliente.getEmail());
        values.put(COLUMN_TELEFONE, cliente.getTelefone());
        int rowsAffected = db.update(TABLE_CLIENTES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
        return rowsAffected;
    }

    public void deletarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
    }
}
