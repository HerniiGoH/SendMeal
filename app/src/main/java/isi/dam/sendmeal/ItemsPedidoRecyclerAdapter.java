package isi.dam.sendmeal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Plato;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
        holder.titPlato.setText(plato.getPlato().getNombre());
        holder.precPlato.setText("$"+plato.getPrecio().toString());


        holder.btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        public ItemsPedidoViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenOferta = itemView.findViewById(R.id.imagenOferta);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);
            btnMas = itemView.findViewById(R.id.ofertaButton);
            btnMenos = itemView.findViewById(R.id.editarButton);


        }

    }

    public void showPopup(View view){

    }

    private List<ItemsPedido> dataSet;
    private Context context;
    private Boolean bool;

    public ItemsPedidoRecyclerAdapter(List<ItemsPedido>dataSet, Context context, Boolean bool){
        this.dataSet=dataSet;
        this.context=context;
        this.bool = bool;
    }

}
