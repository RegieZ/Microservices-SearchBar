package com.regino.other;

import java.util.*;

public class RandomTest {

    public static void main(String[] args) {
        while(true) {
            //组1
            List<String> list1 = new ArrayList<>();
            Collections.addAll(list1, "Adam", "Ben", "Carl");

            //组2
            List<String> list2 = new ArrayList<>();
            Collections.addAll(list2, "Eve", "Barbie", "Candy");

            Map<Integer, List<String>> map = new HashMap<>();
            map.put(1, list1);
            map.put(2, list2);

            Random random = new Random();
            //获取组序号
            Scanner scanner = new Scanner(System.in);
            int groupNum = scanner.nextInt();
            List<String> list = map.get(groupNum);
            //抽取LuckyDog
            String str = list.get(random.nextInt(list.size()));
            System.out.println(str);
        }
    }
}
