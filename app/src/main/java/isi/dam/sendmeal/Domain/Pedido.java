package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    private Integer idPedido;
    @ColumnInfo
    private Date fecha_creacion;
    @ColumnInfo
    private EstadoPedido estado;
    @ColumnInfo
    private Double lat;
    @ColumnInfo
    private Double lng;
    @Embedded
    private ArrayList<ItemsPedido> items;
    public Pedido (){
        items = new ArrayList<ItemsPedido>();
    }

    public static class Converter {
        @TypeConverter
        public static Date toDate(Long dateLong) {
            return dateLong == null ? null : new Date(dateLong);
        }

        @TypeConverter
        public static Long fromDate(Date date) {
            return date == null ? null : date.getTime();
        }
    }
    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return idPedido;
    }

    public void setId(Integer id) {
        this.idPedido = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public ArrayList<ItemsPedido> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsPedido> items) {
        this.items = items;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}

