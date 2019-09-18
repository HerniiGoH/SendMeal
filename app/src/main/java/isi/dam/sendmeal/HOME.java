package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class HOME extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar_01);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.op_01:

                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                return true;

            case R.id.op_02:
                Intent i2 = new Intent(this, CrearItem.class);
                startActivity(i2);
                return true;

            case R.id.op_03:
                Intent i3 = new Intent(this, ListaItems.class);
                startActivity(i3);
                return true;

            default: return false;
        }
    }
}
