package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


public class Listmap {
    static Logger logger = Logger.getLogger(Listmap.class);
    List<Map<String, String>> lm = null;

    public Listmap() {

    }

    public Listmap(List<Map<String, String>> lm) {
        this.lm = lm;
    }

    public Map<String, String> index(int i) {
        return lm.get(i);
    }

    public int size() {
        if (lm == null) {
            return 0;
        } else {
            return lm.size();
        }
    }

    public String value(int index, String name) {
        Map<String, String> m = lm.get(index);
        return mValue(m, name);

    }

    private String mValue(Map<String, String> m, String name) {
        if (m.get(name) == null) {
            return null;
        } else {
            return m.get(name).toString();
        }

    }

    public void show() {
        List<String> keys = new ArrayList<String>();
        if (lm == null) {
            return;
        }
        Map<String, String> a = lm.get(0);

        for (String key : a.keySet()) {
            // System.out.print(key +"\t");
            keys.add(key);
        }
        // System.out.println("\n");

        for (Map<String, String> m : lm) {

            for (String key : keys) {
                System.out.println(key + ":\t" + mValue(m, key) + "\t");
            }


        }


    }

}
