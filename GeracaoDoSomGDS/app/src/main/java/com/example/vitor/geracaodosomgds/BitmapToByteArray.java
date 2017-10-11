package com.example.vitor.geracaodosomgds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Vitor on 15/05/2017.
 * Classe que converte a foto em bitmap para byte (ava)
 * Basicamente para ser usada no cadastro e alteracao
 */

public class BitmapToByteArray {
    // DE BITMAP PARA BYTE
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // DE BYTE PARA BITMAP
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
