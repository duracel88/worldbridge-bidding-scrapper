package com.duracel.bridge.app;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class Fetcher {
    private final String rootDir;

    @SneakyThrows
    public Document get(String params) {
        return Jsoup.connect(getUrl(params)).get();
    }

    public String getUrl(String params){
        return rootDir + "/" + params;
    }
}
