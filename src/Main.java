import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            Random random = new Random();

            URL url = new URL(" http://numbersapi.com/"+random.nextInt()+"/trivia");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println("input:      "+informationString);
                Map<Character, Double> dictionary = new HashMap<>();
                IntStream streamFromString = informationString.chars();
                // informationString.chars().forEach((e) -> dictionary.get(e) != null ? dictionary.replace((char) e, dictionary.get(e) + 1) : dictionary.put((char) e, 1));

                for (int i = 0; i < informationString.length(); i++) {
                    if (dictionary.get(informationString.charAt(i)) != null) {
                        dictionary.replace((char) informationString.charAt(i), dictionary.get(informationString.charAt(i)) + 1);
                    } else {
                        dictionary.put((char) informationString.charAt(i), 1.0);
                    }
                }
                System.out.println("dictionary:     "+dictionary);

                float averageFrequency = (float) informationString.length() / (float) dictionary.size();
                System.out.println("averageFrequency:       "+informationString.length()+"/"+dictionary.size()+"="+averageFrequency);
                Map<Double, List<Character>> frequencyMap = new HashMap<>();
                dictionary.forEach((k, v) -> {
                    Double prFr = Math.abs(averageFrequency - v);
                    if (frequencyMap.containsKey(prFr)) {
                    } else {
                        frequencyMap.put(prFr, new ArrayList<>());

                    }
                    frequencyMap.get(prFr).add(k);
                });

                 System.out.println("frequencyMap:      "+frequencyMap);

                double minDeviation = frequencyMap.keySet().stream().min(Double::compareTo).get();
                 System.out.println("minDeviation:      "+minDeviation);
                System.out.print("RESULT:       ");
                for (var myRes:frequencyMap.get(minDeviation)
                     ) {
                    System.out.print(myRes+"("+ (int)myRes+ ") ");
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
