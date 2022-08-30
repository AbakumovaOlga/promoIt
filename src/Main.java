import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        StringBuilder informationString = new StringBuilder();

        getString(random.nextInt(), informationString);
        System.out.println("input:      " + informationString);

        Map<Character, Double> dictionaryMap = new HashMap<>();
        // informationString.chars().forEach((e) -> dictionary.get(e) != null ?
        // dictionary.replace((char) e, dictionary.get(e) + 1) : dictionary.put((char)
        // e, 1));
        countFrequency(informationString, dictionaryMap);
        System.out.println("dictionaryMap:     " + dictionaryMap);

        float averageFrequency = (float) informationString.length() / (float) dictionaryMap.size();
        System.out.println("averageFrequency:       " + informationString.length() + "/" + dictionaryMap.size()
                + "=" + averageFrequency);

        Map<Double, List<Character>> deviationMap = new HashMap<>();
        countDeviation(dictionaryMap, deviationMap, averageFrequency);
        System.out.println("deviationMap:      " + deviationMap);

        double minDeviation = deviationMap.keySet().stream().min(Double::compareTo).get();
        System.out.println("minDeviation:      " + minDeviation);
        System.out.print("RESULT:       ");
        for (var myRes : deviationMap.get(minDeviation)) {
            System.out.print(myRes + "(" + (int) myRes + ") ");
        }
    }

    private static void countDeviation(Map<Character, Double> dictionaryMap,
            Map<Double, List<Character>> deviationMap, float averageFrequency) {
        dictionaryMap.forEach((k, v) -> {
            Double prFr = Math.abs(averageFrequency - v);
            if (deviationMap.containsKey(prFr)) {
            } else {
                deviationMap.put(prFr, new ArrayList<>());

            }
            deviationMap.get(prFr).add(k);
        });
    }

    private static void countFrequency(StringBuilder informationString, Map<Character, Double> dictionaryMap) {
        for (int i = 0; i < informationString.length(); i++) {
            if (dictionaryMap.get(informationString.charAt(i)) != null) {
                dictionaryMap.replace((char) informationString.charAt(i),
                        dictionaryMap.get(informationString.charAt(i)) + 1);
            } else {
                dictionaryMap.put((char) informationString.charAt(i), 1.0);
            }
        }
    }

    private static void getString(int nextInt, StringBuilder informationString) {
        try {

            URL url = new URL(" http://numbersapi.com/" + nextInt + "/trivia");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
