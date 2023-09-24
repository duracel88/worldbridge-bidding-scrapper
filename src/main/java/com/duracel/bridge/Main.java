package com.duracel.bridge;

import com.duracel.bridge.app.Fetcher;
import com.duracel.bridge.app.Generator;
import com.duracel.bridge.app.Selectors;
import lombok.SneakyThrows;

public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        Fetcher fetcher = new Fetcher("http://db.worldbridge.org/repository/tourn/marrakech.23/microsite/Asp");
        Generator generator = new Generator(fetcher);
        generator.generate("/tmp/bb/qf", Selectors.SELECTORS, "QF");
        generator.generate("/tmp/bb/sf", Selectors.SELECTORS, "SF");
        generator.generate("/tmp/bb/f", Selectors.SELECTORS, "FF");
    }
}