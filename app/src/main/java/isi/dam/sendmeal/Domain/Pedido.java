package isi.dam.sendmeal.Domain;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Pedido implements Parcelable {
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
    @Ignore
    private List<ItemsPedido> items;

    public Pedido() {
        items = new ArrayList<ItemsPedido>();
    }

    protected Pedido(Parcel in) {
        if (in.readByte() == 0) {
            idPedido = null;
        } else {
            idPedido = in.readInt();
        }
        if (in.readByte() == 0) {
            fecha_creacion = null;
        } else {
            Date date = new Date();
            date.setTime(in.readLong());
            fecha_creacion = date;
        }
        if (in.readByte() == 0) {
            estado = null;
        } else {
            estado = EstadoPedido.valueOf(in.readString());
        }
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lng = null;
        } else {
            lng = in.readDouble();
        }
        items = in.createTypedArrayList(ItemsPedido.CREATOR);
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idPedido == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idPedido);
        }
        if (fecha_creacion == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(fecha_creacion.getTime());
        }
        if (estado == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeString(estado.name());
        }
        if (lat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lat);
        }
        if (lng == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lng);
        }
        parcel.writeTypedList(items);
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

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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
        return (ArrayList<ItemsPedido>) items;
    }

    public void setItems(ArrayList<ItemsPedido> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return getIdPedido().equals(pedido.getIdPedido()) &&
                getFecha_creacion().equals(pedido.getFecha_creacion()) &&
                getEstado() == pedido.getEstado() &&
                getLat().equals(pedido.getLat()) &&
                getLng().equals(pedido.getLng()) &&
                getItems().equals(pedido.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPedido(), getFecha_creacion(), getEstado(), getLat(), getLng(), getItems());
    }
}

