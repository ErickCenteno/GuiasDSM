package centeno.home.guia4app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Tareas> list;

    public MyAdapter(List<Tareas> list) {
        this.list = list;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView tvTitulo, tvDescripcion, tvFecha, tvHora;

        public MyViewHolder(View v) {
            super(v);
            tvTitulo = v.findViewById(R.id.tvTitulo);
            tvDescripcion = v.findViewById(R.id.tvDescripcion);
            tvFecha = v.findViewById(R.id.tvFecha);
            tvHora = v.findViewById(R.id.tvHora);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            // Obtener los datos del ítem seleccionado
            String titulo = list.get(position).getTitulo();
            String descripcion = list.get(position).getDescripcion();
            String fecha = list.get(position).getFecha();
            String hora = list.get(position).getHora();

            // Inflar el layout del diálogo
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View dialogView = inflater.inflate(R.layout.item_dialogo, null);

            // Referenciar los EditText del diálogo
            EditText edtTitulo = dialogView.findViewById(R.id.edtTitulo);
            EditText edtDescripcion = dialogView.findViewById(R.id.edtDescripcion);
            EditText edtFecha = dialogView.findViewById(R.id.edtFecha);
            EditText edtHora = dialogView.findViewById(R.id.edtHora);

            // Cargar los datos en los EditText
            edtTitulo.setText(titulo);
            edtDescripcion.setText(descripcion);
            edtFecha.setText(fecha);
            edtHora.setText(hora);

            // Crear el diálogo
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
            AlertDialog dialog = dialogBuilder.setView(dialogView)
                    .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Guardar los datos editados en la lista
                            list.get(position).setTitulo(edtTitulo.getText().toString());
                            list.get(position).setDescripcion(edtDescripcion.getText().toString());
                            list.get(position).setFecha(edtFecha.getText().toString());
                            list.get(position).setHora(edtHora.getText().toString());

                            // Notificar al adaptador que los datos han cambiado
                            notifyItemChanged(position);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Cancelar la edición, no se hace nada
                            dialog.dismiss();
                        }
                    }).create();

            // Cambiar el fondo del diálogo a blanco programáticamente
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

            // Mostrar el diálogo
            dialog.show();

        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            String item = list.get(position).getDescripcion();

            Toast.makeText(view.getContext(), "Elemento presionado: " + item, Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tvTitulo.setText(list.get(position).getTitulo());
        holder.tvDescripcion.setText(list.get(position).getDescripcion());
        holder.tvFecha.setText(list.get(position).getFecha());
        holder.tvHora.setText(list.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
