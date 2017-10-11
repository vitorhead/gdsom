package com.example.vitor.geracaodosomgds;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vitor on 27/04/2017.
 */

public class StringToSHA1
{
    private  String convertToHex(byte[] data)
    {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public  String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String retorno = "Se estiver escrito erro, deu merda.";
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] sha1hash = md.digest();
            retorno =  convertToHex(sha1hash);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
           e.printStackTrace();

        }
        return retorno;
    }
}
