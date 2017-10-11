package com.example.vitor.geracaodosomgds;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.EMAIL.EnviaEmail;
import com.example.vitor.geracaodosomgds.Modelos.BandaModel;
import com.example.vitor.geracaodosomgds.Repositorios.BandaRepo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.example.vitor.geracaodosomgds.BitmapToByteArray.getBytes;
import static com.example.vitor.geracaodosomgds.BitmapToByteArray.getImage;

public class telacadastro extends AppCompatActivity {
    private EditText txtNome, txtEmail, txtTelefone, txtSite, txtDescricao;
    private Button btnConfirmar, btnFoto;
    private Boolean bEdita;
    private String loginEdita;
    private int FOTO_CODIGO = 1;
    private Bitmap bitmapFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telacadastro);
        getSupportActionBar().setTitle("CADASTRO DE BANDAS");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        if (getIntent().getExtras() != null)
            bEdita = getIntent().getExtras().getBoolean("bEdita");
        else
            bEdita = false;

        // Inicializo a foto aqui caso ele nao mande uma no cadastro, essa é a default
        bitmapFoto = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                                    R.drawable.vinyl);

        txtNome =  (EditText) findViewById(R.id.txtNomeCadastro);
        txtEmail =  (EditText) findViewById(R.id.txtEmail);
        txtTelefone =  (EditText) findViewById(R.id.txtTelefone);
        txtSite = (EditText) findViewById(R.id.txtSite);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmarCadastro);
        btnFoto = (Button) findViewById(R.id.btnFoto);;

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getText().toString().contains("@")) //validacao besta no lugar mais besta ainda
                {
                    executaCadastro();
                }
                else
                    Toast.makeText(getApplicationContext(), "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iFoto = new Intent();
                iFoto.setType("image/*");
                iFoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(iFoto.createChooser(iFoto, "Selecione uma foto"), FOTO_CODIGO);
            }
        });

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            loginEdita = extras.get("login").toString();
            if (bEdita) {
                //significa que a tela vai ser usada para Editar os dados de uma banda, vamos fazer o select
                //e trazer os dados
                BandaModel bmLogin = new BandaRepo(this).selectEdita(loginEdita);
                txtNome.setText(bmLogin.getNome());
                txtEmail.setEnabled(false);
                txtEmail.setText(bmLogin.getEmail());
                txtTelefone.setText(bmLogin.getTelefone());
                txtSite.setText(bmLogin.getSite());
                txtDescricao.setText(bmLogin.getDescricao());
                bitmapFoto = getImage(bmLogin.getFoto());
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (    requestCode == FOTO_CODIGO &&
                resultCode == RESULT_OK &&
                data != null && data.getData() !=null){
            Uri uri = data.getData();
            try
            {
                bitmapFoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void concluirCadastro(View view)
    {
        if(camposVazios()==false)
        {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setMessage("Verifique o preenchimento dos campos");
            alerta.setCancelable(true);

            alerta.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog msgErroCampoVazio = alerta.create();
            msgErroCampoVazio.show();

        }
        else
        {

        }
    }

    public boolean camposVazios()
    {
        return( ( txtNome.getText().toString().trim().equals("")) ||
                ( txtEmail.getText().toString().trim().equals("")) ||
                ( txtTelefone.getText().toString().trim().equals("")) ||
                ( txtSite.getText().toString().equals("")) );
    }

    public void executaCadastro() {
        if (!camposVazios()) {
            String nome = txtNome.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String senha = "";
            for (int i = 0; i <= 5; i++ ) { senha = senha+dados_GeraAutenticacao.geraCodigo(); }
            String site = txtSite.getText().toString().trim();
            String descricao = txtDescricao.getText().toString();
            byte[] foto = getBytes(bitmapFoto);
            String telefone = txtTelefone.getText().toString().trim();

            BandaModel bandaCadastrar = new BandaModel();
            bandaCadastrar.setNome(nome);
            bandaCadastrar.setEmail(email);
            bandaCadastrar.setTelefone(telefone);
            bandaCadastrar.setDescricao(descricao);
            bandaCadastrar.setSite(site);
            bandaCadastrar.setFoto(foto);

            if (bEdita) {
                bandaCadastrar.setLogin(loginEdita);
                bandaCadastrar.setNumacesso(2);
                new BandaRepo(this).updateBanda(bandaCadastrar);
                Toast.makeText(getApplicationContext(), "Registros alterados com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                String corpoEmail = "<html>" +
                                    "<head>" +
                                    "</head>" +
                                    "<body>" +
                                    "     <div id=Topo style='background-color:#8BC34A;text-align:center;border-style:solid;border-color:#8BC34A;width:100%;float:top;margin:auto'>" +
                                    "         <p><font color=white face=verdana>GERAÇÃO DO SOM - CADASTRO FEITO COM SUCESSO!</font></p>" +
                                    "     </div>" +
                                    "     <div id=Texto style='text-align:justify;background-color:white;border-style:solid ;border-color:#8BC34A;width:100%;'>" +
                                    "          <p style='margin-left: 6px'><font face=verdana>  Obrigado pelo cadastro no nosso sistema! =)</font></p>" +
                                    "     <p style='margin-left: 6px'><font face=verdana>  Utilize a senha <font color=#4CAf50>"+senha+" </font>para realizar o primeiro acesso.</font></p>" +
                                    "     </div>" +
                                    "</body>" +
                                    "</html>";
                bandaCadastrar.setLogin(email);
                try{
                    senha = new StringToSHA1().SHA1(senha);
                }
                catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                bandaCadastrar.setSenha(senha);
                bandaCadastrar.setNumacesso(1);
                new BandaRepo(this).insert(bandaCadastrar);
                EnviaEmail ee = new EnviaEmail(this, email, "GERAÇÃO DO SOM - CADASTRO", corpoEmail, 1);
                ee.execute();
            }
        }
        else {
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setTitle("ALERTA");
            ab.setMessage("É necessário que todos os campos sejam preenchidos para efetuar o cadastro.");
            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            final AlertDialog atencaoCad = ab.create();
            atencaoCad.show();
        }
    }

}
