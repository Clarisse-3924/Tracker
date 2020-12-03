package wildlifetracker;

import org.sql2o.Sql2o;
public class DB {


    //public static Sql2o sql2o = new Sql2o( "jdbc:postgresql://ec2-54-237-135-248.compute-1.amazonaws.com:5432/dh2sr63eividr", "cmnnjoosswkjam", "2f00aaf76768a1b13c2ba39960c9d39e2d5a39229aed2ae9d4b372a7ec6bb852");

    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "clara", "abc");
}