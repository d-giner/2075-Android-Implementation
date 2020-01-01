package com.game.in2075.Retrofit.JsonClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.in2075.R;

import java.util.List;

public class DataAdapterGames extends RecyclerView.Adapter<DataAdapterGames.ViewHolderData> {
    private List<Game> games; /** We create a Game list */

    public DataAdapterGames(List<Game> g) { /** Constructor */
        this.games = g;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { /** It links the adapter with game_list through ViewHolderData() function */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_list,null,false);
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

        public ViewHolderData(@NonNull View itemView) { /** We make references in order to modify the data from the layout game_list */
            super(itemView);

            date = itemView.findViewById(R.id.dateText);
            points = itemView.findViewById(R.id.pointsText);
            level = itemView.findViewById(R.id.levelText);
            kills = itemView.findViewById(R.id.killsText);
            deaths= itemView.findViewById(R.id.deathsText);
        }
    }
}
