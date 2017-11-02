package com.supreme.andelaintermediatetest_currency_btc_eth;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Currency_Converter_Activity extends AppCompatActivity {
    private String[] card;
    private TextView btcVal, ethVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency__converter_);

        Intent intent = getIntent();
        card = intent.getStringExtra("CURRENCY").split(",");
        TextView currency = (TextView) findViewById(R.id.currency);
        btcVal = (TextView) findViewById(R.id.btc_value);
        ethVal = (TextView) findViewById(R.id.eth_value);

        currency.setText(card[0]);
        btcVal.setText(card[1]);
        ethVal.setText(card[2]);

        final EditText inputValue = (EditText) findViewById(R.id.input_value);

        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                convert(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                convert(editable.toString());
            }
        });
    }

    void convert(String input) {
        float btcConversionRate = Float.parseFloat(card[1]);
        float ethConversionRate = Float.parseFloat(card[2]);
        float inputVal, eth, btc;
        try {
            inputVal = Float.parseFloat(input);
            eth = inputVal / ethConversionRate;
            btc = inputVal / btcConversionRate;
        } catch (Exception e) {
            eth = Float.parseFloat(card[1]);
            btc = Float.parseFloat(card[2]);
        }

        btcVal.setText("" + btc);
        ethVal.setText("" + eth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_back) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}




