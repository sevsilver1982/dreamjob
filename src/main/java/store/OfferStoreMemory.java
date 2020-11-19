package store;

import model.Offer;

public class OfferStoreMemory extends StoreMemory<Offer> {
    private static final Store<Offer> INSTANCE = new OfferStoreMemory();

    private OfferStoreMemory() {
    }

    public static Store<Offer> getInstance() {
        return INSTANCE;
    }

}