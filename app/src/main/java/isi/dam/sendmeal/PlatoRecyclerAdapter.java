
package isi.dam.sendmeal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public void onBindViewHolder(@NonNull final PlatoViewHolder holder, final int position) {

        Plato plato = dataSet.get(position);
        holder.titPlato.setText(plato.getNombre());
        holder.precPlato.setText("$"+plato.getPrecio().toString());
        holder.btnOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean b = !dataSet.get(position).getEnOferta();
                dataSet.get(position).setEnOferta(b);
                if(b){
                    holder.textoOferta.setVisibility(View.VISIBLE);
                }
                else{
                    holder.textoOferta.setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSet.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), dataSet.size());
            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditarItem.class);
                intent.putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlato;
        TextView titPlato, precPlato, textoOferta;
        CardView cardView;
        Button btnOferta, btnEditar, btnEliminar;
        public PlatoViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);
            btnOferta = itemView.findViewById(R.id.ofertaButton);
            btnEditar = itemView.findViewById(R.id.editarButton);
            btnEliminar = itemView.findViewById(R.id.eliminarButton);
            textoOferta = itemView.findViewById(R.id.textoOferta);
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