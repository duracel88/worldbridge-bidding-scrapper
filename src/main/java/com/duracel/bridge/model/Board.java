package com.duracel.bridge.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Board {
    String title;
    Hand west, north, east, south;
    @Singular List<Bidding> biddings;
}
