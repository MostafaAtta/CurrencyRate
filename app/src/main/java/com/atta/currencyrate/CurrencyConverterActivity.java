package com.atta.currencyrate;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CurrencyConverterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView curr1, curr2, swap;
    EditText amountTxt;
    Button convertBtn;
    TextView resultTxt;

    double rate, result;

    Currency currency;

    boolean swapped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        currency = (Currency) getIntent().getSerializableExtra("currency");

        curr1 = findViewById(R.id.imageView);
        curr2 = findViewById(R.id.imageView2);
        swap = findViewById(R.id.imageView3);
        amountTxt = findViewById(R.id.editText);
        convertBtn = findViewById(R.id.button);
        resultTxt = findViewById(R.id.textView);


        curr1.setImageResource(currency.getImage());
        rate = currency.getValue();

        convertBtn.setOnClickListener(this);
        swap.setOnClickListener(this);

        amountTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(amountTxt.getWindowToken(), 0);
                    calculateResult();
                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == convertBtn){
            calculateResult();
        }else if (view == swap){

            rate = 1/rate;
            if (!swapped){
                curr2.setImageResource(currency.getImage());
                curr1.setImageResource(R.drawable.egypt);
                swapped =true;
            }else {

                curr1.setImageResource(currency.getImage());
                curr2.setImageResource(R.drawable.egypt);
                swapped =false;
            }

        }
    }

    private void calculateResult() {
        String amountString = amountTxt.getText().toString();
        if (amountString.isEmpty()){
            amountTxt.setError("enter an amountString");
            amountTxt.requestFocus();
            return;
        }

        double amount = Double.valueOf(amountString);

        result = amount*rate;

        resultTxt.setText(String.format(Locale.US,"%.4f", result));
    }
}
