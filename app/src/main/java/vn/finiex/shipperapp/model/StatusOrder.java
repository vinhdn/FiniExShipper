package vn.finiex.shipperapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinh on 7/6/16.
 */

public class StatusOrder {
    @SerializedName("Status")
    private int status;
    @SerializedName("Notes")
    private String note;

    public StatusOrder(int status, String note) {
        this.status = status;
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
