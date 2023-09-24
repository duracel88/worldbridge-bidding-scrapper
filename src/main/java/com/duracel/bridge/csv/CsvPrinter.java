package com.duracel.bridge.csv;

import com.duracel.bridge.model.Bidding;
import com.duracel.bridge.model.Board;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.util.List;

public class CsvPrinter {
    private final CSVFormat format = CSVFormat.DEFAULT
            .withDelimiter(';');

    @SneakyThrows
    public void print(FileWriter fileWriter, Board board) {
        try (CSVPrinter printer = new CSVPrinter(fileWriter, format)) {
            printer.println();
            printer.printRecord(null, board.north(), null);
            printer.printRecord(board.west(), null, board.east());
            printer.printRecord(null, board.south(), null);
            printer.println();
            printer.println();
            for (Bidding bidding : board.biddings()) {
                printer.printRecord(bidding.west(), bidding.north(), bidding.east(), bidding.south());
                for(List<String> row : bidding.bids()){
                    printer.printRecord(row);
                }
                printer.println();
                printer.println();
            }
        }
    }
}
