package com.example.ammacias.ftp.Conect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.ammacias.ftp.Clases.Archivo;
import com.example.ammacias.ftp.Interfaz.IArchivo;
import com.example.ammacias.ftp.LoginActivity;
import com.example.ammacias.ftp.Menu_lateral;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ammacias on 16/12/2016.
 */

public class FTPConnection extends AsyncTask<Void,Void,Boolean> {

    // IP o nombre dominio del servidor ftp
    public static final String URL_FTP = "";
    public static final int URL_FTP_PORT = 21;
    FTPClient mFTPClient;
    List<String> parametros;
    Context ctx;
    IArchivo interfaz;
    static List<Archivo> listadoArchivos;
    String archivo="";

    public static List<Archivo> getListadoArchivos() {
        return listadoArchivos;
    }

    public FTPConnection(List<String> parametros, Context ctx) {
        this.parametros = parametros;
        this.ctx = ctx;
        this.interfaz = (IArchivo) ctx;
    }
    public FTPConnection(List<String> parametros, Context ctx, String nombreArchivo) {
        this.parametros = parametros;
        this.ctx = ctx;
        this.interfaz = (IArchivo) ctx;
        this.archivo = nombreArchivo;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        mFTPClient = new FTPClient();
        boolean status=false;
        // connecting to the host
        try {

            mFTPClient.connect(parametros.get(0).toString(), URL_FTP_PORT);
            //Para poder listar
            mFTPClient.enterLocalPassiveMode();
            //boolean status = mFTPClient.login("u740106234", "DeMuXe");
            status = mFTPClient.login(parametros.get(1).toString(), parametros.get(2).toString());




            if (archivo!=""){
                // Changing working directory
                boolean result = mFTPClient.changeWorkingDirectory("/"+archivo+"/");

                if (result == true) {
                    System.out.println("Working directory is changed.Your New working directory:");
                } else {
                    System.out.println("Unable to change");
                }/*
                System.out.println("Cambio directorio a /"+archivo);
                mFTPClient.changeWorkingDirectory("/"+archivo);
                */
                System.out.println("Estoy en : "+mFTPClient.printWorkingDirectory());
                listadoArchivos = ftpPrintFilesList(parametros.get(0).toString()+mFTPClient.printWorkingDirectory());
            }else{
                listadoArchivos = ftpPrintFilesList(parametros.get(0).toString()+mFTPClient.printWorkingDirectory());
            }
            Log.i("*** DIR FTP ***","**** "+mFTPClient.printWorkingDirectory());

            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                mFTPClient.setFileType(FTP.ASCII_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);

        if(aVoid){
            interfaz.doAfterFTRConect();
        }else{
            Toast.makeText(ctx, "Imposible conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Archivo> ftpPrintFilesList(String dir_path){
        List<Archivo> listado;
        listado = new ArrayList<>();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


            FTPFile[] ftpFiles = mFTPClient.listFiles();

            int length = ftpFiles.length;
            int auxiliar=0;
            for (int i = 0; i < length; i++) {
                String name = ftpFiles[i].getName();
                boolean isFile = ftpFiles[i].isFile();

                /*System.out.println("Type; "+ftpFiles[i].getType());
                System.out.println("Time; "+ftpFiles[i].getTimestamp());
                System.out.println("Group; "+ftpFiles[i].getGroup());
                System.out.println("Link; "+ftpFiles[i].getLink());
                System.out.println("RawListing; "+ftpFiles[i].getRawListing());*/

                if (isFile) {
                    auxiliar = 0;
                    /*System.out.println(name);
                    Log.i("*** FTP ***", "File : " + name);*/
                }
                else {
                    auxiliar = 1;
                    /*System.out.println(name);
                    Log.i("*** FTP ***", "Directory : " + name);*/
                }
                listado.add(new Archivo(ftpFiles[i].getName(), auxiliar,ftpFiles[i].getSize(), dir_path));
                for (Archivo a:listado ) {
                    System.out.println("CONNECT: "+a.getNombre());
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return listado;
    }
}
