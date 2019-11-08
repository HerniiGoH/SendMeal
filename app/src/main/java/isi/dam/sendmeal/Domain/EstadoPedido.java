package isi.dam.sendmeal.Domain;

import androidx.room.TypeConverter;

public enum EstadoPedido {
    TODOS(-1),PENDIENTE(0), ENVIADO(1), ACEPTADO(2), RECHAZADO(3), EN_PREPARACION(4), EN_ENVIO(5), ENTREGADO(6), CANCELADO(-9);

    EstadoPedido(Integer code){
        this.code = code;
    }
    
    private final Integer code;

    public Integer getCode() {
        return code;
    }

    @TypeConverter
    public static EstadoPedido getEstadoPedido(Integer n){
        switch (n){
            case -1: return TODOS;
            case 0: return PENDIENTE;
            case 1: return ENVIADO; 
            case 2: return ACEPTADO; 
            case 3: return RECHAZADO; 
            case 4: return EN_PREPARACION; 
            case 5: return EN_ENVIO; 
            case 6: return ENTREGADO; 
            case -9: return CANCELADO; 
            default: return null;
        }
    }

    @TypeConverter
    public static Integer getEstadoCode(EstadoPedido estadoPedido){
        if(estadoPedido!=null){
            return estadoPedido.getCode();
        }
        else{
            return null;
        }
    }
    
    
}


