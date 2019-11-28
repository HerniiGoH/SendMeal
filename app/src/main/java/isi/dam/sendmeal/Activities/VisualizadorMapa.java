package isi.dam.sendmeal.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import isi.dam.sendmeal.R;

public class VisualizadorMapa extends MapsActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker marker;
    private Toolbar toolbar;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizador_mapa);
        toolbar = findViewById(R.id.toolbar_visualizar_mapa);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        btnConfirmar = findViewById(R.id.btnLocalizadorMapa);
        btnConfirmar.setEnabled(false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapVisualizador);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        final LatLng santaFe = new LatLng(-31.629484, -60.701036);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaFe, 15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker == null) {
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                } else {
                    marker.setPosition(latLng);
                }
                btnConfirmar.setEnabled(true);
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (marker == null) {
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Santa Fe").draggable(true));
                } else {
                    marker.setPosition(latLng);
                }
                btnConfirmar.setEnabled(true);
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaItemsPedido.latLng = marker.getPosition();
                finish();
            }
        });

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

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
