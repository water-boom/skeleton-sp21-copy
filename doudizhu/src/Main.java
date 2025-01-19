import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> zhengshu = new ArrayList<>();
        zhengshu.add("zhangsan,23");
        zhengshu.add("lisi,24");
        zhengshu.add("wangwu,25");
        zhengshu.add("zhaoliu,26");
        Map<String, Integer> map = zhengshu.stream()
                .filter(s -> Integer.parseInt(s.split(",")[1]) > 24)
                .collect(Collectors.toMap(s -> s.split(",")[0], s -> Integer.parseInt(s.split(",")[1])));
        System.out.println(map);
    }
}