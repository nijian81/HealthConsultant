package cn.com.asz.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by nijian on 2015/6/4.
 */
@AVClassName("LibraryItem")
public class LibraryItem extends AVObject {

    public String getDisplayName() {
        return getString("displayName");
    }
    public void setDisplayName(String value) {
        put("displayName", value);
    }
}
