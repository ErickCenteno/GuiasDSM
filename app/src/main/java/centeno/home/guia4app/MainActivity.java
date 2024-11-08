package centeno.home.guia4app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnAgregar;
    public static List<Tareas> lstTareas = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAgregar = findViewById(R.id.btnTarea);
        recyclerView = findViewById(R.id.recycleview);

        recyclerView.setHasFixedSize(true);

        adapter = new MyAdapter(lstTareas);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(adapter);

        // Configura el deslizamiento para eliminar un ítem del RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // No se maneja el movimiento en este caso
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Cuando se desliza, muestra un diálogo de confirmación para eliminar el ítem
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar elementos")
                        .setMessage("¿Estás seguro de eliminar esta tarea?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Si se confirma, elimina el ítem de la lista
                                int position = viewHolder.getAdapterPosition();
                                adapter.removeItem(position); // Elimina el ítem en la posición correspondiente
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Si se cancela, restaura el ítem deslizado
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Dibuja un fondo rojo mientras se desliza el ítem
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
                    View itemView = viewHolder.itemView;
                    Drawable redBackground = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_launcher_foreground);
                    redBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    redBackground.draw(c);
                }
            }
        });

        // Vincula el ItemTouchHelper al RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
