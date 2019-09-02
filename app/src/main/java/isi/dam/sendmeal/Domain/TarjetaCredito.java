package isi.dam.sendmeal.Domain;

import java.util.Calendar;
import java.util.Objects;

public class TarjetaCredito {
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
}
