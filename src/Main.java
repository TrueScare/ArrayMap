public class Main {
    public static void main(String[] args) {
        ArrayMap<Integer, String> lAM = new ArrayMap<>();

        lAM.put(8,"Thomas");
        lAM.put(12,"Johannes");
        lAM.put(32,"Carla");
        lAM.put(80,"Mathilda");

        System.out.println(lAM.size() +" , " +lAM);

        System.out.println(lAM.containsKey(80));
        lAM.remove(80);
        System.out.println(lAM.containsKey(80));

        System.out.println(lAM.containsKey(81));
        System.out.println(lAM.containsValue("Thomas"));
        System.out.println(lAM.containsValue("Gerrit"));

        System.out.println(lAM.get(8));

        System.out.println(lAM.size() +" , " +lAM);
        lAM.clear();
        System.out.println(lAM.size() +" , " +lAM);
    }
}