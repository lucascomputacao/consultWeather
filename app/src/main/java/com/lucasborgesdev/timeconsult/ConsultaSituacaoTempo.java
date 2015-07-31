package com.lucasborgesdev.timeconsult;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lucas on 30/07/15.
 */
public class ConsultaSituacaoTempo extends AsyncTask<Void, Void, String> {

    private ConsultaSituacaoTempoListener listener;

    private static String cidade = "Salvador";
    private static final String URL_STRING = "http://api.openweathermap.org/data/2.5/weather?q=" + cidade + ",br&lang=pt&units=metric";

    public ConsultaSituacaoTempo(ConsultaSituacaoTempoListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground (Void... params) {

        try {
            String resultado = consultaServidor();

            return interpretaResultado(resultado);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    * Interpreta o resultado vindo do servidor
    * */
    private String interpretaResultado(String resultado) throws JSONException {

        JSONObject object = new JSONObject(resultado);

        JSONArray jsonArray = object.getJSONArray("weather");
        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);

        // descrição do tempo na cidade
        String descricao = jsonObjectWeather.getString("description").toUpperCase();

        // nome da cidade
        String name = object.getString("name");

        JSONObject  main = object.getJSONObject("main");
        int temperatura = main.getInt("temp");
        int humidade = main.getInt("humidity");
        int tempMax = main.getInt("temp_max");
        int tempMin = main.getInt("temp_min");

        JSONObject wind = object.getJSONObject("wind");
        int velocVento = wind.getInt("speed");

        //JSONObject sys = object.getJSONObject("sys");
        //String pais = sys.getString("country");

        return "Condições Climáticas em " + name + ":"
                + "\n" + descricao
                + "\nTemperatura Atual: " + temperatura + "ºC"
                + "\nHumidade relativa do ar: " + humidade + "%"
                + "\nTemperatura Mínima: " + tempMin + "ºC"
                + "\nTemperatura Máxima: " + tempMax + "ºC"
                + "\nVelocidade do Vento: " + velocVento + "m/s";
    }

    /*
    * Comunica com o servidor
    * */
    private String consultaServidor() throws IOException{
        InputStream is = null;
        try{
            URL url = new URL(URL_STRING);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            conn.getResponseCode();

            is = conn.getInputStream();

            Reader reader = null;
            reader = new InputStreamReader(is);
            char[] buffer = new char[2048];
            reader.read(buffer);
            return new String(buffer);

        } finally {
            if(is!=null){
                is.close();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onConsultaConcluida(result);
    }

    public interface ConsultaSituacaoTempoListener{
        void onConsultaConcluida(String situacaoTempo);
    }
}
