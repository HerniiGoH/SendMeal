package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
   @PrimaryKey
   private Integer id;
    @ColumnInfo
   private Date fecha_creacion;
    @ColumnInfo
    private EstadoPedido estado;
    @ColumnInfo
    private Double lat;
    @ColumnInfo
    private Double lng;
    @ColumnInfo
    private List<ItemsPedido> items;




}
