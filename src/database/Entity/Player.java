package database.Entity;

public class Player {
    private String playerID;
    private String password;
    private double potential;

    public Player() {
    }

    public Player(String playerID, String password, double potential) {
        this.playerID = playerID;
        this.password = password;
        this.potential = potential;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }
}
