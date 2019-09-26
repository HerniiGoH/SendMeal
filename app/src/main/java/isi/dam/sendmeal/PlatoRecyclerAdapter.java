
package isi.dam.sendmeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isi.dam.sendmeal.Domain.Plato;


public class PlatoRecyclerAdapter extends RecyclerView.Adapter<PlatoRecyclerAdapter.PlatoViewHolder> {

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder pvh = new PlatoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {

        Plato plato = dataSet.get(position);
        holder.titPlato.setText(plato.getNombre());
        holder.precPlato.setText("$"+plato.getPrecio().toString());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlato;
        TextView titPlato, precPlato;
        CardView cardView;
        public PlatoViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(itemView);
                }
            });
        }

    }

    public void showPopup(View view){

    }

    private List<Plato> dataSet;
    private Context context;

    public PlatoRecyclerAdapter(List<Plato>dataSet, Context context){
        this.dataSet=dataSet;
        this.context=context;
    }
}