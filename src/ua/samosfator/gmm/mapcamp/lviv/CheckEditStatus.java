package ua.samosfator.gmm.mapcamp.lviv;

import com.google.gdata.util.ServiceException;
import javafx.scene.control.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class CheckEditStatus {

    private final String PENDING_ICON = "/mapmaker/mapfiles/marker_orangeA-k.png";
    private final String PUBLISHED_ICON = "/mapmaker/mapfiles/markerA-k.png";
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
            writeStatuses(getEditStatus(edit));
        }
    }

    private String getEditStatus(String edit) throws IOException {
        String editStatus;
        if (edit != null && edit.contains("http")) {
            Document doc = Jsoup.connect(edit).timeout(0).get();
            String html = doc.toString();

            if (html.contains(PENDING_ICON) && html.contains("stat-pending")) {
                editStatus = EditStatus.PENDING.getName();
            } else if (html.contains(PUBLISHED_ICON)) {
                editStatus = EditStatus.PUBLISHED.getName();
            } else editStatus = EditStatus.UNDEFINED.getName();
        } else editStatus = "Немає посилання";
        return editStatus;
    }

    private void writeStatuses(String editStatus) throws IOException, ServiceException {
        gSheets.write(editStatus);
    }
}
