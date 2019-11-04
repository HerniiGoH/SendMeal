package isi.dam.sendmeal.Domain;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;

import java.util.List;


@Entity
public class ItemsPedido {
    @PrimaryKey(autoGenerate = true)
    private Integer idItemPedido;
    @ForeignKey(parentColumns = "idPedido", childColumns = "idPedido_Child", entity = Pedido.class, onDelete = ForeignKey.CASCADE)
    private Integer idPedido_Child;
    @Embedded
    private Plato plato;
    @ColumnInfo
    private Integer cantidad;
    @ColumnInfo
    private Double precioPlato;

    public ItemsPedido() {
    }

    public Integer getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Integer getIdPedido_Child() {
        return idPedido_Child;
    }

    public void setIdPedido_Child(Integer idPedido_Child) {
        this.idPedido_Child = idPedido_Child;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }

    /*public class PedidoAndItemsPedido{
        @Embedded
        public Pedido Pedido;
        @Relation(entityColumn = "idPedido_Child", parentColumn = "idPedido")
        public List<ItemsPedido>itemsPedidos;
    }

    @Dao
    public interface PedidoItemPedidoDao{
        @Query("Select idPedido from Pedido")
        public List<PedidoAndItemsPedido> cargarPedidoseItemsPedido();
    }*/
}
