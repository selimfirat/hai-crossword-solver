package com.hai.gui.domain.merger;

import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hai.rest.Candidate;
import com.hai.rest.RestClient;
import com.hai.rest.RestModule;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Merger {

    private Puzzle puzzle;
    private RestClient restClient;

    public Merger(Puzzle puzzle) {
        this.puzzle = puzzle;
        restClient = new RestClient();
    }

    public Map<String, Domain> getDomains() {
        Map<String, Domain> domains = new HashMap<>();

        for (Clue clue : puzzle.getClues().getA())
            useModules(clue, domains, true);

        for (Clue clue : puzzle.getClues().getD())
            useModules(clue, domains, false);

        return domains;
    }

    public void useModules(Clue clue, Map<String, Domain> domains, boolean isAcross) {
        for (RestModule module : RestModule.values()) {
            List<Candidate> candidates = restClient.useModule(module, clue.getValue(), Math.abs(clue.getClueEnd() - clue.getClueStart()));
            domains.put((isAcross ? "A" : "D") + clue.getClueNum(), new Domain(candidates));
        }
    }
}