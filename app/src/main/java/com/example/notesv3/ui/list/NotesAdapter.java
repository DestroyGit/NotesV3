package com.example.notesv3.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface OnNoteClickedListener{
        void onNoteClickedListener(@NonNull Notes note);
    }

    private final ArrayList<Notes> notes = new ArrayList<>();

    public void setData(List<Notes> toSet){
        notes.clear();
        notes.addAll(toSet);
    }

    private OnNoteClickedListener listener;

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // даем возможность надувать контейнер (фрагмент) вьюхами.
        // В скобках: вьюха, макет фрагмента, перезапись - если true, то в одном и том же месте будет
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Notes note = notes.get(position);

        // добавляем в найденные элементы макета текст для вывода на экран - то, чем заполняем TextView
        holder.notesName.setText(note.getName());
        holder.notesDate.setText(note.getDate());
        holder.card.setBackgroundResource(R.color.light_grey);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        // находим элементы макета
        TextView notesName;
        TextView notesDate;
        LinearLayout card;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getListener() != null){
                        getListener().onNoteClickedListener(notes.get(getAdapterPosition())); // getAdapterPosition() - определенный номер элемента в списке
                    }
                }
            });

            notesName = itemView.findViewById(R.id.notes_name);
            notesDate = itemView.findViewById(R.id.notes_date);
            card = itemView.findViewById(R.id.card);
        }
    }
}
