package ua.samosfator.gmm.mapcamp.lviv;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitName {
    public static void splitLvivList(String originalName, String editedName) throws IOException {
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

    public static void splitGasStationsList(String originalName, String editedName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(originalName));
        List<String[]> rows = reader.readAll();
        List<String[]> editedRows = new ArrayList<>();

        for (String[] raw : rows) {
            String[] split = raw[0].split(",");
            String[] coords = raw[1].split(",");

            String lat;
            String lon;
            String editedCoords;
            try {
                lat = coords[1];
                lon = coords[0];
                editedCoords = lat + ", " + lon;
            } catch (IndexOutOfBoundsException e) {
                editedCoords = "";
            }

            String[] row = new String[split.length + 3];

            row[0] = editedCoords;
            System.arraycopy(split, 0, row, 1, split.length);

            editedRows.add(row);
        }

        CSVWriter writer = new CSVWriter(new FileWriter(editedName));
        writer.writeAll(editedRows);
    }
}
