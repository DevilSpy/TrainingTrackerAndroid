package fi.sami.trainingtracker.component;

/**
 * Created by Sami on 29.10.2015.
 */
public class ParticipantsListItem {

    private String itemTitle;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ParticipantsListItem(String title) {
        this.itemTitle = title;
    }
}
