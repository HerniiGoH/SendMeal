package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ItemsPedido {
    @PrimaryKey
    private Integer id;
    /*@ColumnInfo
    private Pedido pedido;
    @ColumnInfo
    private Plato plato;*/
    @ColumnInfo
    private Integer cantidad;
    @ColumnInfo
    private Double precio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
