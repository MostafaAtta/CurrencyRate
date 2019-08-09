package com.atta.currencyrate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CurrencyConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        Currency currency= (Currency) getIntent().getSerializableExtra("currency");

    }
}
