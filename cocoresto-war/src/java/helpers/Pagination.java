package helpers;

public class Pagination {

    private final int currentPage;
    private final int totalItems;
    private int itemsPerPage;
    private int min = 0;
    private final String option;

    public Pagination(String option, int currentPage, int itemsPerPage, int totalItems) {
        this.option = option;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
    }

    public String getPagination() {
        double pages = Math.ceil(totalItems / (double) itemsPerPage);
        int page = currentPage - 1;
        
        if(currentPage > pages) {
            page = (int) pages -1;
        }

        min = page * itemsPerPage;
        itemsPerPage += min;

        String html = "";

        if (pages > 1) {

            html += "<div class=\"tile-footer dvd dvd-top\">\n"
                    + "<div class=\"row\">\n"
                    + "<div class=\"col-xs-12 text-right\">\n"
                    + "<ul class=\"pagination pagination-sm m-0\">";

            if (currentPage > 1) {
                html += "<li><a href=\"FrontController?option=" + option + "&page=" + (currentPage - 1) + "\"><i class=\"fa fa-chevron-left\"></i></a></li>";
            }

            for (int i = 1; i <= pages; i++) {
                html += " <li><a href=\"FrontController?option=" + option + "&page=" + i + "\">" + i + "</a></li>\n";
            }

            if (currentPage < pages) {
                html += "<li><a href=\"FrontController?option=" + option + "&page=" + (currentPage + 1) + "\"><i class=\"fa fa-chevron-right\"></i></a></li>\n";
            }
            
            html += "</ul>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "</div>";
        }

        return html;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getMin() {
        return min;
    }

    public int getCurrentPage() {
        return currentPage;
    }

}
