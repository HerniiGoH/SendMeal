package isi.dam.sendmeal.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(foreignKeys = @ForeignKey(parentColumns = "idPedido", childColumns = "idPedido_Child", entity = Pedido.class, onDelete = ForeignKey.CASCADE))
public class ItemsPedido implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Integer idItemPedido;

    private Integer idPedido_Child;
    @Embedded
    private Plato plato;
    @ColumnInfo
    private Integer cantidad;
    @ColumnInfo
    private Double precioPlato;

    public ItemsPedido() {
    }

    protected ItemsPedido(Parcel in) {
        if (in.readByte() == 0) {
            idItemPedido = null;
        } else {
            idItemPedido = in.readInt();
        }
        if (in.readByte() == 0) {
            idPedido_Child = null;
        } else {
            idPedido_Child = in.readInt();
        }
        plato = in.readParcelable(Plato.class.getClassLoader());
        if (in.readByte() == 0) {
            cantidad = null;
        } else {
            cantidad = in.readInt();
        }
        if (in.readByte() == 0) {
            precioPlato = null;
        } else {
            precioPlato = in.readDouble();
        }
    }

    public static final Creator<ItemsPedido> CREATOR = new Creator<ItemsPedido>() {
        @Override
        public ItemsPedido createFromParcel(Parcel in) {
            return new ItemsPedido(in);
        }

        @Override
        public ItemsPedido[] newArray(int size) {
            return new ItemsPedido[size];
        }
    };

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idItemPedido == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idItemPedido);
        }
        if (idPedido_Child == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idPedido_Child);
        }
        parcel.writeParcelable(plato, i);
        if (cantidad == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(cantidad);
        }
        if (precioPlato == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(precioPlato);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemsPedido)) return false;
        ItemsPedido that = (ItemsPedido) o;
        return getIdItemPedido().equals(that.getIdItemPedido()) &&
                getIdPedido_Child().equals(that.getIdPedido_Child()) &&
                getPlato().equals(that.getPlato()) &&
                getCantidad().equals(that.getCantidad()) &&
                getPrecioPlato().equals(that.getPrecioPlato());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdItemPedido(), getIdPedido_Child(), getPlato(), getCantidad(), getPrecioPlato());
    }
}
