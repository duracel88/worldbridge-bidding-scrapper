package com.duracel.bridge.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchDetails {
    String openNorth;
    String openWest;
    String openEast;
    String openSouth;
    String closedNorth;
    String closedWest;
    String closedEast;
    String closedSouth;
}
