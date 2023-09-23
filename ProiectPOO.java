import java.io.*;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;

public class ProiectPOO {

    public static String ceva = new String();

    public static void main(String[] args) {

//             ceva += "sunt la comanda " + args[3] + '\n';
//             ceva += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        if (args == null || args.length != 4) {
            System.out.println("Nothing to read here");
        } else {
            // file paths
            //printing("Sunt la comanda " + args[3]);

            String streamersPath = "src/main/resources/" + args[0];
            String streamsPath = "src/main/resources/" + args[1];
            String usersPath = "src/main/resources/" + args[2];
            String commandPath = "src/main/resources/" + args[3];

            // populate app data
            Application app = Application.getInstance();
            app.populateData(streamersPath, streamsPath, usersPath);

            // register app commands
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(commandPath));

                String line = reader.readLine();
                while (line != null) {
                    String[] values = line.split(" ");
                    Integer id = Integer.parseInt(values[0]);
                    Command c = null;
                    switch (values[1]) {
                        case "ADD":
                            c = new StreamerAddStreamCommand(id,
                                    Integer.parseInt(values[3]),
                                    StreamType.fromInteger(Integer.parseInt(values[2])),
                                    Integer.parseInt(values[4]),
                                    Long.parseLong(values[5]),
                                    values[6]);
                            break;

                        case "LIST":
                            if (app.getStreamers().getStreamer(id) != null) { // am streamer cu id-ul cerut
                                c = new StreamerListStreamsCommand(id);
                            }

                            if (app.getUsers().getUser(id) != null) { // am user cu id-ul cerut
                                c = new UserListCommand(id);
                            }
                            break;

                        case "DELETE":
                            c = new StreamerDeleteStreamCommand(id, Integer.parseInt(values[2]));
                            break;

                        case "LISTEN":
                            c = new UserListenCommand(id, Integer.parseInt(values[2]));
                            break;

                        case "RECOMMEND":
                            c = new UserRecommendCommand(id, StreamType.fromString(values[2]));
                            break;

                        case "SURPRISE":
                            c = new UserSurpriseCommand(id, StreamType.fromString(values[2]));
                            break;
                    }

                    if (c != null) {
                        app.registerCommand(c);
                    }

                    line = reader.readLine();
                }
                reader.close();
                app.executeCommands(); // execut toate comenzile din lista curenta
                Application.destroyInstance();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//            printing(ceva);
    }
//        public static void printing(String string) throws IOException {
//            File fileOut = null;
//            fileOut = new File("src/main/resources/output/");
//
//            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileOut)))) {
//                System.out.println(fileOut);
//                String line = null;
//
//                out.println(string);
//            }
//        }
}
