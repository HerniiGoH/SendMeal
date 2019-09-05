package isi.dam.sendmeal.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class CuentaBancaria implements Parcelable {
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

    protected CuentaBancaria(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        CBU = in.readString();
        aliasCBU = in.readString();
    }

    public static final Creator<CuentaBancaria> CREATOR = new Creator<CuentaBancaria>() {
        @Override
        public CuentaBancaria createFromParcel(Parcel in) {
            return new CuentaBancaria(in);
        }

        @Override
        public CuentaBancaria[] newArray(int size) {
            return new CuentaBancaria[size];
        }
    };

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
        parcel.writeString(CBU);
        parcel.writeString(aliasCBU);
    }
}
