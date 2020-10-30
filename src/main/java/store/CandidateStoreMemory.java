package store;

import model.Candidate;

public class CandidateStoreMemory extends StoreMemory<Candidate> {
    private static final CandidateStoreMemory INSTANCE = new CandidateStoreMemory();

    private CandidateStoreMemory() {
    }

    public static Store<Candidate> getInstance() {
        return INSTANCE;
    }

}