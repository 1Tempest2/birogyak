import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> konyvtar = new HashMap<>();
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<>();
        Random rand = new Random();
        for (int i = 1; i <= 10; i++) {
            list.add(rand.nextInt(100));
            konyvtar.put(i + "niga", i);
        }
        for (int i = 1; i <= 10; i++) {
            list2.add(rand.nextInt(100));
        }

        List<Integer> sortedList = new ArrayList<>(list);
        sortedList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2,o1);
            }
        });


//        for(var x : sortedList) {
//            System.out.println(x);
//        }

//        for(Map.Entry<String, Integer> entry : konyvtar.entrySet()) {
//            String key = entry.getKey();
//            int value = entry.getValue();
//
//            System.out.println(key + " : " + value);
//        }
        Iterator it = konyvtar.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> elem = (Map.Entry<String, Integer>) it.next();
            if(elem.getKey().equals("1niga")) {
                it.remove();
                continue;
            }
            System.out.println(elem.getKey() + ": " + elem.getValue());
        }
        list.addAll(list2);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
