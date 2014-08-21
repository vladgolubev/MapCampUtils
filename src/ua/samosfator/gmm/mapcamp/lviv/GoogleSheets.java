package ua.samosfator.gmm.mapcamp.lviv;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleSheets {
    private SpreadsheetService service;
    private List<ListEntry> entries;
    private ListEntry listEntry;
    private URL listFeedUrl;

    private int rowNumber = Integer.valueOf(String.valueOf(Config.preferences.getInt("fromRange", 0)));
    private String columnName;

    public GoogleSheets() throws IOException, ServiceException {
        columnName = Config.preferences.get("editStatusColumn", "");
        service = new SpreadsheetService("Print Google Spreadsheet Demo");
        service.setUserCredentials(Config.preferences.get("gmail", ""), Config.preferences.get("password", ""));
        URL metaFeedUrl = new URL(Config.preferences.get("spreadsheetLink", ""));
        SpreadsheetEntry spreadsheet = service.getEntry(metaFeedUrl, SpreadsheetEntry.class);
        listFeedUrl = spreadsheet.getWorksheets().get(0).getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
        entries = listFeed.getEntries();
    }

    public List<String> read() throws ServiceException, IOException {
        ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
        List<String> existing = new ArrayList<>(3000);
        existing.addAll(feed.getEntries().stream().map(entry -> entry.getCustomElements()
                .getValue(Config.preferences.get("editLinkColumn", ""))).collect(Collectors.toList()));
        return existing;
    }

    public void write(String editStatus) throws ServiceException, IOException {
        listEntry = entries.get(rowNumber);

        listEntry.getCustomElements().setValueLocal(columnName, editStatus);

        System.out.println(editStatus);

        try {
            listEntry.update();
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
            listEntry.update();
        }
        rowNumber++;
    }
}
