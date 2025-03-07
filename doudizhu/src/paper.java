import java.util.ArrayList;
import java.util.Collections;

public class paper {
    static ArrayList<String> pokerList = new ArrayList<>();
    static{
        String [] color = {"♠","♥","♣","♦" };
        String [] number = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        ArrayList<String> pokerList = new ArrayList<>();
        for (String c : color) {
            for (String n : number) {
                pokerList.add(c+n);
            }
        }
        pokerList.add("大王");
        pokerList.add("小王");
    }
    public paper(){
        System.out.println("新牌已经发好了");
        Collections.shuffle(pokerList) ;
        ArrayList <String> lord = new ArrayList<>();
        ArrayList <String> player1 = new ArrayList<>();
        ArrayList <String> player2 = new ArrayList<>();
        ArrayList <String> player3= new ArrayList<>();
        for (int i = 0; i < pokerList.size(); i++) {
            String p = pokerList.get(i);
            if (i>=51){
                lord.add(p);
            }
            else if (i%3==0){
                player1.add(p);
            }
            else if (i%3==1){
                player2.add(p);
            }
            else if (i%3==2){
                player3.add(p);
            }
        }

    }
    public void lookPaper(String name , ArrayList<String> player){
        System.out.print(name + "的牌是：");
        for (String p : player) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
    public static void sortPaper(ArrayList<String> player){
        
    }
}
