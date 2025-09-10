import java.io.*;
import java.util.*;


public class AnalyzeFile {
    private static int schoolDays = 292;
    public static void main(String[] args) throws  IOException {

        CovidData[] avgDaily = new CovidData[schoolDays];
        for (int i = 0; i < schoolDays; i++) {
            avgDaily[i] = new CovidData(0f, 0f);
        }

        int count = 0;

        File folder = new File("analyze");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];

            if (file.isFile() && file.getName().endsWith(".csv") && file.getName().startsWith("simulator")) {
                ++count;

                BufferedReader br = new BufferedReader(new FileReader(folder + "/" + file.getName()));
                for (int j = 0; j < schoolDays; j++) {
                    StringTokenizer st = new StringTokenizer(br.readLine(), ",");
                    st.nextToken();
                    avgDaily[j].cases += Integer.parseInt(st.nextToken());
                    avgDaily[j].deaths += Integer.parseInt(st.nextToken());
                }
                br.close();
            }
        }

        if (count != 0) {
            for (int i = 0; i < schoolDays; i++) {
                avgDaily[i].cases /= count;
                avgDaily[i].deaths /= count;
            }
        }

        String outputFilename = "analyze/simulator-analyzed.csv";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFilename)));
        for (int i = 0; i < schoolDays; i++) {
            pw.println((i + 1) + "," + avgDaily[i].cases + "," + avgDaily[i].deaths);
        }
        pw.close();
    }

    public static class CovidData {
        float cases;
        float deaths;

        public CovidData(float cases, float deaths) {
            this.cases = cases;
            this.deaths = deaths;
        }
    }
}
