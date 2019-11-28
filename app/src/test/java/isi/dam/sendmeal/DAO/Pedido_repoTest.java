package isi.dam.sendmeal.DAO;

import android.content.Context;

import androidx.transition.Visibility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import isi.dam.sendmeal.Activities.Info_Pedido;
import isi.dam.sendmeal.Activities.ListaPedido;
import isi.dam.sendmeal.Domain.Pedido;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Pedido_repoTest {

private Context context;

    Integer cantidad;
    @Mock
    Context mMockContext;

    @Mock
    PedidoDao dataBaseHelper;

@Before
    public void init (){
    dataBaseHelper = DBClient.getInstance(new ListaPedido()).getPedidoDB().pedidoDao(); //calculo que el error es aca
    cantidad = dataBaseHelper.getall().size();
}

 @Test
    public void creacion_normal_del_pedido () {

        when(dataBaseHelper.getall().size()).thenReturn(cantidad);
        Pedido_repo prueba = new Pedido_repo();
        Integer resultado = prueba.getListaPedidos().size();
        assertThat(resultado,is(cantidad));
    }


}