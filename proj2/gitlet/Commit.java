package gitlet;

// TODO: any imports you need here
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable{
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private long timestamp;
    private String uid;
    private Map<String , String>files;


    public Commit(String message, long timestamp){
        this.message = message;
        this.timestamp = timestamp;
        this.uid = Utils.sha1(message, timestamp);
        this.files = new HashMap<>();
    }
    public String  getMessage(){
        return message;
    }
    public String ggetUID() {
        return uid;
    }
    public boolean containsFile(String fileName, String fileHash){
        return files.containsKey(fileName) && files.get(fileName).equals(fileHash);
    }





    /* TODO: fill in the rest of this class. */
}
