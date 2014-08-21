package ua.samosfator.gmm.mapcamp.lviv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static ua.samosfator.gmm.mapcamp.lviv.EditStatus.*;

public class Edit {
    public static String getStatus(String edit) throws IOException {
        String editStatus = "";
        if (edit != null) {
            if (edit.contains("http")) {
                Document doc = Jsoup.connect(edit).timeout(0).get();
                String html = doc.toString();

                if (doc.location().contains("gw=129")) {
                    editStatus = LINK_MALFORMED.toString();
                } else {
                    if (html.contains(PENDING_ICON) && html.contains("stat-pending")) {
                        editStatus = PENDING.toString();
                    } else {
                        if (html.contains(PUBLISHED_ICON)) {
                            editStatus = PUBLISHED.toString();
                        } else {
                            editStatus = UNDEFINED.toString();
                        }
                    }
                }
            } else {
                editStatus = LINK_MALFORMED.toString();
            }
        } else {
            editStatus = NO_LINK.toString();
        }

        return editStatus;
    }
}