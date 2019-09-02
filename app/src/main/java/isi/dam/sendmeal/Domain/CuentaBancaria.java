package isi.dam.sendmeal.Domain;

import java.util.Objects;

public class CuentaBancaria {
    private Integer id;
    private String CBU;
    private String aliasCBU;

    public CuentaBancaria() {
    }

    public CuentaBancaria(String CBU, String aliasCBU) {
        this.CBU = CBU;
        this.aliasCBU = aliasCBU;
    }

    public CuentaBancaria(Integer id, String CBU, String aliasCBU) {
        this.id = id;
        this.CBU = CBU;
        this.aliasCBU = aliasCBU;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public String getAliasCBU() {
        return aliasCBU;
    }

    public void setAliasCBU(String aliasCBU) {
        this.aliasCBU = aliasCBU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBancaria)) return false;
        CuentaBancaria that = (CuentaBancaria) o;
        return getId().equals(that.getId()) &&
                getCBU().equals(that.getCBU()) &&
                getAliasCBU().equals(that.getAliasCBU());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCBU(), getAliasCBU());
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", CBU='" + CBU + '\'' +
                ", aliasCBU='" + aliasCBU + '\'' +
                '}';
    }
}
