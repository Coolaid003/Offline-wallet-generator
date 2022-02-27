package com.ashelkov.owg.wallet;

import java.util.List;

import com.ashelkov.owg.bip.Coin;
import com.ashelkov.owg.address.BIP44Address;

import static com.ashelkov.owg.bip.Constants.BIP44_PURPOSE;

public class ErgoWallet extends ColdWallet {

    public static final Coin COIN = Coin.ERG;
    public static final int PURPOSE = BIP44_PURPOSE;

    public ErgoWallet(List<BIP44Address> derivedAddresses) {
        super(derivedAddresses);
    }

    @Override
    public String getIdentifier() {
        return COIN.toString();
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        // append coin name
        result.append(COIN);
        result.append(':');

        // append addresses
        for (BIP44Address address : addresses) {
            result.append('\n');
            result.append(address.toString());
        }

        return result.toString();
    }
}