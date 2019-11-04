package isi.dam.sendmeal.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import isi.dam.sendmeal.Domain.EstadoPedido;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;

@Database(entities = {Pedido.class, ItemsPedido.class}, version = 1,exportSchema = false)
@TypeConverters ({Pedido.Converter.class, EstadoPedido.class})
public abstract class PedidoDB extends RoomDatabase {
public abstract PedidoDao pedidoDao();
public abstract ItemsPedidoDao itemsPedidoDao();
public abstract Pedido.PedidoItemPedidoDao pedidoItemPedidoDao();
}
