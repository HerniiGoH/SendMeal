package isi.dam.sendmeal.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Objects;

public class TarjetaCredito implements Parcelable {
    private Integer id;
    private Long numeroTarjeta;
    private Integer CCV;
    private Calendar date;

    public TarjetaCredito() {
    }

    public TarjetaCredito(Long numeroTarjeta, Integer CCV, Calendar date) {
        this.numeroTarjeta = numeroTarjeta;
        this.CCV = CCV;
        this.date = date;
    }

    public TarjetaCredito(Integer id, Long numeroTarjeta, Integer CCV, Calendar date) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.CCV = CCV;
        this.date = date;
    }

    protected TarjetaCredito(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            numeroTarjeta = null;
        } else {
            numeroTarjeta = in.readLong();
        }
        if (in.readByte() == 0) {
            CCV = null;
        } else {
            CCV = in.readInt();
        }
    }

    public static final Creator<TarjetaCredito> CREATOR = new Creator<TarjetaCredito>() {
        @Override
        public TarjetaCredito createFromParcel(Parcel in) {
            return new TarjetaCredito(in);
        }

        @Override
        public TarjetaCredito[] newArray(int size) {
            return new TarjetaCredito[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Integer getCCV() {
        return CCV;
    }

    public void setCCV(Integer CCV) {
        this.CCV = CCV;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarjetaCredito)) return false;
        TarjetaCredito that = (TarjetaCredito) o;
        return getId().equals(that.getId()) &&
                getNumeroTarjeta().equals(that.getNumeroTarjeta()) &&
                getCCV().equals(that.getCCV()) &&
                getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumeroTarjeta(), getCCV(), getDate());
    }

    @Override
    public String toString() {
        return "TarjetaCredito{" +
                "id=" + id +
                ", numeroTarjeta=" + numeroTarjeta +
                ", CCV=" + CCV +
                ", date=" + date +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (numeroTarjeta == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(numeroTarjeta);
        }
        if (CCV == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(CCV);
        }
    }
}
