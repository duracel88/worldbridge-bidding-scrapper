package com.duracel.bridge.pages;

import com.duracel.bridge.app.Fetcher;
import com.duracel.bridge.model.MatchDetails;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

@RequiredArgsConstructor
public class MatchSummaryPage {
    private final Fetcher fetcher;
    private final String params;

    public MatchDetails asMatchDetails() {
        Document document = fetcher.get(params);

        List<String> players = document
                .selectFirst(".TextTable")
                .select("font")
                .select("a[href*=person.asp]")
                .stream()
                .map(Element::text)
                .toList();

        return MatchDetails.builder()
                .openNorth(players.get(0))
                .openWest(players.get(2))
                .openEast(players.get(3))
                .openSouth(players.get(6))
                .closedNorth(players.get(1))
                .closedWest(players.get(4))
                .closedEast(players.get(5))
                .closedSouth(players.get(7))
                .build();
    }

}
