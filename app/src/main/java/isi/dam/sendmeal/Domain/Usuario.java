package isi.dam.sendmeal.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Usuario implements Parcelable {
    private Integer id;
    private String nombre;
    private String mail;
    private String clave;
    private Boolean notificarMail;
    private Double credito;
    private CuentaBancaria cuentaBancaria;
    private TarjetaCredito tarjetaCredito;
    private TipoCuenta tipoCuenta;

    public Usuario() {
    }

    public Usuario(String nombre, String mail, String clave, Boolean notificarMail, Double credito, CuentaBancaria cuentaBancaria, TarjetaCredito tarjetaCredito, TipoCuenta tipoCuenta) {
        this.nombre = nombre;
        this.mail = mail;
        this.clave = clave;
        this.notificarMail = notificarMail;
        this.credito = credito;
        this.cuentaBancaria = cuentaBancaria;
        this.tarjetaCredito = tarjetaCredito;
        this.tipoCuenta = tipoCuenta;
    }

    public Usuario(Integer id, String nombre, String mail, String clave, Boolean notificarMail, Double credito, CuentaBancaria cuentaBancaria, TarjetaCredito tarjetaCredito, TipoCuenta tipoCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.clave = clave;
        this.notificarMail = notificarMail;
        this.credito = credito;
        this.cuentaBancaria = cuentaBancaria;
        this.tarjetaCredito = tarjetaCredito;
        this.tipoCuenta = tipoCuenta;
    }

    protected Usuario(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nombre = in.readString();
        mail = in.readString();
        clave = in.readString();
        byte tmpNotificarMail = in.readByte();
        notificarMail = tmpNotificarMail == 0 ? null : tmpNotificarMail == 1;
        if (in.readByte() == 0) {
            credito = null;
        } else {
            credito = in.readDouble();
        }
        cuentaBancaria = in.readParcelable(CuentaBancaria.class.getClassLoader());
        tarjetaCredito = in.readParcelable(TarjetaCredito.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getNotificarMail() {
        return notificarMail;
    }

    public void setNotificarMail(Boolean notificarMail) {
        this.notificarMail = notificarMail;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getId().equals(usuario.getId()) &&
                getNombre().equals(usuario.getNombre()) &&
                getMail().equals(usuario.getMail()) &&
                getClave().equals(usuario.getClave()) &&
                getNotificarMail().equals(usuario.getNotificarMail()) &&
                getCredito().equals(usuario.getCredito()) &&
                Objects.equals(getCuentaBancaria(), usuario.getCuentaBancaria()) &&
                getTarjetaCredito().equals(usuario.getTarjetaCredito()) &&
                getTipoCuenta() == usuario.getTipoCuenta();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getMail(), getClave(), getNotificarMail(), getCredito(), getCuentaBancaria(), getTarjetaCredito(), getTipoCuenta());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", clave='" + clave + '\'' +
                ", notificarMail=" + notificarMail +
                ", credito=" + credito +
                ", cuentaBancaria=" + cuentaBancaria +
                ", tarjetaCredito=" + tarjetaCredito +
                ", tipoCuenta=" + tipoCuenta +
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
        parcel.writeString(nombre);
        parcel.writeString(mail);
        parcel.writeString(clave);
        parcel.writeByte((byte) (notificarMail == null ? 0 : notificarMail ? 1 : 2));
        if (credito == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(credito);
        }
        parcel.writeParcelable(cuentaBancaria, i);
        parcel.writeParcelable(tarjetaCredito, i);
    }
}
