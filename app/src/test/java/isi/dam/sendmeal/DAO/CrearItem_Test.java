package isi.dam.sendmeal.DAO;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import java.util.List;
import isi.dam.sendmeal.Activities.ListaItemsPedido;
import isi.dam.sendmeal.R;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)

@Config(sdk = Build.VERSION_CODES.O_MR1)

public class CrearItem_Test {
        private ListaItemsPedido activity;

        @Before
        public void setUp() throws Exception{
            activity = Robolectric.buildActivity( ListaItemsPedido.class )
                    .create()
                    .resume()
                    .get();
        }

        @Test
        public void shouldNotBeNull() throws Exception
        {
            assertNotNull( activity );
        }

        @Test
        public void debeCrear() throws Exception {
            assertNotNull( activity.getCrear() );
            EditText Nombre = (EditText) activity.findViewById(R.id.ingresoNombrePlato);
            EditText Descripcion = (EditText) activity.findViewById(R.id.ingresoDescripcionPlato);
            EditText Precio = (EditText) activity.findViewById(R.id.ingresoPrecioPlato);
            EditText Calorias = (EditText) activity.findViewById(R.id.ingresoCaloriasPlato);

            Nombre.setText("plato_1");
            Descripcion.setText("descripcion");
            Precio.setText("32000");
            Calorias.setText("500");
            Button button = (Button) activity.findViewById(R.id.buttonRegistrarPlato);
            button.performClick();
            Thread.sleep(1500);

            Robolectric.flushForegroundThreadScheduler();

            assertEquals("",Nombre.getText().toString());
            assertEquals("",Descripcion.getText().toString());
            assertEquals("",Precio.getText().toString());
            assertEquals("",Calorias.getText().toString());

            List<Toast> toasts = Shadows.shadowOf(activity.getApplication()).getShownToasts();
            assertEquals(1,toasts.size());
        }

    }





