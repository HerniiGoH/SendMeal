package isi.dam.sendmeal.Domain;

import java.util.Objects;

public class Usuario {
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
}
