
package isi.dam.sendmeal;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isi.dam.sendmeal.Domain.Plato;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.content.ContextCompat.getSystemService;




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

        final Plato plato = dataSet.get(position);
        holder.titPlato.setText(plato.getNombre());
        holder.precPlato.setText("$"+plato.getPrecio().toString());
        holder.btnOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean b = !dataSet.get(position).getEnOferta();
                dataSet.get(position).setEnOferta(b);
                if(b){
                    holder.imagenOferta.setVisibility(View.VISIBLE);
                }
                else{
                    holder.imagenOferta.setVisibility(View.INVISIBLE);
                }

                Mihilo hilo = new Mihilo(b);

                hilo.start();
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
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);
                notifyItemChanged(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                View popup = LayoutInflater.from(context).inflate(R.layout.popup_plato,null);
                TextView nombrePop = popup.findViewById(R.id.nombrePlatoPopup);
                nombrePop.append(" "+plato.getNombre());
                TextView descripcionPop = popup.findViewById(R.id.descripcionPlatoPopup);
                descripcionPop.append(" "+plato.getDescripcion());
                TextView precioPopup = popup.findViewById(R.id.precioPlatoPopup);
                precioPopup.append(" $"+plato.getPrecio().toString());
                TextView caloriasPopup = popup.findViewById(R.id.caloriasPlatoPopup);
                caloriasPopup.append(" "+plato.getCalorias().toString()+" cal.");
                final PopupWindow popupWindow = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(view.getRootView(), Gravity.CENTER,0,0);
                View container = (View) popupWindow.getContentView().getParent();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.7f;
                wm.updateViewLayout(container, p);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class PlatoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlato, imagenOferta;
        TextView titPlato, precPlato;
        CardView cardView;
        Button btnOferta, btnEditar, btnEliminar;
        public PlatoViewHolder(@NonNull final View itemView) {
            super(itemView);
            imagenOferta = itemView.findViewById(R.id.imagenOferta);
            cardView = itemView.findViewById(R.id.cardViewPlato);
            imgPlato = itemView.findViewById(R.id.imagenPlato);
            titPlato = itemView.findViewById(R.id.ListaPlatoNombre);
            precPlato = itemView.findViewById(R.id.ListaPlatoPrecio);
            btnOferta = itemView.findViewById(R.id.ofertaButton);
            btnEditar = itemView.findViewById(R.id.editarButton);
            btnEliminar = itemView.findViewById(R.id.eliminarButton);

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

    public class Mihilo extends Thread{

        private Boolean oferta;

        public Mihilo(Boolean b){
            this.oferta=b;
        }

        @Override
        public void run(){
            /*try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            Intent intentMy = new Intent();
            intentMy.setAction("android.intent.action.myreceiver");
            intentMy.putExtra("whatever", oferta);
            context.sendBroadcast(intentMy);
        }
    }
}