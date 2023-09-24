package com.duracel.bridge.model;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value
public class Bidding {
    String west, north, east, south;
    List<List<String>> bids;

    @Builder
    public Bidding(String west, String north, String east, String south, String bids) {
        this.west = west;
        this.north = north;
        this.east = east;
        this.south = south;
        this.bids = transformBids(bids);
    }

    private static List<List<String>> transformBids(String bids) {
        List<String> list = Arrays.stream(bids.split(" "))
                .filter(bid -> !bid.isBlank())
                .toList();

        List<List<String>> result = new ArrayList<>();
        int counter = 0;
        List<String> row = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            row.add(list.get(i));
            if(++counter >= 4){
                counter = 0;
                result.add(row);
                row = new ArrayList<>();
            }
        }
        if(!row.isEmpty()){
            result.add(row);
        }
        return result;
    }
}
