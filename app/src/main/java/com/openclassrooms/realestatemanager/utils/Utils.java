package com.openclassrooms.realestatemanager.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils extends AsyncTask<Void,Void,Boolean> {

    private Consumer mConsumer;

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroDollar(int euros){
        return (int) Math.round(euros * 1.188);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */

    @Override
    protected Boolean doInBackground(Void... voids) {
        return isInternetAvailable();
    }

    public  interface Consumer {
        void accept(Boolean internet);
    }

    public  Utils(Consumer consumer) {
        this.mConsumer = consumer;
        execute();
    }

    public static Boolean isInternetAvailable(){
        Socket sock = new Socket();
        try {
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        mConsumer.accept(internet);
    }

    /*
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifi != null;
        return wifi.isWifiEnabled();


        ConnectivityManager cm =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnectedOrConnecting();
    }
     */
}
