package com.supreme.andelaintermediatetest_currency_btc_eth;


/**
 * Created by supreme on 11/1/17.
 */

public class Card_Adapter  {
        private  String Currency;
        private int ETHPrice;
        private int BTCPrice;
        private String FlagUrl;

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }


        public Card_Adapter(){}

        public int getETHPrice() {
            return ETHPrice;
        }

        public void setETHPrice(int ETHPrice) {
            this.ETHPrice = ETHPrice;
        }

        public int getBTCPrice() {
            return BTCPrice;
        }

        public void setBTCPrice(int BTCPrice) {
            this.BTCPrice = BTCPrice;
        }

        public String getFlagUrl() {
            return FlagUrl;
        }

        public void setFlagUrl(String flagUrl) {
            FlagUrl = flagUrl;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "Currency='" + Currency + '\'' +
                    ", ETHPrice='" + ETHPrice + '\'' +
                    ", BTCPrice='" + BTCPrice + '\'' +
                    ", FlagUrl='" + FlagUrl + '\'' +
                    '}';
        }
}
