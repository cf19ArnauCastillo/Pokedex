package com.example.pokedex;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ahmadrosid.svgloader.SvgLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class fetchDataType extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String type;
    protected ArrayList<String> pokemons;

    public fetchDataType(String type) {
        this.type = type;
        pokemons = new ArrayList<String>();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type/" + type);
            Log.i("logtest", "https://pokeapi.co/api/v2/type/" + type);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;
        String img = "";
        String typeName = "";
        String typeObj="";

        try {
            jObject = new JSONObject(data);


            // Get type/types
            JSONArray pokemonsList = new JSONArray(jObject.getString("pokemon"));
            for(int i=0; i<pokemonsList.length(); i++){
                JSONObject pokemon = new JSONObject(pokemonsList.getString(i));
                JSONObject pokemon2  = new JSONObject(pokemon.getString("pokemon"));
                Log.i("logtest", pokemon2.getString("name"));

                pokemons.add(pokemon2.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}