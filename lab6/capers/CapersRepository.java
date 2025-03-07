package capers;

import java.io.File;
import static capers.Utils.*;

/** Capers 仓库类
 * 该类用于管理 Capers 仓库的持久化数据。
 * 仓库的结构如下：
 *
 * .capers/ -- 顶级文件夹，包含所有持久化数据
 *    - dogs/ -- 包含所有狗的持久化数据的文件夹
 *    - story -- 包含当前故事的文件
 *
 * TODO: 如果你做了不同的事情，请更改上述结构。
 */
public class CapersRepository {
    /** 当前工作目录。 */
    static final File CWD = new File(System.getProperty("user.dir"));
    /** 主元数据文件夹。 */
    static final File CAPERS_FOLDER = join(CWD, "capers"); // TODO 提示：查看 Utils 中的 `join` 函数

    /**
     * 执行所需的文件系统操作以允许持久化。
     * （创建任何必要的文件夹或文件）
     * 记住：推荐的结构（你不必遵循）：
     *
     * .capers/ -- 顶级文件夹，包含所有持久化数据
     *    - dogs/ -- 包含所有狗的持久化数据的文件夹
     *    - story -- 包含当前故事的文件
     */
    public static void setupPersistence() {
        // TODO
        CAPERS_FOLDER.mkdir(); // 创建 .capers 文件夹
        File dogsFolder = Utils.join(CAPERS_FOLDER, "dogs"); // 创建 dogs 文件夹
        dogsFolder.mkdir();
        File storyFile = join(CAPERS_FOLDER, "story"); // 创建 story 文件
        if (!storyFile.exists()) {
            writeContents(storyFile, ""); // 如果 story 文件不存在，则创建一个空文件
        }
    }

    /**
     * 将第一个非命令参数追加到 .capers 目录中的 `story` 文件中。
     * @param text 要追加到故事中的文本字符串
     */
    public static void writeStory(String text) {
        // TODO
        File storyFile = join(CAPERS_FOLDER, "story"); // 获取 story 文件的路径
        String currentStory = readContentsAsString(storyFile); // 读取当前故事内容
        currentStory += text + "\n"; // 将新文本追加到当前故事内容中
        writeContents(storyFile, currentStory); // 将更新后的故事内容写回文件
        System.out.println(currentStory); // 打印当前故事内容
    }

    /**
     * 使用第一个三个非命令参数（名字、种、年龄）创建并持久化保存一只狗。
     * 还使用 toString() 打印狗的信。
     * @param name 狗的名字
     * @param breed 狗的品种
     * @param age 狗的年龄
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog newDog = new Dog(name, breed, age); // 创建新的狗对象
        newDog.saveDog(); // 持久化保存狗对象
        System.out.println(newDog.toString()); // 打印狗的信息
    }

    /**
     * 持久化地增加狗的年龄并打印庆祝信息。
     * 还使用 toString() 打印狗的信息。
     * 根据第一个非命令参数选择要庆祝生日的狗。
     * @param name 我们要庆祝生日的狗的名字
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog dog = Dog.fromFile(name); // 从文件中读取狗对象
        dog.haveBirthday(); // 增加狗的年龄并打印庆祝信息
        dog.saveDog(); // 持久化保存更新后的狗对象
    }
}