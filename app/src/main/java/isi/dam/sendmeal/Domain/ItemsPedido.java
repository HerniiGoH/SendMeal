package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;


@Entity
public class ItemsPedido {
    @PrimaryKey(autoGenerate = true)
    private Integer idItemPedido;
    @Relation(entity = Pedido.class, parentColumn = "idPedido", entityColumn = "idItemPedido")
    private Pedido pedido;
    @Embedded
    private Plato plato;
    @ColumnInfo
    private Integer cantidad;
    @ColumnInfo
    private Double precioPlato;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Integer getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }
}
