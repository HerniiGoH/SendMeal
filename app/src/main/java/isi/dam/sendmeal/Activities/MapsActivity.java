package isi.dam.sendmeal.Activities;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

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
    private List<Marker> markerList;
    private List<Polyline> polylineList;
    private Spinner spinner;

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
        spinner = findViewById(R.id.spinner_activity_maps);
        ArrayAdapter<EstadoPedido> arrayAdapter = new ArrayAdapter<>(MapsActivity.this, android.R.layout.simple_list_item_1, EstadoPedido.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        markerList = new ArrayList<>();
        polylineList = new ArrayList<>();
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
        mMap.getUiSettings().setMyLocationButtonEnabled(mMap.isMyLocationEnabled());
        final LatLng santaFe = new LatLng(-31.629484, -60.701036);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaFe, 15));

        cargarMarcadores();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(Polyline p : polylineList){
                    p.remove();
                }
                polylineList.clear();
                PolylineOptions polylineOptions = new PolylineOptions();
                EstadoPedido estadoPedido = (EstadoPedido) adapterView.getItemAtPosition(i);
                if (estadoPedido == EstadoPedido.TODOS) {
                    for (Marker marker : markerList) {
                        marker.setVisible(true);
                    }
                } else {
                    if (estadoPedido == EstadoPedido.EN_ENVIO) {
                        for (int j = 0; j < pedidoList.size(); j++) {
                            if (pedidoList.get(j).getEstado() == estadoPedido) {
                                polylineOptions.add(markerList.get(j).getPosition());
                                markerList.get(j).setVisible(true);
                            } else {
                                markerList.get(j).setVisible(false);
                            }
                        }
                        polylineOptions.color(Color.BLUE);
                        polylineOptions.width(3);
                        polylineList.add(mMap.addPolyline(polylineOptions));
                    } else {
                        for (int j = 0; j < pedidoList.size(); j++) {
                            if (pedidoList.get(j).getEstado() == estadoPedido) {
                                markerList.get(j).setVisible(true);
                            } else {
                                markerList.get(j).setVisible(false);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void cargarMarcadores() {
        for (Pedido p : pedidoList) {
            Double costo = 0D;
            for (ItemsPedido ip : p.getItems()) {
                costo += ip.getPrecioPlato() * ip.getCantidad();
            }
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(p.getLat(), p.getLng())).title("Pedido: " + p.getIdPedido() + " Estado: " + p.getEstado().name() + " Costo: $" + costo.toString()).draggable(false);
            BitmapDescriptor bitmapDescriptor;
            switch (p.getEstado()) {
                case PENDIENTE:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
                    break;
                case RECHAZADO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    break;
                case ACEPTADO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    break;
                case EN_PREPARACION:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
                case EN_ENVIO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    break;
                case ENVIADO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                case ENTREGADO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    break;
                case CANCELADO:
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    break;
                default:
                    bitmapDescriptor = null;
            }
            markerOptions.icon(bitmapDescriptor);
            markerList.add(mMap.addMarker(markerOptions));
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
