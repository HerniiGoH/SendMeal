package isi.dam.sendmeal.RecyclerAdapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.R;

public class ItemsPedidoInfoRecyclerAdapter extends RecyclerView.Adapter<ItemsPedidoInfoRecyclerAdapter.ItemsPedidoInfoViewHolder> {
    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage (Message m){
            Log.d("APP_2", "VUELVE AL HANDLER"+ m.arg1);
            switch ( m.arg1){
                case Plato_repo._BORRADO_PLATO:
                    notifyDataSetChanged();
                    break;
                case Plato_repo._UPDATE_PLATO:
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    @NonNull
    @Override
    public ItemsPedidoInfoRecyclerAdapter.ItemsPedidoInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_item_info_pedido, parent, false);
        ItemsPedidoInfoRecyclerAdapter.ItemsPedidoInfoViewHolder pvh = new ItemsPedidoInfoRecyclerAdapter.ItemsPedidoInfoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsPedidoInfoRecyclerAdapter.ItemsPedidoInfoViewHolder holder, final int position) {

        final ItemsPedido plato = dataSet.get(position);
        holder.titPlato.setText(plato.getPlato().getNombre());
        holder.precPlato.setText("$"+plato.getPrecioPlato().toString());

        if( dataSet.get(position).getPlato().getEnOferta()){
            holder.imagenOferta.setVisibility(View.VISIBLE);
        }
        else{
            holder.imagenOferta.setVisibility(View.INVISIBLE);
        }
        holder.cantidad.setEnabled(false);
        holder.cantidad.getEditText().setText(dataSet.get(position).getCantidad().toString());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ItemsPedidoInfoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlato, imagenOferta;
        TextView titPlato, precPlato;
        CardView cardView;
        TextInputLayout cantidad;

        public ItemsPedidoInfoViewHolder(@NonNull final View itemView) {
            super(itemView);
            imagenOferta = itemView.findViewById(R.id.imagenOferta);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);
            cantidad = itemView.findViewById(R.id.ingreso_cantidad);
        }

    }

    private List<ItemsPedido> dataSet;
    private Context context;
    private TextView total;


    public ItemsPedidoInfoRecyclerAdapter(List<ItemsPedido>dataSet, Context context, TextView total){
        this.dataSet=dataSet;
        this.context=context;
        this.total = total;
    }


}
