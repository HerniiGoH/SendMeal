package isi.dam.sendmeal.DAO;

import android.content.Context;

import androidx.room.Room;

public class DBClient {
    private static DBClient DB = null;

    private PedidoDB pedidoDB;

    private DBClient(Context ctx) {
        pedidoDB = Room.databaseBuilder(ctx, PedidoDB.class, "pedidoDB-DB").allowMainThreadQueries().build();
    }

    public synchronized static DBClient getInstance(Context ctx) {
        if (DB == null) {
            DB = new DBClient(ctx);
        }
        return DB;
    }

    public PedidoDB getPedidoDB() {
        return pedidoDB;
    }

}
