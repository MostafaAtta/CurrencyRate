package com.atta.currencyrate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private Context mContext;

    private List<Currency> currencies;


    public CurrencyAdapter(Context mContext, List<Currency> currencies) {
        this.mContext = mContext;
        this.currencies = currencies;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        //TODO comment name and uncomment currency
        final Currency currency = currencies.get(position);


        myViewHolder.currName.setText(currency.getName());
        myViewHolder.currFlag.setImageResource(currency.getImage());
        String currentValue = String.format(Locale.US,"%.4f",currency.getValue());
        myViewHolder.currValue.setText(currentValue);


        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, CurrencyConverterActivity.class);
                intent.putExtra("currency", currency);
                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
/*
        if (currencies == null){

            Toast.makeText(mContext, "No Orders Found", Toast.LENGTH_SHORT).show();

            return  currencies == null ? 0 : currencies.size();

        }*/

        return currencies.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView currFlag;
        TextView currName, currValue;
        LinearLayout linearLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            currFlag = itemView.findViewById(R.id.flag);
            currName = itemView.findViewById(R.id.name);
            currValue = itemView.findViewById(R.id.cur);
            linearLayout = itemView.findViewById(R.id.linear);



        }
    }


}
