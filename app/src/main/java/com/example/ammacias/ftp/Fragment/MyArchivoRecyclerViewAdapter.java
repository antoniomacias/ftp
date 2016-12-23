package com.example.ammacias.ftp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ammacias.ftp.Clases.Archivo;
import com.example.ammacias.ftp.Interfaz.IArchivo;
import com.example.ammacias.ftp.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IArchivo} and makes a call to the
 * specified {@link Archivo}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyArchivoRecyclerViewAdapter extends RecyclerView.Adapter<MyArchivoRecyclerViewAdapter.ViewHolder> {

    private final List<Archivo> mValues;
    private final IArchivo mListener;

    public MyArchivoRecyclerViewAdapter(List<Archivo> items, IArchivo listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_archivo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombre.setText(mValues.get(position).getNombre());
        if (mValues.get(position).getIcono() == 0){
            holder.icono.setImageResource(R.drawable.folder1_color);
        }else{
            holder.icono.setImageResource(R.drawable.folder_color);
        }

        holder.peso.setText(mValues.get(position).getPeso()+"kb");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClickArchivo(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final ImageView icono;
        public final TextView peso;
        public Archivo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.nombreArchivo);
            icono = (ImageView) view.findViewById(R.id.imagenArchivo);
            peso = (TextView) view.findViewById(R.id.pesoArchivo);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
