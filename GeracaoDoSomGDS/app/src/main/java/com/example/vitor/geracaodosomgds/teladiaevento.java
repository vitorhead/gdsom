package com.example.vitor.geracaodosomgds;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.Modelos.ReservaModel;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.ReservaRepo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class teladiaevento extends AppCompatActivity
{
    TextView txtDia, txtArtistas, txtArtistaEvento, txtInfoEvento;
    int diaClicado, cdevento;
    Button btnReserva;
    String dtevento, qtdeingressos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teladiaevento);

        diaClicado = getIntent().getExtras().getInt("diaClicado");
        cdevento = getIntent().getExtras().getInt("cdevento");
        dtevento = getIntent().getExtras().getString("dtevento");
        qtdeingressos = getIntent().getExtras().getString("qtdeingressos");
        editaEvento(diaClicado);

        btnReserva = (Button) findViewById(R.id.btnReserva);
        btnReserva.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                abreReserva();
            }
        });
    }

    public void abreReserva()
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("RESERVA");
        b.setMessage("Digite seu nome e CPF\nLembre-se de levar o documento com foto na entrada do evento!");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtNome = new EditText(this);
        edtNome.setHint("Digite seu nome completo");
        edtNome.setTextSize(18f);
        ll_dialogo.addView(edtNome, lp);

        final EditText edtCPF = new EditText(this);
        edtCPF.setHint("Digite seu cpf");
        edtCPF.setTextSize(18f);
        edtCPF.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll_dialogo.addView(edtCPF, lp);

        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtCPF.equals("") || edtNome.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "PREENCHA OS CAMPOS", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (isCPF(edtCPF.getText().toString()) == false)
                        Toast.makeText(getApplicationContext(), "CPF INVÁLIDO", Toast.LENGTH_SHORT).show();
                    else
                    {
                        List<ReservaModel> qtdeReservas = new ArrayList<ReservaModel>();
                        qtdeReservas = new ReservaRepo(getApplicationContext()).selectAllReservas(cdevento);
                        if (qtdeReservas.size() >= Integer.valueOf(qtdeingressos))
                        {
                            Toast.makeText(getApplicationContext(), "Desculpe, atingimos o número maximo de reservas para esse evento =(", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            ReservaModel rm = new ReservaModel();
                            rm.setNome(edtNome.getText().toString());
                            rm.setCpf(edtCPF.getText().toString());
                            rm.setCdEvento(cdevento);
                            rm.setDtEvento(dtevento);
                            new ReservaRepo(getApplicationContext()).insertReserva(rm);
                            Toast.makeText(getApplicationContext(), "Reserva efetivada com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        b.setNegativeButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        b.setView(ll_dialogo);
        final AlertDialog reserva = b.create();
        reserva.show();
    }

    public void editaEvento(int diaClicado)
    {
        txtDia = (TextView) findViewById(R.id.txtDia);
        txtArtistas = (TextView) findViewById(R.id.txtArtistas);
        txtArtistaEvento = (TextView) findViewById(R.id.txtArtistaEvento);
        txtInfoEvento = (TextView) findViewById(R.id.txtInfoEvento);
        switch(diaClicado)
        {

//            case 28:
//                txtDia.setText("" + diaClicado);
//                txtArtistas.setText("Black Label Society");
//                txtArtistaEvento.setText("Heavy Metal");
//                txtInfoEvento.setText(  "Zakk Wylde – vocais, guitarra\n\n" +
//                        "Dario Lorina - guitarra \n\n" +
//                        "John DeServio – baixo \n\n" +
//                        "Jeff Fabb – bateria");
//                break;
//
//            case 04:
//                txtDia.setText("" + diaClicado);
//                txtArtistas.setText("Red Hot Chili Peppers");
//                txtArtistaEvento.setText("Funk Rock");
//                txtInfoEvento.setText(  "Anthony Kiedis - vocais\n\n"+
//                        "Flea - baixo\n\n"+
//                        "Chad Smith - bateria\n\n"+
//                        "Josh Klinghoffer - guitarra\n\n");
//                break;
//
//            case 16:
//                txtDia.setText("" + diaClicado);
//                txtArtistas.setText("Bad Meets Evil");
//                txtArtistaEvento.setText("Horrorcore");
//                txtInfoEvento.setText(  "Eminem\n\n"+
//                                    "Royce da 5'9\"");
//                break;

            default:
                List<String> formatar = new ApresentacaoRepo(this).retornaApresentacao(String.valueOf(cdevento));
                txtDia.setText(""+diaClicado);
                txtArtistas.setText(formatar.get(0));
                txtArtistaEvento.setText(formatar.get(1));
                String infoEvento;
                switch(formatar.get(2))
                {
                    case "AG":
                         infoEvento = "aguardando confirmação";
                        break;
                    case "S":
                        infoEvento = "confirmado";
                        break;
                    case "N":
                        infoEvento = "recusado";
                        btnReserva.setEnabled(false);
                        break;
                    default:
                        infoEvento = "aguardando confirmação";
                        break;
                }
                txtInfoEvento.setText(infoEvento); // status

        }
//        }

    }

    public static boolean isCPF(String CPF) {
// considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
// converte o i-esimo caractere do CPF em um numero:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

// Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

}
