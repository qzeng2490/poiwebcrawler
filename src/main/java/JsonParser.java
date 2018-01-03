import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonParser {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        List<String> results = new ArrayList<String>();


        File[] files = new File("/Users/zengqiang/Downloads/shunde2/poi86.com").listFiles();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(files[i]));
            } catch (Exception e) {
                continue;
            }

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            String address = (String) jsonObject.get("address");
            String lnglat = (String) jsonObject.get("lnglat");
            String city = "佛山";
            String longitude = lnglat.split(",")[0];
            String latitude = lnglat.split(",")[1];
            if(name.length() > 2) {
                name = name.substring(0, name.length() - 2);
            }
            sb.append(city + "," + name + "," + address + "," + longitude + "," + latitude);
//            System.out.println(city + "," + name + "," + address + "," + longitude + "," + latitude);
            sb.append("\n");
        }

        FileWriter fw = null;
        BufferedWriter bw=null;
        PrintWriter out = null;

        try {
            fw = new FileWriter("/Users/zengqiang/Downloads/shunde2/fspoi.txt", true);
            bw = new BufferedWriter(fw);
            out= new PrintWriter(bw);
            out.print(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out.close();
            }
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
