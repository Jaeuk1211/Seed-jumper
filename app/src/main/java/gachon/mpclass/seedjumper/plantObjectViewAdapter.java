package gachon.mpclass.seedjumper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class plantObjectViewAdapter extends RecyclerView.Adapter<plantObjectViewAdapter.ViewHolder> implements OnItemClickListener {
    private ArrayList<plantObjectViewItem> mList = new ArrayList<plantObjectViewItem>();
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_one_plant, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        plantObjectViewItem item = mList.get(position);//뷰 순서 반환

        holder.plantName.setText(item.getPlantName());

        if (item.getPlantName().equalsIgnoreCase("noname")) {
            holder.plantItem.setBackgroundResource(R.drawable.empty_object);
        } else {
            //Log.d("viewInfo", item.getPlantName() + ", "+ Integer.toString(position));
            switch (position) {
                case 0:
                    holder.plantItem.setBackgroundResource(R.drawable.seed6);
                    break;
                case 1:
                    holder.plantItem.setBackgroundResource(R.drawable.one_dandelion2);
                    break;
                case 2:
                    holder.plantItem.setBackgroundResource(R.drawable.bundle_dandelion);
                    break;
                case 3:
                    holder.plantItem.setBackgroundResource(R.drawable.seed1);
                    break;
                case 4:
                    holder.plantItem.setBackgroundResource(R.drawable.one_rose);
                    break;
                case 5:
                    holder.plantItem.setBackgroundResource(R.drawable.bundle_rose);
                    break;
                case 6:
                    holder.plantItem.setBackgroundResource(R.drawable.seed2);
                    break;
                case 7:
                    holder.plantItem.setBackgroundResource(R.drawable.one_tulip);
                    break;
                case 8:
                    holder.plantItem.setBackgroundResource(R.drawable.bundle_whitetulip);
                    break;
                case 9:
                    holder.plantItem.setBackgroundResource(R.drawable.seed3);
                    break;
                case 10:
                    holder.plantItem.setBackgroundResource(R.drawable.one_sunflower);
                    break;
                case 11:
                    holder.plantItem.setBackgroundResource(R.drawable.bundle_sunflower);
                    break;
                case 12:
                    holder.plantItem.setBackgroundResource(R.drawable.seed4);
                    break;
                case 13:
                    holder.plantItem.setBackgroundResource(R.drawable.one_yellowfreesia4);
                    break;
                case 14:
                    holder.plantItem.setBackgroundResource(R.drawable.bundle_freesia);
                    break;
                case 15:
                    holder.plantItem.setBackgroundResource(R.drawable.chair);
                    break;
                case 16:
                    holder.plantItem.setBackgroundResource(R.drawable.lamp);
                    break;
                case 17:
                    holder.plantItem.setBackgroundResource(R.drawable.fence);
                    break;
                case 18:
                    holder.plantItem.setBackgroundResource(R.drawable.empty_object);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void OnItemClick(ViewHolder holder, View view, int position) {
        if (mListener != null) {
            mListener.OnItemClick(holder, view, position);
        }
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout plantItem;
        TextView plantName;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            plantItem = itemView.findViewById(R.id.plant_img);
            plantName = itemView.findViewById(R.id.plantName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null) {
                        listener.OnItemClick(ViewHolder.this, v, pos);
                    }
                }
            });

        }

    }

    public String getName(int i) {
        return mList.get(i).getPlantName();
    }

    public void setName(String itemCode) {
        mList.get(Integer.parseInt(itemCode)).setPlantName(itemCode);
    }

    public void addItem(String plantName){
        plantObjectViewItem item = new plantObjectViewItem();
        item.setPlantName(plantName);
        mList.add(item);
    }

    //-------------------------------------------------------------------------
}