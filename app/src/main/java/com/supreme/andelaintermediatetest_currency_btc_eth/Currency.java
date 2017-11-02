package com.supreme.andelaintermediatetest_currency_btc_eth;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Currency extends AppCompatActivity {
    private static String URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR,JPY,CHF,CAD,AUD,HKD,NGN,CNY,NZD,BRL,KRW,NOK,GBP,SEK,MXN,SGD,INR,ZAR,INS";
    private GridView mGridView;
    private ListAdapter mAdapter;
    private LinearLayout currencyProgress;
    private TextView progressDetails;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        currencyProgress = (LinearLayout) findViewById(R.id.currency_progress);
        mGridView = (GridView) findViewById(R.id.cards);
        currencyProgress.setVisibility(View.VISIBLE);
        progressDetails = (TextView) findViewById(R.id.progress_info);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        load();

    }

    void load() {

        mGridView.setAdapter(null);
        currencyProgress.setVisibility(View.VISIBLE);

        try {
            getCards(new GetCards() {
                @Override
                public void success(final ArrayList<HashMap<String, String>> cards, final ArrayList iterator) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currencyProgress.setVisibility(View.GONE);
                            mGridView.setVisibility(View.VISIBLE);
                            mGridView.setAdapter(null);
                            mAdapter = new SimpleAdapter(
                                    getApplicationContext(),
                                    cards,
                                    R.layout.single_view,
                                    new String[]{"currency", "btc", "eth", "flag"},
                                    new int[]{R.id.currency, R.id.btc, R.id.eth, R.id.flag});
                            mGridView.setAdapter(mAdapter);

                            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getApplicationContext(), Currency_Converter_Activity.class).putExtra("CURRENCY", iterator.get(i).toString());
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }

                @Override
                public void error() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method set to add country flags to each card
    Map getFlags() {
        Map<String, String> flags = new HashMap<>();
        flags.put("USD", String.valueOf(R.drawable.flag_usa));
        flags.put("EUR", String.valueOf(R.drawable.flag_europe));
        flags.put("JPY", String.valueOf(R.drawable.flag_japan));
        flags.put("CHF", String.valueOf(R.drawable.flag_swiss_franc_chf));
        flags.put("CAD", String.valueOf(R.drawable.flag_canada));
        flags.put("AUD", String.valueOf(R.drawable.flag_australia));
        flags.put("HKD", String.valueOf(R.drawable.flag_hong_kong_hkd));
        flags.put("NGN", String.valueOf(R.drawable.flag_nigeria));
        flags.put("CNY", String.valueOf(R.drawable.flag_cny));
        flags.put("NZD", String.valueOf(R.drawable.flag_nzd));
        flags.put("BRL", String.valueOf(R.drawable.flag_brazil));
        flags.put("KRW", String.valueOf(R.drawable.flag_south_korea));
        flags.put("NOK", String.valueOf(R.drawable.flag_norwegian_krone_nok));
        flags.put("GBP", String.valueOf(R.drawable.flag_britain));
        flags.put("SEK", String.valueOf(R.drawable.flag_swedish_krona_sek));
        flags.put("MXN", String.valueOf(R.drawable.flag_mexico_mxn));
        flags.put("SGD", String.valueOf(R.drawable.flag_singapore_sgd));
        flags.put("INR", String.valueOf(R.drawable.flag_india));
        flags.put("ZAR", String.valueOf(R.drawable.flag_south_africa));
        flags.put("INS", String.valueOf(R.drawable.flag_isreal));
        return flags;
    }

    void getData(final GetData callBack) throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(URL)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            progressDetails.setText(R.string.no_internet);
                        }
                    });
                    e.printStackTrace();
                }
                try {
                    assert response != null;
                    String json = response.body().string();
                    callBack.success(new JSONObject(json));
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.error();
                }
            }
        });
        thread.start();

    }

    interface GetData {
        void success(JSONObject response) throws JSONException;

        void error();
    }

    interface GetCards {
        void success(ArrayList<HashMap<String, String>> cards, ArrayList reponse);

        void error();
    }

    void getCards(final GetCards getCards) throws Exception {
        getData(new GetData() {
            @Override
            public void success(JSONObject response) throws JSONException {
                ArrayList cards = new ArrayList();
                ArrayList<String> iterator = new ArrayList<>();
                //Getting JSON object
                //BTC and ETH
                JSONObject BTC = response.getJSONObject("BTC");
                JSONObject ETH = response.getJSONObject("ETH");
                Map flags = getFlags();
                Iterator<String> currencies = BTC.keys();

                while (currencies.hasNext()) {
                    String currency = currencies.next();
                    HashMap<String, String> newCurrency = new HashMap<>();
                    iterator.add(String.format("%s,%s,%s", currency, BTC.getInt(currency), ETH.getInt(currency)));
                    newCurrency.put("currency", currency);
                    newCurrency.put("flag", (String) flags.get(currency));
                    newCurrency.put("btc", String.valueOf(BTC.getInt(currency)));
                    newCurrency.put("eth", String.valueOf(ETH.getInt(currency)));
                    cards.add(newCurrency);
                }
                getCards.success(cards, iterator);
            }

            @Override
            public void error() {
                getCards.error();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            load();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
