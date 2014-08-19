package ua.samosfator.gmm.mapcamp.lviv;

import com.google.gdata.util.ServiceException;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckEditStatus {
    private List<String> edits = new ArrayList<>();
    private List<String> editStatuses = new ArrayList<>();
    private GoogleSheets gSheets;
    private ProgressBar progressBar;
    private String editColumn;
    private String statusColumn;

    public CheckEditStatus(String editColumn, String statusColumn) throws IOException, ServiceException {
        this.editColumn = editColumn;
        this.statusColumn = statusColumn;
        gSheets = new GoogleSheets();
    }

    public CheckEditStatus setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public CheckEditStatus check() throws IOException, ServiceException {
        edits = gSheets.read();
        edits.remove(edits.get(edits.size() - 1));

        browseLinks(edits);
        return this;
    }

    private void browseLinks(List<String> edits) throws IOException, ServiceException {
        String editStatus = "";
        final String PENDING_ICON = "/mapmaker/mapfiles/marker_orangeA-k.png";
        final String PUBLISHED_ICON = "/mapmaker/mapfiles/markerA-k.png";

        final int from = Integer.parseInt(String.valueOf(Config.preferences.getInt("fromRange", 0)));
        final int to = Integer.parseInt(String.valueOf(Config.preferences.getInt("toRange", 0)));

        for (int i = from; i < to; i++) {
            String edit = edits.get(i);
            if (edit!= null && edit.contains("http")) {
                Document doc = Jsoup.connect(edit).timeout(0).get();
                String html = doc.toString();

                if (html.contains(PENDING_ICON) && html.contains("stat-pending")) {
                    editStatus = EditStatus.PENDING.getName();
                } else if (html.contains(PUBLISHED_ICON) && html.contains("stat-published")) {
                    editStatus = EditStatus.PUBLISHED.getName();
                } else editStatus = EditStatus.UNDEFINED.getName();
            } else editStatus = "Немає посилання";
            writeStatuses(editStatus);
        }
    }

    private void writeStatuses(String editStatuses) throws IOException, ServiceException {
        gSheets.write(editStatuses);
    }
}
