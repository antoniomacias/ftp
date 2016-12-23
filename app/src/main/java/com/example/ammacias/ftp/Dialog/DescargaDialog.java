package com.example.ammacias.ftp.Dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ammacias.ftp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescargaDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_descarga_dialog, null);

        EditText texto = (EditText) v.findViewById(R.id.descarga);

        Button btn = (Button) v.findViewById(R.id.button_download);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Descargando...", Toast.LENGTH_SHORT).show();
                DescargaDialog.this.getDialog().cancel();
            }
        });

        builder.setView(v);
        builder.setMessage("Buscar una ciudad")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
