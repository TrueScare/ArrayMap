package Tests;

import IUClasses.ArrayMap;

import java.util.Map;

public class ArrayMapTest {
    ArrayMap<Integer,String> arrayMap;
    public ArrayMapTest(){
        arrayMap = new ArrayMap<>();

        arrayMap.put(8,"Thomas");
        arrayMap.put(12,"Johannes");
        arrayMap.put(32,"Carla");
        arrayMap.put(80,"Mathilda");

        assumeEqual(arrayMap.size(), 4);

        assumeContainsKey(arrayMap,80);
        arrayMap.remove(80);
        assumeNotContainsKey(arrayMap,80);

        assumeNotContainsKey(arrayMap,81);

        assumeContainsValue(arrayMap,"Thomas");
        assumeNotContainsValue(arrayMap,"Gerrit");

        assumeEqual(arrayMap.get(8), "Thomas");

        assumeEqual(arrayMap.size(), 3);
        arrayMap.clear();
        assumeEqual(arrayMap.size(), 0);
    }
    private boolean assumeEqual(Object pObject1, Object pObject2){
        System.out.printf("Assuming %s equals %s%n", pObject1, pObject2);
        boolean result = pObject1.equals(pObject2);
        System.out.println(result);
        return result;
    }

    private boolean assumeNotEqual(Object pObject1, Object pObject2){
        System.out.printf("Assuming %s not equals %s%n", pObject1, pObject2);
        boolean result = !pObject1.equals(pObject2);
        System.out.println(result);
        return result;
    }

    private boolean assumeContainsKey(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s contains key %s%n", pMap, pObject);
        boolean result = pMap.containsKey(pObject);
        System.out.println(result);
        return result;
    }

    private boolean assumeNotContainsKey(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s not contains key %s%n", pMap, pObject);
        boolean result = !pMap.containsKey(pObject);
        System.out.println(result);
        return result;
    }

    private boolean assumeContainsValue(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s contains value %s%n", pMap, pObject);
        boolean result = pMap.containsValue(pObject);
        System.out.println(result);
        return result;
    }

    private boolean assumeNotContainsValue(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s not contains value %s%n", pMap, pObject);
        boolean result = !pMap.containsValue(pObject);
        System.out.println(result);
        return result;
    }
}
