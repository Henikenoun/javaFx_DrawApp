package repository;

import model.ShapeEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileShapeRepository implements ShapeRepository {

    private static final String FILE_NAME = "shapes.txt";

    @Override
    public void save(ShapeEntity shape) {
        save(shape.getType().name(), shape.getDetails());
    }

    @Override
    public void save(String type, String details) {
        File file = new File(FILE_NAME);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write("[" + type + "] " + details);
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
}
