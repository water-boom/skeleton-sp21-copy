package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 *
 */
import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            Utils.message("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                add(args[1]);
                break;
            // TODO: FILL THE REST IN
        }
    }
    private static void init(){
        File gitletDir = new File(".gitlet");
        if (gitletDir.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        if (gitletDir.mkdir()){
            try{
                //创建初识提交并保存
                Commit initiaCommit = new Commit ("initial commit", 0);
                File commitFile = Utils.join(gitletDir, "commits");
                Utils.writeObject(commitFile, initiaCommit);

                //创建分支
                File masterBranch = new File (gitletDir,"HEAD");
                Utils.writeContents(masterBranch,"master");

                File head = new File  (gitletDir,"HEAD");
                Utils.writeContents(head,"master");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    private static void add(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("File does not exist;");
            return;}
        File gitletDir = new File(".gitlet");
        File stagingArea = Utils.join(gitletDir,"staging");
        if (!stagingArea.exists()){
            stagingArea.mkdir();
        }
        File currentCommit = Utils.join(gitletDir,"commits");



    }

}

