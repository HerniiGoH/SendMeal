package isi.dam.sendmeal.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
   @PrimaryKey
   private Integer id;
    /*@ColumnInfo
   private Date fecha_creacion;
    @ColumnInfo
    private EstadoPedido estado;*/
    @ColumnInfo
    private Double lat;
    @ColumnInfo
    private Double lng;
  /*  @ColumnInfo
    private ArrayList<ItemsPedido> items;*/



    /*public class DateConverter {

        @TypeConverter
        public Date toDate(Long dateLong){
            return dateLong == null ? null: new Date(dateLong);
        }

        @TypeConverter
        public Long fromDate(Date date){
            return date == null ? null : date.getTime();
        }
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
/*
    public ArrayList<ItemsPedido> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsPedido> items) {
        this.items = items;
    }*/
}
