package com.example.vitor.geracaodosomgds;

import java.util.Random;

/**
 * Created by Vitor on 21/04/2017.
 */

public class dados_GeraAutenticacao
{
    private static String fixa = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String geraCodigo()
    {
        Random r = new Random();
        return String.valueOf(fixa.charAt(r.nextInt(fixa.length())));
    }
}
