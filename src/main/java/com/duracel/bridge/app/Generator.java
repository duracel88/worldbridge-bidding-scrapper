package com.duracel.bridge.app;

import com.duracel.bridge.csv.CsvPrinter;
import com.duracel.bridge.model.Board;
import com.duracel.bridge.pages.BoardSummaryPage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.util.List;

@RequiredArgsConstructor
public class Generator {
    private final static String URL_TEMPLATE = "BoardAcrossKO.asp?qboard=%s.%s.2354&qphase=%s";
    private final static String FILE_TEMPLATE = "/%s.csv";

    private final Fetcher fetcher;


    @SneakyThrows
    public void generate(String targetDirectory, List<String> selectors, String phase) {
        for (String selector : selectors) {
            BoardSummaryPage boardSummaryPage = new BoardSummaryPage(fetcher, String.format(URL_TEMPLATE, selector, phase, phase));
            Board board = boardSummaryPage.asBoard();
            CsvPrinter printer = new CsvPrinter();
            printer.print(new FileWriter(targetDirectory + String.format(FILE_TEMPLATE, selector)), board);
            System.out.println("Generated " + selector + " " + phase);
        }

    }
}
