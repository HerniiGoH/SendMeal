package isi.dam.sendmeal.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isi.dam.sendmeal.Activities.Info_Pedido;
import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.R;


public class PedidoRecyclerAdapter extends RecyclerView.Adapter<PedidoRecyclerAdapter.PedidoViewHolder> {

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_pedido, parent, false);
        PedidoViewHolder pvh = new PedidoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PedidoViewHolder holder, final int position) {

        final Pedido pedido = dataSet.get(position);
        holder.textView.setText("Pedido: "+pedido.getIdPedido());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Info_Pedido.class);
                intent.putExtra("pos", dataSet.indexOf(pedido));
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class PedidoViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView;
        ImageView imageView;

        public PedidoViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewPedido);
            textView = itemView.findViewById(R.id.tvPedido);
            imageView = itemView.findViewById(R.id.imagenPedido);
        }

    }

    private List<Pedido> dataSet;
    private Context context;

    public PedidoRecyclerAdapter(List<Pedido>dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }
}
