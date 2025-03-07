package capers;

import java.io.File;
import java.io.Serializable;
import static capers.Utils.*;

/** Represents a dog that can be serialized.
 */
public class Dog implements Serializable { // 实现 Serializable 接口以便对象可以被序列化

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = join(".capers", "dogs"); // 定义狗文件夹路径

    /** Age of dog. */
    private int age; // 狗的年龄
    /** Breed of dog. */
    private String breed; // 狗的品种
    /** Name of dog. */
    private String name; // 狗的名字

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        File dogFile = join(DOG_FOLDER, name); // 创建指向狗文件的路径
        return readObject(dogFile, Dog.class); // 从文件中读取并反序列化狗对象
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1; // 增加狗的年龄
        System.out.println(toString()); // 打印狗的详细信息
        System.out.println("Happy birthday! Woof! Woof!"); // 打印生日庆祝信息
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        File dogFile = join(DOG_FOLDER, name); // 创建指向狗文件的路径
        writeObject(dogFile, this); // 将狗对象序列化并写入文件
    }

    @Override
    public String toString() {
        return String.format(
                "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
                name, breed, age); // 返回狗的详细信息字符串
    }
}