package gitlet;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Gitlet的驱动类，一个Git版本控制系统的子集。 */
public class Main {

    /** 用法: java gitlet.Main ARGS, 其中ARGS包含 <COMMAND> <OPERAND1> <OPERAND2> ... */
    public static void main(String[] args) throws IOException {
        // 如果args为空，提示用户输入命令
        if (args.length == 0) {
            System.out.println("请输入一个命令。");
            return;
        }

        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                // 处理`init`命令
                init();
                break;
            case "add":
                // 处理`add [filename]`命令
                if (args.length < 2) {
                    System.out.println("请输入要添加的文件名。");
                    return;
                }
                add(args[1]);
                break;
            case "commit":
                // 处理`commit [message]`命令
                if (args.length < 2) {
                    System.out.println("请输入提交信息。");
                    return;
                }
                commit(args[1]);
                break;
            case "rm":
                // 处理`rm [filename]`命令
                if (args.length < 2) {
                    System.out.println("请输入要移除的文件名。");
                    return;
                }
                rm(args[1]);
                break;
            case "log":
                // 处理`log`命令
                log();
                break;
            case "golobal-log":
                // 处理`global-log`命令
                globalLog();
                break;
            case "find":
                // 处理`find [message]`命令
                if (args.length < 2) {
                    System.out.println("请输入要查找的提交信息。");
                    return;
                }
                find(args[1]);
                break;
            case "status":
                status();
                break;
            case "checkout":
                checkout(args);
                break;
            case "branch":
                branch(args[1]);
                break;
            case "rm branch":
                rmbranch(args[1]);
                break;
            case " reset":
                reset(args[1]);
                break;
            case " merge":
                merge(args[1]);
                break;
            default:
                System.out.println("未知命令: " + firstArg);
                break;
        }
    }

    /**
     * 初始化Gitlet版本控制系统。
     * 创建.gitlet目录，并生成初始提交。
     */
    public static void init() {
        // 创建.gitlet目录
        File gitletDir = new File(".gitlet");
        if (gitletDir.exists()) {
            System.out.println("当前目录中已存在一个Git let版本控制系统。");
            return;
        }

        if (!gitletDir.mkdir()) {
            System.out.println("创建.gitlet目录失败。");
            return;
        }

        // 创建初始提交
        Commit initialCommit = new Commit("initial commit", new Date(0), null, new HashMap<>());
        File commitFile = new File(gitletDir, initialCommit.getId());
        Utils.writeObject(commitFile, initialCommit);

        // 设置master分支
        File masterBranch = new File(gitletDir, "master");
        Utils.writeContents(masterBranch, initialCommit.getId());
    }

    /**
     * 将指定文件添加到暂存区。
     * @param fileName 要添加的文件名
     * @throws IOException 如果文件读取或写入失败
     */
    public static void add(String fileName) throws IOException {
        // 检查文件是否存在
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件不存在。");
            return;
        }

        // 读取文件内容
        byte[] fileContent = Files.readAllBytes(file.toPath()); //将文件内容逐字节读取到一个字节数组中
        File stagingArea = new File(".gitlet/staging");
        if (!stagingArea.exists()) {
            stagingArea.mkdir();
        }

        // 创建暂存文件
        File stagedFile = new File(stagingArea, fileName);
        if (stagedFile.exists()) {
            byte[] stagedContent = Files.readAllBytes(stagedFile.toPath());
            if (Utils.sha1(fileContent).equals(Utils.sha1(stagedContent))) {
                return; // 如果文件内容未变化，则无需重复添加
            }
        }

        // 将文件内容写入暂存区
        Utils.writeContents(stagedFile, fileContent);
    }

    /**
     * 提交暂存区中的更改。
     * @param message 提交信息
     * @throws IOException 如果文件读取或写入失败
     */
    public static void commit(String message) throws IOException {
        // 检查提交信息是否为空
        if (message.trim().isEmpty()) {
            System.out.println("请输入提交信息。");
            return;
        }

        // 检查暂存区是否为空
        File stagingArea = new File(".gitlet/staging");
        if (!stagingArea.exists() || stagingArea.list().length == 0) {
            System.out.println("没有添加到提交的更改。");
            return;
        }

        // 获取当前提交
        Commit parentCommit = getCurrentCommit();

        // 创建新的blobs映射
        Map<String, String> newBlobs = new HashMap<>(parentCommit.getBlobs());
        for (File file : stagingArea.listFiles()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String blobId = Utils.sha1(fileContent);  // 生成blobId
            File blobFile = new File(".gitlet", blobId);// 创建blobFile
            Utils.writeContents(blobFile, fileContent); // 将文件内容写入blobFile
            newBlobs.put(file.getName(), blobId); // 将文件名和blobI
            // d映射关系存入newBlobs
        }

        // 创建新的提交对象
        Commit newCommit = new Commit(message, new Date(), getCurrentCommitId(), newBlobs);
        File newCommitFile = new File(".gitlet", newCommit.getId());
        Utils.writeObject(newCommitFile, newCommit);

        // 更新head指针
        Utils.writeContents(new File(".gitlet", "master"), newCommit.getId());

        // 清空暂存区
        for (File file : stagingArea.listFiles()) {
            file.delete();
        }
    }

    /**
     * 从暂存区或工作目录中移除文件。
     * @param fileName 要移除的文件名
     * @throws IOException 如果文件读取或写入失败
     */
    public static void rm(String fileName) throws IOException {
        File stagingArea = new File(".gitlet/staging");
        File stagedFile = new File(stagingArea, fileName); // 这行代码创建了一个 File 对象 stagedFile，它表示 stagingArea 目录中的一个名为 fileName 的文件。
        File removalArea = new File(".gitlet/removal");

        if(!removalArea.exists()){
            removalArea.mkdir();
        }
        Commit parentCommit = getCurrentCommit();

        // 如果文件在暂存区中，则删除它
        if (stagedFile.exists()) {
            Utils.writeObject(stagedFile, removalArea);
            stagedFile.delete();
        }

        // 如果文件在当前提交中，则从工作目录中删除它，并标记为待删除
        if (parentCommit.getBlobs().containsKey(fileName)) { // 检查 parentCommit 的 blobs 映射中是否包含指定的 fileName
            File file = new File(fileName);
            if (file.exists()) {
                Utils.writeObject(stagedFile, removalArea);
                file.delete();
            }
            Utils.writeContents(new File(stagingArea, fileName + ".rm"), "");
        } else if (!stagedFile.exists()) {
            System.out.println("没有理由移除该文件。");
        }
    }

    /**
     * 显示提交历史。
     * @throws IOException 如果文件读取失败
     */
    public static void log() throws IOException {
        String commitId = getCurrentCommitId();

        // 遍历提交历史并打印每个提交的信息
        while (commitId != null) {
            Commit commit = getCommitById(commitId);
            System.out.println("===");
            System.out.println("commit " + commit.getId());
            if (commit.getParent() != null) {
                Commit parentCommit = getCommitById(commit.getParent());
                if (parentCommit.getParent() != null) {
                    System.out.println("Merge: " + commit.getParent().substring(0, 7) + " " + parentCommit.getParent().substring(0, 7));
                }
            }
            System.out.println("Date: " + commit.getTimestamp());
            System.out.println(commit.getMessage());
            System.out.println();
            commitId = commit.getParent();
        }
    }
    /**
     * 显示所有提交历史。
     * @throws IOException 如果文件读取失败
     */
    public static void globalLog() throws IOException{
        File gitletDir = new File (".gitlet");
        for (File files : gitletDir.listFiles()){
            if (files.isFile()) {
                Commit commit = Utils.readObject(files, Commit.class);
                System.out.println("===");
                System.out.println("commit " + commit.getId());
                if (commit.getParent() != null) {
                    Commit parentCommit = getCommitById(commit.getParent());
                    if (parentCommit.getParent() != null) {
                        System.out.println("Merge: " + commit.getParent().substring(0, 7) + " " + parentCommit.getParent().substring(0, 7));
                    }
                }
                System.out.println("Date: " + commit.getTimestamp());
                System.out.println(commit.getMessage());
                System.out.println();


            }

        }
    }
    /**
     * 查找提交历史。
     * @param message 提交信息
     * @throws IOException 如果文件读取失败
     */

    public static void find(String message) throws IOException{
        File gitletDir = new File(".gitlet");
        for (File files : gitletDir.listFiles()){
            if (files.isFile()){
                Commit commit = Utils.readObject(files, Commit.class);
                if (commit.getMessage().equals(message)){
                    System.out.println(commit.getId());
                }
            }
        }
    }

    public static void status() throws IOException{
        File gitletDir = new File(".gitlet");
        File branchesDir = new File(gitletDir,"branch");
        File removalArea =  new File(gitletDir,"removal");

        String currentBranch = Utils.readContentsAsString(new File (gitletDir, "HEAD"));

        //显示分支
        System.out.println("===branch===");
            for (File branch : branchesDir.listFiles()){
                if (branch.getName().equals(currentBranch)){
                    System.out.println("*"+ branch.getName());
                }else{
                    System.out.println(branch.getName());
                }
            }
        System.out.println();

        File stagingArea = new File(".gitlet/staging");
        //显示暂存区文件
        System.out.println("===暂存区===");
        for (File files : stagingArea.listFiles()){
            if (files.isFile()){
                System.out.println(files.getName());
            }
        }
        System.out.println();
        //显示已删除文件
        System.out.println("===已删除文件===");
        for (File rm : removalArea.listFiles()){
            System.out.println(rm.getName());
        }
        System.out.println();

    }

    private static void checkout(String []args) throws IOException{
        //传入id和文件名的情况
        if (args.length == 4 && args[2].equals( "--")){
            checkoutFileCommit(args[1], args[3]);
        }//传入文件名的情况
        else if (args.length == 3 && args[1].equals("--")) {
            checkoutFileHead(args[2]);
        }//传入分支名的情况
        else if (args.length == 2){
            checkoutBranch(args[1]);
        }else{
            System.out.println("传入参数错误");
        }
    }

    private static void checkoutFileCommit(String commitId , String fileName) throws IOException {
        Commit commit = getCommitById(commitId);
        if (commit == null){
            System.out.println("没有该id的提交");
            return;
        }
        if (!commit.getBlobs().containsKey(fileName)){
            System.out.println("该文件不存在");
            return;
        }
        String blobId = commit.getBlobs().get(fileName);
        File blobFile = new File (".gitlet" , blobId);
        byte [] fileContent = Utils.readContents(blobFile);
        Utils.writeContents(new File(fileName),fileContent);


    }

    private static void checkoutFileHead(String fileName) throws IOException{
        Commit headCommit = getCurrentCommit();
        if (!headCommit.getBlobs().containsKey(fileName)){
            System.out.println("文件在该提交中不存在");
            return;
        }
        String bolbId = headCommit.getBlobs().get(fileName);
        File bolbFile = new File (".gitlet" , bolbId);
        byte [] fileContent = Utils.readContents(bolbFile);
        Utils.writeContents(new File(fileName) , fileContent);
    }
    private static void checkoutBranch(String branchName) throws IOException{

        File branchFile = new File(".gitlet/branches",branchName);
        if (!branchFile.exists()){
            System.out.println("没有该分支");
            return;
        }
        String currentBranch = Utils.readContentsAsString(new File(".gitlet","HEAD"));
        if (branchName.equals(currentBranch)){
            System.out.println("无须检出该分支");
        }

        String commitid = Utils.readContentsAsString(branchFile);
        Commit commit = getCommitById(commitid);
        if (commit == null){
            System.out.println("没有该id的提交");
        }

        for (String fileName : Utils.plainFilenamesIn(".")){
            if (!commit.getBlobs().containsKey(fileName) && getCurrentCommit().getBlobs().containsKey(fileName)){
                System.out.println("有跟踪的文件会被覆盖 请先删除 或添加并提交它");
                return;
            }
        }
        //清空工作目录
        for (String fileName : Utils.plainFilenamesIn(".")){
            File file = new File(fileName);
            if (file.isFile()){
                file.delete();
            }
        }
        //从提交中赋值文件
        for ( Map.Entry<String , String> entry : commit.getBlobs().entrySet()){
            String fileName = entry.getKey();
            String commmitId = entry.getValue();
            File bolbFile = new File(fileName,commmitId);
            byte [] fileContent = Utils.readContents(bolbFile);
            Utils.writeContents(new File(fileName),fileContent);

        }

        //更新HEAD并且清空暂存区

        Utils.writeContents(new File(".gitlet" , "HEAD"),branchName);
        File stagingArea = new File (".gitlet/satging");
        for(File file : stagingArea.listFiles()){
            if (file.isFile()){
                file.delete();
            }
        }


    }

    public static void branch(String branchName) throws IOException{

        File gitletDir = new File (".gitlet");
        File branchDir = new File (gitletDir,"branches");
        //检查分支目录是否创建
        if (!branchDir.exists()){
            branchDir.mkdirs();
        }
        //检查分支是否存在
        File branchFile = new File (branchDir,branchName);
        if (branchFile.exists()){
            System.out.println("当前分支已存在");
            return;
        }
        //获取当前提交的分支id
        String currentCommitId = getCurrentCommitId();
        //创建新分支文件
        Utils.writeContents(branchFile,currentCommitId);

    }

    public static void rmbranch( String branchName) throws IOException{
        File gitletDir = new File (".gitlet");
        File branchDir = new File (gitletDir,"branches");
        if (!branchDir.exists()){
            System.out.println("文件夹不存在");
            return;
        }
        //检查分支是否存在 如果存在就删除
        File branchFile = new File (branchDir , branchName);
        if (branchFile.isFile()){
            branchFile.delete();
        }else{
            System.out.println("该分支不存在");
        }
        File headFile  = new File(gitletDir,"HEAD");
        if (headFile.exists()){
            String currentId = getCurrentCommitId();
            if (branchName.equals(currentId)){
                System.out.println("不能删除档当前分支");
            }else{
                branchFile.delete();
            }
        }
    }

    public static void reset(String commitId) throws IOException{
        //获取指定的提交对象
        Commit commit = getCommitById(commitId);
        if (commit == null){
            System.out.println("没有该id的提交");
            return;
        }
        //清空工作目录
        for (String fileName : Utils.plainFilenamesIn(".")){
            File file = new File(fileName);
            if (file.isFile()){
                file.delete();
            }
        }

        //从提交中复制文件到工作目录
        for (Map.Entry<String , String> entry : commit.getBlobs().entrySet()){
            String fileName = entry.getKey();
            String bolbId = entry.getValue();
            File blobFile = new File (".gitlet" , bolbId);
            byte[] fileContent = Utils.readContents(blobFile);
            Utils.writeContents(new File(fileName) , fileContent);

        }

        //更新当前分支的头指针
            String currentBranch = Utils.readContentsAsString(new File (".gitlet" ,"HEAD"));
            File branchFile = new File (".gitlet/branches" , currentBranch);
            Utils.writeContents(branchFile , commitId);


        //清空暂存区文件
        File stagingFile = new File (".gitlet/staging" );
        for (File files : stagingFile.listFiles()){
            if (files.isFile()){
                files.delete();
            }
        }

    }

    public static void merge(String branchName) throws IOException{
        File gitletDir = new File(".getlet");
        String currentCommitId = getCurrentCommitId();
        
    }
    
    /**
     * 获取当前分支的最新提交ID。
     * @return 当前分支的最新提交ID
     * @throws IOException 如果读取文件失败
     */
    private static String getCurrentCommitId() throws IOException {
        File gitletDir = new File(".gitlet");
        File headFile = new File(gitletDir, "master");
        return Utils.readContentsAsString(headFile);
    }

    /**
     * 获取当前分支的最新提交对象。
     * @return 当前分支的最新提交对象
     * @throws IOException 如果读取文件失败
     */
    private static Commit getCurrentCommit() throws IOException {
        String commitId = getCurrentCommitId();
        return getCommitById(commitId);
    }

    /**
     * 根据提交ID获取提交对象。
     * @param commitId 提交ID
     * @return 提交对象
     * @throws IOException 如果读取文件失败
     */
    private static Commit getCommitById(String commitId) throws IOException {
        File gitletDir = new File(".gitlet");
        return Utils.readObject(new File(gitletDir, commitId), Commit.class);
    }



}