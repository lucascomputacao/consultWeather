package com.lucasborgesdev.timeconsult;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.lucasborgesdev.timeconsult.ConsultaSituacaoTempo.ConsultaSituacaoTempoListener;

public class MainActivity extends AppCompatActivity implements ConsultaSituacaoTempoListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new ConsultaSituacaoTempo(this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            String dialog_title = "ConsultWeather";
            String dialog_message = "Este aplicativo busca dados climáticos da API openweathermap.org";

            // criar dialog do menu sobre este app
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setando Título do dialog
            alertDialog.setTitle(dialog_title);
            // Mensagem do dialog
            alertDialog.setMessage(dialog_message);
            //
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // ação ao clicar no botão OK
                }
            });

            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConsultaConcluida(String situacaoTempo){
        TextView text = (TextView) findViewById(R.id.main_resultado_servidor);
        text.setText(situacaoTempo);
    }
}
