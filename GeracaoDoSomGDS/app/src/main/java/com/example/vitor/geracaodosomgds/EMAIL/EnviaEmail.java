package com.example.vitor.geracaodosomgds.EMAIL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Vitor on 20/05/2017.
 */

public class EnviaEmail extends AsyncTask<Void,Void,Void>  {
    private Context c;
    private Session s;

    private String email;
    private String assunto;
    private String mensagem;
    private Integer operacao;

    private ProgressDialog progressDialog;

    public EnviaEmail(Context c, String email, String assunto, String mensagem, Integer operacao)
    {
        this.c = c;
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.operacao = operacao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(c,"Enviando email...","Aguarde",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        switch(operacao) {
            case 1: //Cadastro
                ((Activity) c).finish();
                Toast.makeText(c, "Cadastro realizado com sucesso! Verifique seu email para prosseguir!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                break;

            case 2: //Reset Senha
                progressDialog.dismiss();
                Toast.makeText(c, "Enviamos um email com uma nova senha.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        s = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configEmail.EMAIL, configEmail.SENHA);
            }
        });

        try
        {
            MimeMessage m = new MimeMessage(s);

            m.setFrom(new InternetAddress(configEmail.EMAIL));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            m.setSubject(assunto);
            m.setText(mensagem);
            Transport.send(m);

        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
