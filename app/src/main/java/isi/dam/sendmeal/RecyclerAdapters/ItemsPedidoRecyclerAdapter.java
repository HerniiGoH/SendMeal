package isi.dam.sendmeal.RecyclerAdapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
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

public class ItemsPedidoRecyclerAdapter extends RecyclerView.Adapter<ItemsPedidoRecyclerAdapter.ItemsPedidoViewHolder>  {


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
    public ItemsPedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_item_pedido, parent, false);
        ItemsPedidoViewHolder pvh = new ItemsPedidoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsPedidoViewHolder holder, final int position) {

        final ItemsPedido plato = dataSet.get(position);
        byte[] b = Base64.decode(plato.getPlato().getFoto(), Base64.DEFAULT);
        holder.imgPlato.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
        holder.titPlato.setText(plato.getPlato().getNombre());
        holder.precPlato.setText("$"+plato.getPrecioPlato().toString());

        if( dataSet.get(position).getPlato().getEnOferta()){
            holder.imagenOferta.setVisibility(View.VISIBLE);
        }
        else{
            holder.imagenOferta.setVisibility(View.INVISIBLE);
        }
        holder.cantidad.getEditText().setText(dataSet.get(position).getCantidad().toString());

        holder.btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataSet.get(position).setCantidad(dataSet.get(position).getCantidad()+1);
                holder.cantidad.getEditText().setText(dataSet.get(position).getCantidad().toString());

                dataSet.get(position).setPrecioPlato(dataSet.get(position).getPlato().getPrecio()*dataSet.get(position).getCantidad());
                total.setText(String.valueOf(Double.valueOf(total.getText().toString())+dataSet.get(position).getPlato().getPrecio()));
                notifyDataSetChanged();
            }
        });

        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataSet.get(position).getCantidad()>0){
                dataSet.get(position).setCantidad(dataSet.get(position).getCantidad()-1);
                holder.cantidad.getEditText().setText(dataSet.get(position).getCantidad().toString());
                    dataSet.get(position).setPrecioPlato(dataSet.get(position).getPlato().getPrecio()*dataSet.get(position).getCantidad());
                    total.setText(String.valueOf(Double.valueOf(total.getText().toString())-dataSet.get(position).getPlato().getPrecio()));
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ItemsPedidoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlato, imagenOferta;
        TextView titPlato, precPlato;
        CardView cardView;
        Button btnMas, btnMenos;
        TextInputLayout cantidad;
        public ItemsPedidoViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenOferta = itemView.findViewById(R.id.imagenOferta);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);
            btnMas = itemView.findViewById(R.id.mas_platos);
            btnMenos = itemView.findViewById(R.id.menos_platos);
            cantidad = itemView.findViewById(R.id.ingreso_cantidad);
        }

    }

    private List<ItemsPedido> dataSet;
    private Context context;
    private TextView total;


    public ItemsPedidoRecyclerAdapter(List<ItemsPedido>dataSet, Context context, TextView total){
        this.dataSet=dataSet;
        this.context=context;
        this.total = total;
    }

}
