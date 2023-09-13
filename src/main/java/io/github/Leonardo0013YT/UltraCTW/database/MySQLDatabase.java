package io.github.Leonardo0013YT.UltraCTW.database;

import com.zaxxer.hikari.HikariDataSource;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.TopType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.IDatabase;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Request;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MySQLDatabase implements IDatabase {

    private final UltraCTW plugin;
    private final boolean enabled;
    private HikariDataSource hikari;
    private Connection connection;
    private final String CREATE_PD_DB = "CREATE TABLE IF NOT EXISTS UltraCTW_PD(UUID varchar(36) primary key, Name varchar(36), Data TEXT, Kills INT, Wins INT, Captured INT, Bounty DOUBLE);";
    private final String CREATE_MULTIPLIER = "CREATE TABLE IF NOT EXISTS Multipliers(ID INT AUTO_INCREMENT, Type varchar(10), Name varchar(20), Amount DOUBLE, Ending DATETIME, PRIMARY KEY(ID));";
    private final String INSERT_PD = "INSERT INTO UltraCTW_PD VALUES(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE Name=?;";
    private final String INSERT_PD2 = "INSERT INTO UltraCTW_PD (UUID, Name, Data, Kills, Wins, Captured, Bounty) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String SELECT_PD = "SELECT * FROM UltraCTW_PD WHERE UUID=?;";
    private final String SAVE_PD = "UPDATE UltraCTW_PD SET Data=?, Kills=?, Wins=?, Captured=?, Bounty=? WHERE UUID=?;";
    private final String RESET = "UPDATE UltraCTW_PD SET Bounty=0;";
    private final HashMap<UUID, CTWPlayer> players = new HashMap<>();

    public MySQLDatabase(UltraCTW plugin) {
        this.plugin = plugin;
        enabled = plugin.getSources().getBoolean("mysql.enabled");
        if (enabled) {
            hikari = new HikariDataSource();
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", plugin.getSources().get("mysql.host"));
            hikari.addDataSourceProperty("port", plugin.getSources().getInt("mysql.port"));
            hikari.addDataSourceProperty("databaseName", plugin.getSources().get("mysql.database"));
            hikari.addDataSourceProperty("user", plugin.getSources().get("mysql.username"));
            hikari.addDataSourceProperty("password", plugin.getSources().get("mysql.password"));
            hikari.setMaximumPoolSize(10);
            hikari.setMaxLifetime(Long.MAX_VALUE);
            plugin.sendLogMessage("§eMySQL connected correctly.");
        } else {
            File DataFile = new File(plugin.getDataFolder(), "/UltraCTW.db");
            if (!DataFile.exists()) {
                try {
                    DataFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            }
            try {
                Class.forName("org.sqlite.JDBC");
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + DataFile);
                    plugin.sendLogMessage("§eSQLLite connected correctly.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
        }
        createTable();
    }

    @Override
    public void loadMultipliers(Request request) {
        plugin.getMm().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Connection connection = getConnection();
                    String MULTI = "SELECT * FROM Multipliers;";
                    PreparedStatement select = connection.prepareStatement(MULTI);
                    ResultSet result = select.executeQuery();
                    while (result.next()) {
                        String type = result.getString("Type");
                        String name = result.getString("Name");
                        double amount = result.getDouble("Amount");
                        Date date = result.getDate("Ending");
                        plugin.getMm().addMultiplier(result.getInt("ID"), type, name, amount, date.getTime());
                    }
                    close(connection, select, result);
                    request.request(true);
                } catch (SQLException e) {
                    request.request(false);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    @Override
    public void createMultiplier(String type, String name, double amount, long ending, Request request) {
        Date date = new Date(ending);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (enabled) {
                    try {
                        Connection connection = hikari.getConnection();
                        String MULTI = "INSERT INTO Multipliers VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE Name=?;";
                        PreparedStatement insert = connection.prepareStatement(MULTI);
                        insert.setString(1, type);
                        insert.setString(2, name);
                        insert.setDouble(3, amount);
                        insert.setDate(4, date);
                        insert.setString(5, name);
                        insert.execute();
                        close(connection, insert, null);
                        plugin.sendDebugMessage("Se ha creado un Multiplicador:", "§aCantidad: §b" + amount, "§aNombre: §b" + name, "§aFin: §b" + new SimpleDateFormat("YYYY/mm/dd HH:mm:ss").format(date));
                        request.request(true);
                    } catch (SQLException e) {
                        request.request(false);
                    }
                } else {
                    try {
                        Connection connection = getConnection();
                        String MULTI = "INSERT INTO `Multipliers` (`Type`, `Name`, `Amount`, `Ending`) VALUES (?, ?, ?, ?);";
                        PreparedStatement insert = connection.prepareStatement(MULTI);
                        insert.setString(1, type);
                        insert.setString(2, name);
                        insert.setDouble(3, amount);
                        insert.setDate(4, date);
                        insert.execute();
                        close(connection, insert, null);
                        plugin.sendDebugMessage("Se ha creado un Multiplicador:", "§aCantidad: §b" + amount, "§aNombre: §b" + name, "§aFin: §b" + new SimpleDateFormat("YYYY/mm/dd HH:mm:ss").format(date));
                        request.request(true);
                    } catch (SQLException e) {
                        request.request(false);
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    @Override
    public boolean removeMultiplier(int id) {
        try {
            Connection connection = getConnection();
            String MULTI = "DELETE FROM Multipliers WHERE ID=?;";
            PreparedStatement delete = connection.prepareStatement(MULTI);
            delete.setInt(1, id);
            boolean b = delete.execute();
            close(connection, delete, null);
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void loadTopCaptured() {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            String TOP = "SELECT UUID, Name, Captured FROM UltraCTW_PD ORDER BY Captured DESC LIMIT 10;";
            select = connection.prepareStatement(TOP);
            result = select.executeQuery();
            int pos = 1;
            List<String> tops = new ArrayList<>();
            while (result.next()) {
                tops.add(result.getString("UUID") + ":" + result.getString("Name") + ":" + pos + ":" + result.getInt("Captured"));
                pos++;
            }
            plugin.getTop().addTop(TopType.CAPTURED, tops);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, select, result);
        }
    }

    @Override
    public void loadTopKills() {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            String TOP = "SELECT UUID, Name, Kills FROM UltraCTW_PD ORDER BY Kills DESC LIMIT 10;";
            select = connection.prepareStatement(TOP);
            result = select.executeQuery();
            int pos = 1;
            List<String> tops = new ArrayList<>();
            while (result.next()) {
                tops.add(result.getString("UUID") + ":" + result.getString("Name") + ":" + pos + ":" + result.getInt("Kills"));
                pos++;
            }
            plugin.getTop().addTop(TopType.KILLS, tops);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, select, result);
        }
    }

    @Override
    public void loadTopWins() {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            String TOP = "SELECT UUID, Name, Wins FROM UltraCTW_PD ORDER BY Wins DESC LIMIT 10;";
            select = connection.prepareStatement(TOP);
            result = select.executeQuery();
            int pos = 1;
            List<String> tops = new ArrayList<>();
            while (result.next()) {
                tops.add(result.getString("UUID") + ":" + result.getString("Name") + ":" + pos + ":" + result.getInt("Wins"));
                pos++;
            }
            plugin.getTop().addTop(TopType.WINS, tops);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, select, result);
        }
    }

    @Override
    public void loadTopBounty() {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            String TOP = "SELECT UUID, Name, Bounty FROM UltraCTW_PD ORDER BY Bounty DESC LIMIT 10;";
            select = connection.prepareStatement(TOP);
            result = select.executeQuery();
            int pos = 1;
            List<String> tops = new ArrayList<>();
            while (result.next()) {
                tops.add(result.getString("UUID") + ":" + result.getString("Name") + ":" + pos + ":" + (int) result.getDouble("Bounty"));
                pos++;
            }
            plugin.getTop().addTop(TopType.BOUNTY, tops);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, select, result);
        }
    }

    @Override
    public void createPlayer(UUID uuid, String name, CTWPlayer ctw) {
        try {
            Connection connection = getConnection();
            PreparedStatement insert;
            if (enabled) {
                insert = connection.prepareStatement(INSERT_PD);
            } else {
                insert = connection.prepareStatement(INSERT_PD2);
            }
            insert.setString(1, uuid.toString());
            insert.setString(2, name);
            insert.setString(3, plugin.toStringCTWPlayer(ctw));
            insert.setInt(4, 0);
            insert.setInt(5, 0);
            insert.setInt(6, 0);
            insert.setDouble(7, 0.0);
            if (enabled) {
                insert.setString(8, name);
                insert.execute();
            } else {
                insert.executeUpdate();
            }
            loadPlayerData(uuid, ctw);
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void loadPlayer(Player p) {
        String uuid = p.getUniqueId().toString();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                Connection connection = getConnection();
                PreparedStatement insert;
                if (enabled) {
                    insert = connection.prepareStatement(INSERT_PD);
                } else {
                    insert = connection.prepareStatement(INSERT_PD2);
                }
                PreparedStatement select = connection.prepareStatement(SELECT_PD);
                select.setString(1, uuid);
                ResultSet result = select.executeQuery();
                if (result.next()) {
                    loadPlayerData(p, plugin.fromStringCTWPlayer(result.getString("Data")));
                } else {
                    CTWPlayer ctw = new PlayerCTW();
                    insert.setString(1, uuid);
                    insert.setString(2, p.getName());
                    insert.setString(3, plugin.toStringCTWPlayer(ctw));
                    insert.setInt(4, 0);
                    insert.setInt(5, 0);
                    insert.setInt(6, 0);
                    insert.setDouble(7, 0.0);
                    if (enabled) {
                        insert.setString(8, p.getName());
                        insert.execute();
                    } else {
                        insert.executeUpdate();
                    }
                    loadPlayerData(p, ctw);
                }
                close(connection, insert, result);
                close(null, select, null);
            } catch (SQLException ignored) {
            }
        }, 10L);
    }

    @Override
    public void savePlayer(UUID uuid, boolean sync) {
        CTWPlayer ipd = players.get(uuid);
        if (ipd == null) return;
        if (sync) {
            try {
                Connection connection = getConnection();
                PreparedStatement save = connection.prepareStatement(SAVE_PD);
                save.setString(1, plugin.toStringCTWPlayer(ipd));
                save.setInt(2, ipd.getKills());
                save.setInt(3, ipd.getWins());
                save.setInt(4, ipd.getWoolCaptured());
                save.setDouble(5, ipd.getBounty());
                save.setString(6, uuid.toString());
                save.execute();
                close(connection, save, null);
            } catch (SQLException ignored) {
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    Connection connection = getConnection();
                    PreparedStatement save = connection.prepareStatement(SAVE_PD);
                    save.setString(1, plugin.toStringCTWPlayer(ipd));
                    save.setInt(2, ipd.getKills());
                    save.setInt(3, ipd.getWins());
                    save.setInt(4, ipd.getWoolCaptured());
                    save.setDouble(5, ipd.getBounty());
                    save.setString(6, uuid.toString());
                    save.execute();
                    close(connection, save, null);
                } catch (SQLException ignored) {
                }
            });
        }
    }

    @Override
    public void close() {
        if (enabled) {
            hikari.close();
        } else {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public HashMap<UUID, CTWPlayer> getPlayers() {
        return players;
    }

    private void loadPlayerData(UUID p, CTWPlayer pd) {
        CTWPlayer now = new PlayerCTW();
        now.setBowKillDistance(pd.getBowKillDistance());
        now.setMaxBowDistance(pd.getMaxBowDistance());
        now.setBowKills(pd.getBowKills());
        now.setBowKillDistance(pd.getBowKillDistance());
        now.setShopkeepers(pd.getShopkeepers());
        now.setBounty(pd.getBounty());
        now.setWoolCaptured(pd.getWoolCaptured());
        now.setWoolStolen(pd.getWoolStolen());
        now.setKillsWoolHolder(pd.getKillsWoolHolder());
        now.setXp(pd.getXp());
        now.setLevel(pd.getLevel());
        now.setPlaced(pd.getPlaced());
        now.setBroken(pd.getBroken());
        now.setPlayed(pd.getPlayed());
        now.setWalked(pd.getWalked());
        now.setsShots(pd.getsShots());
        now.setShots(pd.getShots());
        now.setWins(pd.getWins());
        now.setLoses(pd.getLoses());
        now.setDeaths(pd.getDeaths());
        now.setShopKeeper(pd.getShopKeeper());
        now.setKillEffect(pd.getKillEffect());
        now.setKillSound(pd.getKillSound());
        now.setTaunt(pd.getTaunt());
        now.setTrail(pd.getTrail());
        now.setWinDance(pd.getWinDance());
        now.setAssists(pd.getAssists());
        now.setWinEffect(pd.getWinEffect());
        now.setCoins(pd.getCoins());
        now.setKill5(pd.getKill5());
        now.setKill25(pd.getKill25());
        now.setKill50(pd.getKill50());
        now.setKilleffects(pd.getKilleffects());
        now.setKills(pd.getKills());
        now.setKillsounds(pd.getKillsounds());
        now.setParting(pd.getParting());
        now.setTaunts(pd.getTaunts());
        now.setTrails(pd.getTrails());
        now.setWindances(pd.getWindances());
        now.setWineffects(pd.getWineffects());
        players.put(p, now);
    }

    private void loadPlayerData(Player p, CTWPlayer pd) {
        loadPlayerData(p.getUniqueId(), pd);
    }

    private void createTable() {
        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(CREATE_PD_DB);
            st.executeUpdate(CREATE_MULTIPLIER);
            plugin.sendLogMessage("§eThe §aUltraCTW_PD§e table has been created.");
            plugin.sendLogMessage("§eThe §aMultipliers§e table has been created.");
            close(connection, st, null);
        } catch (SQLException e) {
            plugin.sendLogMessage("§cThe tables could not be created.");
        }
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null && enabled) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if (enabled) {
            return hikari.getConnection();
        }
        return connection;
    }

    @Override
    public CTWPlayer getCTWPlayer(Player p) {
        return players.get(p.getUniqueId());
    }

}