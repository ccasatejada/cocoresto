package helpers;

public class Alert {

    public static String setAlert(String title, String message, String type) {

        String html = "<div class=\"alert alert-" + type + " alert-dismissible\" role=\"alert\">"
                + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>";
        if (!title.isEmpty()) {
            html += "<strong>" + title + "</strong> ";
        }
        html += message;
        html += "</div>";

        return html;
    }

}
