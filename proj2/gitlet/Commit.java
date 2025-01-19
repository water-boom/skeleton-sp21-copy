package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** 表示一个gitlet提交对象。 */
public class Commit implements Serializable {
    private String message;
    private Date timestamp;
    private String id;
    private String parent;
    private Map<String, String> blobs;

    public Commit(String message, Date timestamp, String parent, Map<String, String> blobs) {
        this.message = message;
        this.timestamp = timestamp;
        this.parent = parent;
        this.blobs = blobs;
        this.id = Utils.sha1(message, timestamp.toString(), parent, blobs.toString());
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setBlobs(Map<String, String> blobs) {
        this.blobs = blobs;
    }

    public Map<String, String> getBlobs() {
        return blobs;
    }
}