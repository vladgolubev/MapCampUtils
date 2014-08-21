package ua.samosfator.gmm.mapcamp.lviv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static ua.samosfator.gmm.mapcamp.lviv.EditStatus.*;

public class Edit {
    private String fprint;
    private String cell_id;
    private String edit_id;

    public Edit(String fprint, String cell_id, String edit_id) {
        this.fprint = fprint;
        this.cell_id = cell_id;
        this.edit_id = edit_id;
    }

    public Edit() {
    }

    public String getStatus(String edit) throws IOException {
        String editStatus;
        if (edit != null || !edit.contains("http")) {
            Document doc = Jsoup.connect(edit).timeout(0).get();
            String html = doc.toString();

            if (!doc.location().contains("gw=") || doc.location().contains("gw=129")) {
                editStatus = LINK_MALFORMED.toString();
            } else {
                if (html.contains(PENDING_ICON) && html.contains("stat-pending")) {
                    editStatus = PENDING.toString();
                } else {
                    if (html.contains(PUBLISHED_ICON)) {
                        editStatus = PUBLISHED.toString();
                    } else {
                        if (html.contains(PLACE_DELETED)) {
                            editStatus = DELETED.toString();
                        } else {
                            editStatus = UNDEFINED.toString();
                        }
                    }
                }
            }

//            getEditInfo(html);

        } else {
            editStatus = NO_LINK.toString();
        }

        return editStatus;
    }

    private void getEditInfo(String html) {
        System.out.println(html);
        String s = html.substring(html.indexOf("edit_features") + 23, html.indexOf("edit_features") + 92).replace("\"", "");
        String[] arr = s.split(":");

        this.fprint = arr[1].split(",")[0];
        this.cell_id = arr[0];
        this.edit_id = arr[2];

        System.out.println(formUrl());
    }

    private String formUrl() {
        String url = "http://www.google.com.ua/mapmaker?gw=35&output=jsonp&geowiki_client=mapmaker" +
                "&xauth=NTNmNTU5Mzc:8lT6QJcQRHVUWasYbB2Hmihilcw&hl=uk&json={\"featureid\":{\"fprint\":\"";

        url += fprint;
        url += "\",\"cell_id\":\"";
        url += cell_id;
        url += "\"},\"detailed_history\":false,\"start\":0,\"get_moderation_info\":false,\"edit_id\":\"";
        url += edit_id;
        url += "\"}";

        return url;
    }
}