package database;

public enum DatabaseSingleton {
    INSTANCE;
    private Database database;

    public Database getDatabase() {
        return new DatabaseImpl();
    }
}
