package Core.Interfaces;


public interface DBConnector {
    public void OpenConnection();
    public void CloseConnection();
    public void execQuery(String query);
}
