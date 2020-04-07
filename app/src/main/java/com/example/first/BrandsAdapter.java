package com.example.first;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dimas Maulana on 5/26/17.
 * Email : araymaulana66@gmail.com
 */

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder> {


    private ArrayList<Brands> dataList;

    public BrandsAdapter(ArrayList<Brands> dataList) {
        this.dataList = dataList;
    }

    @Override
    public BrandsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_brands, parent, false);
        return new BrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandsViewHolder holder, int position) {
        holder.txtNama_brand.setText(dataList.get(position).getNama_brand());
        holder.txtKetegori.setText(dataList.get(position).getKategori());
        String url= dataList.get(position).getNama_file();
        ImageView thumb=holder.img_brand;
        String img_url="http://bukawaralaba.com/"+ dataList.get(position).getNama_file();
        Picasso.get()
                .load(img_url)
                .into(holder.img_brand);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class BrandsViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama_brand, txtKetegori;
        private ImageView img_brand;

        public BrandsViewHolder(View itemView) {
            super(itemView);
            txtNama_brand = (TextView) itemView.findViewById(R.id.txt_title_row);
            txtKetegori = (TextView) itemView.findViewById(R.id.textkategori_row);

            img_brand = (ImageView) itemView.findViewById(R.id.thumb_brand);
        }
    }
}