package config;

import repository.ConsoleShapeRepository;
import repository.DatabaseShapeRepository;
import repository.FileShapeRepository;
import repository.ShapeRepository;
import service.ShapeService;

public final class AppConfig {

    private AppConfig() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static ShapeService createShapeService() {
        return new ShapeService(createRepository("console"));
    }

    public static ShapeService createShapeService(String repositoryType) {
        return new ShapeService(createRepository(repositoryType));
    }

    public static ShapeRepository createRepository(String method) {
        if (method == null) {
            method = "console";
        }

        return switch (method.toLowerCase()) {
            case "file" -> new FileShapeRepository();
            case "database" -> new DatabaseShapeRepository();
            default -> new ConsoleShapeRepository();
        };
    }
}
