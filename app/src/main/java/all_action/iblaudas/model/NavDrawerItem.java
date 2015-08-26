package all_action.iblaudas.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class NavDrawerItem {
   // private boolean showNotify;
    private String title;
    private int icon;
    boolean showNotify;
    public NavDrawerItem() {

    }

    public NavDrawerItem(int icons, String title) {
       // this.showNotify = showNotify;
        this.icon=icons;
        this.title = title;

    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }
    public int getIcon(){return icon;}
    public void setIcon(int iconse){this.icon = iconse;}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
