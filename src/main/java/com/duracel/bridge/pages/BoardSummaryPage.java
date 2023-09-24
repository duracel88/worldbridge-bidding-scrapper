package com.duracel.bridge.pages;

import com.duracel.bridge.app.Fetcher;
import com.duracel.bridge.model.Bidding;
import com.duracel.bridge.model.Board;
import com.duracel.bridge.model.Hand;
import com.duracel.bridge.model.MatchDetails;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

public class BoardSummaryPage {
    private interface Selectors {
        String NORTH_HAND = ".textTable > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2)";
        String WEST_HAND = ".textTable > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1)";
        String EAST_HAND = "td.BrdDispl:nth-child(3)";
        String SOUTH_HAND = ".textTable > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)";

        String RESULT_TABLE = "body > table:nth-child(3) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)";
    }

    private final Fetcher fetcher;
    private final String params;

    public BoardSummaryPage(Fetcher fetcher, String params) {
        this.fetcher = fetcher;
        this.params = params;
    }

    public Board asBoard() {
        Document document = fetcher.get(params);

        Hand north = new Hand(document.selectFirst(Selectors.NORTH_HAND).text());
        Hand south = new Hand(document.selectFirst(Selectors.SOUTH_HAND).text());
        Hand west = new Hand(document.selectFirst(Selectors.WEST_HAND).text());
        Hand east = new Hand(document.selectFirst(Selectors.EAST_HAND).text());

        Element resultTable = document.selectFirst(Selectors.RESULT_TABLE);

        List<MatchDetails> matchDetails = resultTable
                .select("a[href*=BoardDetailsKO]").stream()
                .map(e -> e.attr("href"))
                .map(href -> new MatchSummaryPage(fetcher, href))
                .map(MatchSummaryPage::asMatchDetails)
                .toList();

        List<String> biddings = Arrays.stream(resultTable
                        .select("span")
                        .select("table")
                        .select("td")
                        .text()
                        .split("West North East South "))
                .skip(1)
                .toList();

        Board.BoardBuilder resultBuilder = Board.builder()
                .title("Link: " + fetcher.getUrl(params))
                .north(north)
                .south(south)
                .west(west)
                .east(east);

        for (int i = 0; i < matchDetails.size(); i++) {
            MatchDetails players = matchDetails.get(i);
            String biddingOpen = biddings.get(i * 2);
            String biddingClosed = biddings.get(i * 2 + 1);
            resultBuilder.bidding(Bidding.builder()
                    .north(players.openNorth())
                    .south(players.openSouth())
                    .east(players.openEast())
                    .west(players.openWest())
                    .bids(biddingOpen)
                    .build());

            resultBuilder.bidding(Bidding.builder()
                    .north(players.closedNorth())
                    .south(players.closedSouth())
                    .east(players.closedEast())
                    .west(players.closedWest())
                    .bids(biddingClosed)
                    .build());
        }

        return resultBuilder
                .build();
    }

}
