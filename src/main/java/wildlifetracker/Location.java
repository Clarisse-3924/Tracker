package wildlifetracker;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location{


    private int id;
    private  String name;

    public Location(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Location> all(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM locations";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Location.class);
        }

    }

    public void save(){
        try (Connection con=DB.sql2o.open()){
            String sql="INSERT INTO locations (name) VALUES (:name)";
            if(name.equals("")){
                throw new IllegalArgumentException("field required");
            }
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Location find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM locations WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Location.class);
        }

    }

    public List<Sighting> getLocationSightings(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT sighting_id FROM locations_sightings WHERE location_id=:location_id";
            List<Integer> sightings_ids=con.createQuery(sql)
                    .addParameter("location_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sighting> sightings=new ArrayList<Sighting>();

            for(Integer sighting_id:sightings_ids){
                String sightingsQuery="SELECT * FROM sightings WHERE id=:sighting_id";
                Sighting sighting=con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sighting.class);
                sightings.add(sighting);

            }
            if(sightings.size()==0){
                throw new IllegalArgumentException("Location has no sighting");
            }
            else {return sightings;}


        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location locations = (Location) o;
        return id == locations.id &&
                name.equals(locations.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}