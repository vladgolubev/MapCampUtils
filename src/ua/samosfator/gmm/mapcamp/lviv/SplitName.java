package ua.samosfator.gmm.mapcamp.lviv;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitName {
    public static void split(String originalName, String editedName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(originalName));
        List<String[]> rows = reader.readAll();
        List<String[]> editedRows = new ArrayList<>();

        for (String[] raw : rows) {
            String[] split = raw[1].split(",");
            String[] row = new String[3 + split.length];
            row[0] = raw[0];
            System.arraycopy(split, 0, row, 0, split.length);

            editedRows.add(row);
        }

        CSVWriter writer = new CSVWriter(new FileWriter(editedName));
        writer.writeAll(editedRows);
    }
}
