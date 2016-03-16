
import java.sql.*;
import java.util.ArrayList;

public class Workout {

    public static int createOutdoor(Connection connection, ArrayList<Integer> activities, java.sql.Date date, int shape, int performance, String notes, int spectators, String weather, Float temp) {

        int ØktID = Workout.createWorkout(connection, activities, date, shape, performance, notes, spectators, null);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO UTENDØRS (TreningsØktID, Vær, Temp) VALUES (?, ?, ?)");
            post.setInt(1, ØktID);
            post.setString(2, weather);
            post.setFloat(3, temp);
            post.execute();
            return ØktID;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ØktID;
        }

    }


    public static void createOutdoorFromTemplate(Connection connection, int template, java.sql.Date date, int shape, int performance, String notes, int spectators, String weather, Float temp) {
        ArrayList<Integer> activities = Workout.getActivitiesFromWorkout(connection, template);
        Workout.createOutdoor(connection, activities, date, shape, performance, notes, spectators, weather, temp);
    }


    public static int createIndoor(Connection connection, ArrayList<Integer> activities, java.sql.Date date, int shape, int performance, String notes, int spectators, String airQuality) {

        int ØktID = Workout.createWorkout(connection, activities, date, shape, performance, notes, spectators, null);

        try {
            PreparedStatement post = connection.prepareStatement("INSERT INTO INNENDØRS (TreningsØktID, Luftkvalitet) VALUES (?, ?)");
            post.setInt(1, ØktID);
            post.setString(2, airQuality);
            post.execute();
            return ØktID;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ØktID;
        }
    }

    public static void createIndoorFromTemplate(Connection connection, int template, java.sql.Date date, int shape, int performance, String notes, int spectators, String airQuality) {
        ArrayList<Integer> activities = Workout.getActivitiesFromWorkout(connection, template);
        Workout.createIndoor(connection, activities, date, shape, performance, notes, spectators, airQuality);
    }


    public static ArrayList<Integer> getActivitiesFromWorkout(Connection connection, int workoutID) {

        ArrayList<Integer> activities = new ArrayList<Integer>();

        try {
            PreparedStatement activityQuery = connection.prepareStatement("SELECT * FROM TRENINGSØKTØVELSE WHERE TreningsØktID = ?");
            activityQuery.setInt(1, workoutID);
            ResultSet rs = activityQuery.executeQuery();

            while (rs.next()) {
                int activityID = Integer.parseInt(rs.getString("ØvelseID"));
                activities.add(activityID);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return activities;
    }

    private static Integer createWorkout(Connection connection, ArrayList<Integer> activities, java.sql.Date date, int shape, int performance, String notes, int spectators, Integer template) {

        // Makes a workout entry and returns the primary key
        // The primary key is used to make outdoor and indoor entries

        try {
            PreparedStatement workoutPost = connection.prepareStatement("INSERT INTO TRENINGSØKT (Dato_tid, Form, Prestasjon, Notat, Antall_tilskuere, ØktMalID) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            workoutPost.setDate(1, date);
            workoutPost.setInt(2, shape);
            workoutPost.setInt(3, performance);
            workoutPost.setString(4, notes);
            workoutPost.setInt(5, spectators);

            if (template == null) {
                workoutPost.setNull(6, 0);
            } else {
                workoutPost.setInt(6, template);
            }

            workoutPost.executeUpdate();
            ResultSet rs = workoutPost.getGeneratedKeys();
            rs.next();
            Integer ØktID = rs.getInt(1);

            // Connect activity to workout
            for (Integer item : activities) {
                try {
                    PreparedStatement activityPost = connection.prepareStatement("INSERT INTO TRENINGSØKTØVELSE (TreningsØktID, ØvelseID) VALUES (?, ?)");
                    activityPost.setInt(1, ØktID);
                    activityPost.setInt(2, item);
                    activityPost.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return ØktID;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Workout> getAllTemplates(Connection con){
        ArrayList<Workout> list = new ArrayList<>();
        //TODO (hvis vi ønsker å implementere dette)
        return list;
    }

    public static boolean deleteWorkoutTemplate(Connection con, int workoutID){
        try {
            PreparedStatement post = con.prepareStatement("DELETE FROM TRENINGSØKT WHERE TreningsØktID LIKE ?");
            post.setInt(1, workoutID);
            post.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }



    public static void main(String[] args){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/Trening?useSSL=false";
        String user = "user";
        String password = "12345678";

        try {
            con = DriverManager.getConnection(url, user, password);
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ArrayList<Integer> activities = new ArrayList<Integer>();
            activities.add(1);
            Workout.createOutdoor(con, activities, sqlDate, 4, 4, "Notes", 0, "Pretty good", (float)22.1);
            //Workout.createIndoor(con, activities, sqlDate, 2, 6, "Notes", 0, "Good");

            //Workout.createOutdoorFromTemplate(con, 25, sqlDate, 4, 4, "Notes", 0, "Ok weather", (float)20.0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }

    }


}
