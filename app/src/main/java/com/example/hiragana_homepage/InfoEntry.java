package com.example.hiragana_homepage;

/**
 * Class for InfoEntry objects used in Info page.
 *
 * @author joelgodfrey
 */
public class InfoEntry {

    private String title;
    private String contents;
    private boolean expanded;

    public InfoEntry(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
