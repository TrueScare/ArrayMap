package Tests;

import IUClasses.ArrayMap;

import java.lang.reflect.Method;
import java.util.Map;

public class ArrayMapTest {
    ArrayMap<Integer,String> arrayMap;
    public ArrayMapTest(){
        arrayMap = new ArrayMap<>();

        arrayMap.put(8,"Thomas");
        arrayMap.put(12,"Johannes");
        arrayMap.put(32,"Carla");
        arrayMap.put(80,"Mathilda");

        System.out.println(assumeEqual(arrayMap.size(), 4));

        System.out.println(assumeContainsKey(arrayMap,80));
        arrayMap.remove(80);
        System.out.println(assumeNotContainsKey(arrayMap,80));

        System.out.println(assumeNotContainsKey(arrayMap,81));

        System.out.println(assumeContainsValue(arrayMap,"Thomas"));
        System.out.println(assumeNotContainsValue(arrayMap,"Gerrit"));

        System.out.println(assumeEqual(arrayMap.get(8), "Thomas"));

        System.out.println(assumeEqual(arrayMap.size(), 3));
        arrayMap.clear();
        System.out.println(assumeEqual(arrayMap.size(), 0));
    }
    private boolean assumeEqual(Object pObject1, Object pObject2){
        System.out.printf("Assuming %s equals %s%n", pObject1, pObject2);
        return pObject1.equals(pObject2);
    }

    private boolean assumeNotEqual(Object pObject1, Object pObject2){
        System.out.printf("Assuming %s not equals %s%n", pObject1, pObject2);
        return !pObject1.equals(pObject2);
    }

    private boolean assumeContainsKey(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s contains key %s%n", pMap, pObject);
        return pMap.containsKey(pObject);
    }

    private boolean assumeNotContainsKey(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s not contains key %s%n", pMap, pObject);
        return !pMap.containsKey(pObject);
    }

    private boolean assumeContainsValue(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s contains value %s%n", pMap, pObject);
        return pMap.containsValue(pObject);
    }

    private boolean assumeNotContainsValue(Map<?,?> pMap, Object pObject){
        System.out.printf("Assuming %s not contains value %s%n", pMap, pObject);
        return !pMap.containsValue(pObject);
    }
}
