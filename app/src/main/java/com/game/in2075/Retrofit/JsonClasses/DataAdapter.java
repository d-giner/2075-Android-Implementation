package com.game.in2075.Retrofit.JsonClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.in2075.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Game> games; /** Creem una llista de la classe elements */

    public DataAdapter(List<Game> g) { /** Constructor */
        this.games = g;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { /** Enllaçarà aquest adaptador amb el layout item_list a través de la funció ViewHolderData() */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_list,null,false);    /** ja que a aquesta funció li pasarem el view que hem creat aquí */
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.date.setText(games.get(position).getData());
        holder.points.setText(String.valueOf(games.get(position).getPoints()));
        holder.level.setText(String.valueOf(games.get(position).getLevel()));
        holder.kills.setText(String.valueOf(games.get(position).getKills()));
        holder.deaths.setText(String.valueOf(games.get(position).getMyDeaths()) + " times.");
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView date, points, level, kills, deaths;

        public ViewHolderData(@NonNull View itemView) { /** Creem una referència per modificar el idData (Del layout item_list) */
            super(itemView);

            date = itemView.findViewById(R.id.dateText);
            points = itemView.findViewById(R.id.pointsText);
            level = itemView.findViewById(R.id.levelText);
            kills = itemView.findViewById(R.id.killsText);
            deaths= itemView.findViewById(R.id.deathsText);
        }
    }
}
