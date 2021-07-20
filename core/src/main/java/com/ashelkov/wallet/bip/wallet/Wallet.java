package com.ashelkov.wallet.bip.wallet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ashelkov.wallet.bip.Coin;
import com.ashelkov.wallet.bip.address.Bip44Address;

public abstract class Wallet {

    protected final Coin coin;

    protected Wallet(Coin coin) {
        this.coin = coin;
    }

    protected static final Logger logger = LoggerFactory.getLogger(Wallet.class);

    protected static final String UNUSED_BIP_PATH_MSG_BASE = "'%s' BIP44 field not used for %s; value '%d' ignored";
    protected static final String MISSING_BIP_PATH_MSG_BASE = "'%s' BIP44 field not specified for %s; defaulting to '0'";
    protected static final String ACCOUNT = "account";
    protected static final String CHANGE = "change";
    protected static final String INDEX = "address index";

    private static void logWarning(String path, String coin, int val) {
        logger.warn(String.format(UNUSED_BIP_PATH_MSG_BASE, path, coin, val));
    }

    private static void logMissing(String path, String coin) {
        logger.info(String.format(MISSING_BIP_PATH_MSG_BASE, path, coin));
    }

    protected void logWarning(String path, int val) {
        logWarning(path, coin.toString(), val);
    }

    protected void logMissing(String path) {
        logMissing(path, coin.toString());
    }

    public abstract List<Bip44Address> generateAddresses(
            Integer account,
            Integer change,
            Integer index,
            int numAddresses);

    public abstract List<Bip44Address> generateDefaultAddresses(int numAddresses);

}
