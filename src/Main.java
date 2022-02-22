import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(90, 15, 3, 300.01);
        GameProgress gameProgress3 = new GameProgress(75, 20, 4, 100.22);

        String workDir = "C:\\Games\\savegames\\";

        List<String> files = new ArrayList<>();
        if (saveGame(workDir + "save1.dat", gameProgress1)) files.add(workDir + "save1.dat");
        if (saveGame(workDir + "save2.dat", gameProgress2)) files.add(workDir + "save2.dat");
        if (saveGame(workDir + "save3.dat", gameProgress3)) files.add(workDir + "save3.dat");

        zipFiles(workDir + "zip.zip", files);

        File dir = new File(workDir);
        if (dir.listFiles() != null) {
            for (File item : dir.listFiles()) {
                if (!item.getName().equals("zip.zip")) {
                    if (!item.delete()) System.out.println(item.getName() + " не удален.");
                }
            }
        }
    }

    public static boolean saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static void zipFiles(String path, List<String> files) {
        try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(new File(file).getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
