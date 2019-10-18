package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ItemsPedido {
    @PrimaryKey
    private Integer id;
    @ColumnInfo
    private Pedido pedido;
    @ColumnInfo
    private Plato plato;
    @ColumnInfo
    private Integer cantidad;
    @ColumnInfo
    private Double precio;

}
