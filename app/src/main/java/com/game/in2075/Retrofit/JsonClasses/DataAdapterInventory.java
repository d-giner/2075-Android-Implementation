package com.game.in2075.Retrofit.JsonClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.in2075.R;

import java.util.List;

public class DataAdapterInventory extends RecyclerView.Adapter<DataAdapterInventory.ViewHolderData> {
    private List<Obj> objects; /** We create a Game list */
    private  Boolean oxygenMustShow = false;

    public DataAdapterInventory(List<Obj> o) { /** Constructor */
        this.objects = o;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { /** It links the adapter with game_list through ViewHolderData() function */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obj_list,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        switch (objects.get(position).getObjName()){
            case "Lightsaber":
                holder.objImg.setImageResource(R.drawable.lightsaber);
                break;
            case "Great Shield":
                holder.objImg.setImageResource(R.drawable.shield);
                break;
            case "Helmet":
                holder.objImg.setImageResource(R.drawable.helmet);
                break;
            case "Oxygen Bottle":
                holder.objImg.setImageResource(R.drawable.o2);
                oxygenMustShow = true;
                break;
            case "Wingman":
                holder.objImg.setImageResource(R.drawable.wingman);
                break;
        }
        if (!oxygenMustShow) {
            holder.objectName.setText(objects.get(position).getObjName());
            holder.attack.append(String.valueOf(objects.get(position).getObjAttack()));
            holder.defense.append(String.valueOf(objects.get(position).getObjDefense()));
        }
        else {
            holder.objectName.setText(objects.get(position).getObjName());
            holder.attack.setVisibility(View.GONE);
            holder.defense.setVisibility(View.GONE);
            oxygenMustShow = false;
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView attack, defense, objectName;
        ImageView objImg;

        public ViewHolderData(@NonNull View itemView) { /** We make references in order to modify the data from the layout game_list */
            super(itemView);

            objectName = itemView.findViewById(R.id.objNameText);
            attack = itemView.findViewById(R.id.objAttackText);
            defense = itemView.findViewById(R.id.objDefText);
            objImg = itemView.findViewById(R.id.objImage);
        }
    }
}
