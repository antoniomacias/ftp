package com.example.ammacias.ftp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ammacias.ftp.Clases.Archivo;
import com.example.ammacias.ftp.Conect.FTPConnection;
import com.example.ammacias.ftp.Dialog.DescargaDialog;
import com.example.ammacias.ftp.Interfaz.IArchivo;

import java.util.ArrayList;
import java.util.List;


public class Menu_lateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IArchivo {

    List<String> parametros;
    EditText directory;
    List<Archivo> listadoArchivoFinal;

    public List<Archivo> getListadoArchivoFinal() {
        return listadoArchivoFinal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listadoArchivoFinal = new ArrayList<>(FTPConnection.getListadoArchivos());
        setContentView(R.layout.activity_menu_lateral);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        parametros = new ArrayList<>();
        parametros.add(getIntent().getExtras().getString("hostname"));
        parametros.add(getIntent().getExtras().getString("user"));
        parametros.add(getIntent().getExtras().getString("pw"));

        directory = (EditText)findViewById(R.id.directory);
        directory.setText(listadoArchivoFinal.get(0).getRuta().split("esy.es")[1]);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClickArchivo(Archivo a) {
        Toast.makeText(this, "Icono: "+a.getIcono(), Toast.LENGTH_SHORT).show();
        //Si es file
        if(a.getIcono()==0){
            Toast.makeText(this, "Muestro Dialog", Toast.LENGTH_SHORT).show();
            mostrarDialog();
        //Si es folder
        }else{
            new FTPConnection(parametros, Menu_lateral.this, a.getNombre()).execute();
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();

        }
    }

    private void mostrarDialog() {
        DialogFragment dialogoBusqueda = new DescargaDialog();
        dialogoBusqueda.show(getSupportFragmentManager(), "Dialogo");
        dialogoBusqueda.setCancelable(false);
        dialogoBusqueda.onDismiss(new DialogInterface() {
            @Override
            public void cancel() {

            }

            @Override
            public void dismiss() {

            }
        });
    }

    @Override
    public void doAfterFTRConect() {
        //Recargo fragment
    }
}
