package ua.samosfator.gmm.mapcamp.lviv;

import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;

public class CheckEditStatus {


    private GoogleSheets gSheets;

    public CheckEditStatus() throws IOException, ServiceException {
        gSheets = new GoogleSheets();
    }

    public CheckEditStatus check() throws IOException, ServiceException {
        List<String> edits = gSheets.read();
        edits.remove(edits.get(edits.size() - 1));

        browseLinks(edits);
        return this;
    }

    private void browseLinks(List<String> edits) throws IOException, ServiceException {
        final int from = Integer.parseInt(String.valueOf(Config.preferences.getInt("fromRange", 0)));
        final int to = Integer.parseInt(String.valueOf(Config.preferences.getInt("toRange", 0)));

        for (int i = from; i < to; i++) {
            String edit = edits.get(i);
            writeStatuses(Edit.getStatus(edit));
        }
    }


    private void writeStatuses(String editStatus) throws IOException, ServiceException {
        gSheets.write(editStatus);
    }
}
