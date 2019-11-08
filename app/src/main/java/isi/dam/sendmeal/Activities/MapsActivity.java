package isi.dam.sendmeal.Activities;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.Domain.EstadoPedido;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private List<Pedido> pedidoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = findViewById(R.id.toolbar_map_activity);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pedidoList = getIntent().getParcelableArrayListExtra("pedidos");
        for (Pedido p : pedidoList) {
            p.setItems((ArrayList<ItemsPedido>) DBClient.getInstance(MapsActivity.this).getPedidoDB().itemsPedidoDao().getAllFromPedido(p.getIdPedido()));
        }
        Toast.makeText(MapsActivity.this, "" + pedidoList.size(), Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        actualizarMapa();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        final LatLng santaFe = new LatLng(-31.629484, -60.701036);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaFe, 15));

        for (Pedido p : pedidoList) {
            Double costo = 0D;
            for (ItemsPedido ip : p.getItems()) {
                costo += ip.getPrecioPlato() * ip.getCantidad();
            }
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(p.getLat(), p.getLng())).title("Pedido: " + p.getIdPedido() + " Estado: " + p.getEstado().name() + " Costo: $" + costo.toString()).draggable(false);
            BitmapDescriptor bitmapDescriptor;
            if (p.getEstado() == EstadoPedido.ENVIADO) {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            } else {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
            markerOptions.icon(bitmapDescriptor);
            mMap.addMarker(markerOptions);
        }
    }

    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
