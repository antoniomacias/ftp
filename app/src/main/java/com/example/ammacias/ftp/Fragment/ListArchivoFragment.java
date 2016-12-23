package com.example.ammacias.ftp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ammacias.ftp.Clases.Archivo;
import com.example.ammacias.ftp.Interfaz.IArchivo;
import com.example.ammacias.ftp.Menu_lateral;
import com.example.ammacias.ftp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link IArchivo}
 * interface.
 */
public class ListArchivoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private IArchivo mListener;
    List<Archivo> archivoList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListArchivoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListArchivoFragment newInstance(int columnCount) {
        ListArchivoFragment fragment = new ListArchivoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archivo_list, container, false);

        Menu_lateral p = (Menu_lateral) getActivity();
        archivoList=p.getListadoArchivoFinal();

        /*for (Archivo a: archivoList) {
            System.out.println("Lista de archivos FRAGMENT; "+ a.getNombre());
        }*/

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            /*archivoList = new ArrayList<>();
            archivoList.add(new Archivo("Dam", R.drawable.folder_color, (long) 4.1));
            archivoList.add(new Archivo("Archivo", R.drawable.folder1_color, (long) 4.1));
            archivoList.add(new Archivo("Verano", R.drawable.folder1_color, (long) 4.1));
            archivoList.add(new Archivo("Dam2", R.drawable.folder_color, (long) 4.1));*/

            recyclerView.setAdapter(new MyArchivoRecyclerViewAdapter(archivoList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IArchivo) {
            mListener = (IArchivo) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IArchivo");
        }
    }

}
