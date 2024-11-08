package centeno.home.guia4app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    Button btnFecha, btnHora, btnGuardar;
    EditText edtTitulo, edtDescripcion, edtFecha, edtHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);
        btnGuardar = findViewById(R.id.btnGuardar);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String amPm = (hourOfDay < 12) ? "a.m" : "p.m";
                        int hour12 = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                        String selectedHora = String.format(Locale.getDefault(), "%02d:%02d %s", hour12, minute, amPm);
                        edtHora.setText(selectedHora);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String FechaSelec = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        edtFecha.setText(FechaSelec);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = edtTitulo.getText().toString().trim();
                String descripcion = edtDescripcion.getText().toString().trim();
                String fecha = edtFecha.getText().toString().trim();
                String hora = edtHora.getText().toString().trim();

                if (titulo.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                    StringBuilder mensaje = new StringBuilder("Complete todos los campos:\n");
                    if (titulo.isEmpty()) mensaje.append("Falta el título\n");
                    if (descripcion.isEmpty()) mensaje.append("Falta la descripción\n");
                    if (fecha.isEmpty()) mensaje.append("Falta la fecha\n");
                    if (hora.isEmpty()) mensaje.append("Falta la hora\n");
                    Toast.makeText(AddTaskActivity.this, mensaje.toString().trim(), Toast.LENGTH_LONG).show();
                } else {
                    // Crear y agregar la tarea a la lista global en MainActivity
                    Tareas tarea = new Tareas();
                    tarea.setTitulo(titulo);
                    tarea.setDescripcion(descripcion);
                    tarea.setFecha(fecha);
                    tarea.setHora(hora);

                    MainActivity.lstTareas.add(tarea);  // Agrega la tarea a la lista compartida
                    Toast.makeText(AddTaskActivity.this, "Tarea guardada", Toast.LENGTH_SHORT).show();
                    finish();  // Cierra la actividad
                }
            }
        });
    }
}
