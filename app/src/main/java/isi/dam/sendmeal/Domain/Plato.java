package isi.dam.sendmeal.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plato implements Parcelable {

    private static Integer id_sec;

    private Integer id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private Float calorias;
    private Boolean enOferta;

    public Plato(Integer id, String nombre, String descripcion, Float precio, Float calorias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.enOferta = false;
    }
    @Ignore
    public Plato(String nombre, String descripcion, Float precio, Float calorias) {
        this.id = Plato.getId_sec();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.enOferta = false;
        Plato.setId_sec(id+1);
    }

    public static void setIdSeq(Integer id){
        id_sec = id;
    }

    protected Plato(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nombre = in.readString();
        descripcion = in.readString();
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readFloat();
        }
        if (in.readByte() == 0) {
            calorias = null;
        } else {
            calorias = in.readFloat();
        }
        if (in.readByte() == 0) {
            enOferta = null;
        } else {
            enOferta = new Boolean(in.readString());
        }
    }

    public static final Creator<Plato> CREATOR = new Creator<Plato>() {
        @Override
        public Plato createFromParcel(Parcel in) {
            return new Plato(in);
        }

        @Override
        public Plato[] newArray(int size) {
            return new Plato[size];
        }
    };

    public Boolean getEnOferta() {
        return enOferta;
    }

    public void setEnOferta(Boolean enOferta) {
        this.enOferta = enOferta;
    }

    public static Integer getId_sec() {
        return id_sec;
    }

    private static void setId_sec(Integer id_sec) {
        Plato.id_sec = id_sec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getCalorias() {
        return calorias;
    }

    public void setCalorias(Float calorias) {
        this.calorias = calorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plato)) return false;
        Plato plato = (Plato) o;
        return getId().equals(plato.getId()) &&
                getNombre().equals(plato.getNombre()) &&
                getDescripcion().equals(plato.getDescripcion()) &&
                getPrecio().equals(plato.getPrecio()) &&
                getCalorias().equals(plato.getCalorias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getDescripcion(), getPrecio(), getCalorias());
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
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        if (precio == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(precio);
        }
        if (calorias == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(calorias);
        }
        if (enOferta == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeString(enOferta.toString());
        }
    }

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", calorias=" + calorias +
                '}';
    }
}
