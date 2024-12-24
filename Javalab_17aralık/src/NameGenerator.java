import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class NameGenerator {
	private static List<String> names = getFromList("files/nameslist.txt");
	private static List<String> surnames = getFromList("files/surnamelist.txt");
	@SuppressWarnings("finally")
	private static List<String> getFromList(String path){
        // Path to the file
        Path filePath = Paths.get(path);
        List<String> lines = null;
        try {
            // Read all lines from the file
            lines = Files.readAllLines(filePath.toAbsolutePath());

            // Check if the file is not empty
            if (lines.isEmpty()) {
                System.out.println("The file is empty.");
                return null;
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
			return lines;
		}
	}
	public static String getRandomName() {
		NameGenerator generator = new NameGenerator();
		String name = generator.randomiseInIt(names);
		String surname = generator.randomiseInIt(surnames);
		return name + " " + surname;
	}
	private String randomiseInIt(List<String> name) {
		Random random = new Random();
		return name.get(random.nextInt(name.size()));
	}
}
