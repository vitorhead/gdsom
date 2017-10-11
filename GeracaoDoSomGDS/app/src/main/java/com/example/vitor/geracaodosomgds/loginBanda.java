package com.example.vitor.geracaodosomgds;


import android.content.DialogInterface;
import android.content.Intent;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.EMAIL.EnviaEmail;
import com.example.vitor.geracaodosomgds.Modelos.BandaModel;
import com.example.vitor.geracaodosomgds.RecursosAdmin.telaadmin;
import com.example.vitor.geracaodosomgds.Repositorios.BandaRepo;

public class loginBanda extends AppCompatActivity {
    private EditText txt1, txt2;
    private Button btnEntrar, btnCadastro, btnEsqueci;
    String sAut = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_banda);
        getSupportActionBar().setTitle("LOGIN DE BANDAS");
        txt1 = (EditText) findViewById(R.id.txtEmail);
        txt2 = (EditText) findViewById(R.id.txtSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnEsqueci = (Button) findViewById(R.id.btnEsqueciSenha);

        btnEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esqueciSenha();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt1.getText().toString().equals("vitstipa") && txt2.getText().toString().equals("gdsom123"))
                {
                    Intent itAdmin = new Intent(loginBanda.this, telaadmin.class);
                    itAdmin.putExtra("nome", txt1.getText().toString());
                    startActivity(itAdmin);
                }
                else
                    entrarClick();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });
    }

    public void abrirCadastro() {
        Intent intent = new Intent(loginBanda.this, telacadastro.class);
        startActivity(intent);
    }

    public void entrarClick() {
        if (camposVazios(txt1, txt2)) {
            Toast.makeText(getApplicationContext(), "CAMPOS INVÁLIDOS", Toast.LENGTH_SHORT).show();
        } else {
            String login = txt1.getText().toString();
            String senha = txt2.getText().toString();
            try {
                senha = new StringToSHA1().SHA1(senha);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            BandaModel bmLogin = new BandaRepo(this).selectUmaBanda(login, senha);
            if (bmLogin != null) {
                if (bmLogin.getNumacesso() == 1) {
                    primeiroAcesso();
                } else {
                    dados_SessaoIniciada dadosLogado = new dados_SessaoIniciada();
                    for (int i = 0; i <= 6; i++)
                        sAut = sAut + dados_GeraAutenticacao.geraCodigo();
                    dadosLogado.setAutenticacao(sAut);
                    dadosLogado.setLogin(bmLogin.getLogin());
                    dadosLogado.setNomebanda(bmLogin.getNome());
                    dadosLogado.setDesc(bmLogin.getDescricao());
                    dadosLogado.setSite(bmLogin.getSite());
                    dadosLogado.setTelefone(bmLogin.getTelefone());
                    dadosLogado.setFoto(bmLogin.getFoto());
                    Intent intent = new Intent(loginBanda.this, perfilbanda.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "LOGIN E/OU SENHA INCORRETOS", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean camposVazios(EditText txt1, EditText txt2) {
        return ((txt1.getText().toString().trim().length() <= 0) || (txt2.getText().toString().trim().length() <= 0));
    }

    public void primeiroAcesso() {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("PRIMEIRO ACESSO");
        b.setTitle("Digite no campo abaixo uma nova senha para ser usada para o login.");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);


        final EditText edtSenha = new EditText(this);
        edtSenha.setHint("NOVA SENHA");
        edtSenha.setTextSize(18f);
        edtSenha.setSingleLine();
        edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ll_dialogo.addView(edtSenha, lp);

        b.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtSenha.getText().toString().trim().length() <= 0)
                    Toast.makeText(getApplicationContext(), "SENHA INVALIDA", Toast.LENGTH_SHORT);
                else {
                    String senha = edtSenha.getText().toString();
                    try {
                        senha = new StringToSHA1().SHA1(senha);
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    new BandaRepo(b.getContext()).updateBandaSenhaFIRSTACCESS(txt1.getText().toString(), senha, 2);
                    Toast.makeText(getApplicationContext(), "Senha salva! Utilize-a para fazer seu login.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setView(ll_dialogo);
        AlertDialog a = b.create();
        a.show();
    }

    public void esqueciSenha()
    {
        final AlertDialog.Builder bSenha = new AlertDialog.Builder(this);
        bSenha.setTitle("RESETAR SENHA");
        bSenha.setMessage("Ao clicar em aceitar, geraremos uma nova senha e te enviaremos por e-mail. \nDeseja resetar a senha?");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtEmail = new EditText(this);
        edtEmail.setTextSize(20f);
        edtEmail.setHint("Digite seu email cadastrado");
        edtEmail.setSingleLine();
        ll_dialogo.addView(edtEmail, lp);

        bSenha.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //faznada
            }
        });

        bSenha.setPositiveButton("ACEITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = edtEmail.getText().toString().trim();
                resetaSenha(email);
            }
        });
        bSenha.setView(ll_dialogo);
        final AlertDialog adResetaSenha = bSenha.create();
        adResetaSenha.show();
    }
    public void resetaSenha(String email)
    {
        BandaModel bandaReset = new BandaRepo(this).selectUmaBanda(email);
        if (bandaReset == null)
            Toast.makeText(getApplicationContext(), "Não foi possível localizar a banda informada.", Toast.LENGTH_SHORT).show();
        else
        {
            String novaSenha = "";
            for (int i = 0; i <= 8; i++) { novaSenha = novaSenha+new dados_GeraAutenticacao().geraCodigo(); }
            String corpoMensagem =  "<html>" +
                                    "<head>" +
                                    "</head>" +
                                    "<body>" +
                                    "     <div id=Topo style='background-color:#8BC34A;text-align:center;border-style:solid;border-color:#8BC34A;width:100%;float:top;margin:auto'>" +
                                    "         <p><font color=white face=verdana>GERAÇÃO DO SOM - NOVA SENHA</font></p>" +
                                    "     </div>" +
                                    "     <div id=Texto style='text-align:justify;background-color:white;border-style:solid ;border-color:#8BC34A;width:100%;'>" +
                                    "          <p style='margin-left: 6px'><font face=verdana>  Sua senha foi resetada com sucesso.</font></p>" +
                                    "     <p style='margin-left: 6px'><font face=verdana>  Utilize a senha <font color=#4CAf50>"+novaSenha+" </font>para realizar o acesso.</font></p>" +
                                    "     </div>" +
                                    "</body>" +
                                    "</html>";
            try{
                novaSenha = new StringToSHA1().SHA1(novaSenha);
            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                e.printStackTrace();
            }
            bandaReset.setSenha(novaSenha);
            new BandaRepo(this).updateBandaSenhaFIRSTACCESS(bandaReset.getLogin(), bandaReset.getSenha(), 1);
            EnviaEmail eeSenhaReset = new EnviaEmail(this,
                                                        email,
                                                        "GERAÇÃO DO SOM - SENHA RESETADA",
                                                        corpoMensagem, 2);
            eeSenhaReset.execute();
        }

    }
}
