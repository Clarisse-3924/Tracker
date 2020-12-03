package wildlifetracker;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import wildlifetracker.DB;
import wildlifetracker.Dbmanagement;

import java.util.List;
import java.util.Objects;

public class AnimalType implements Dbmanagement {

    public int id;
    public String name;
    public String type;
    private String health;
    private String age;
    public static final String ANIMAL_TYPE="Canivore";



    public AnimalType(String name,String type) {
        this.name = name;
        this.type=ANIMAL_TYPE;
        this.health="";
        this.age="";
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void save(){
        if(this.name.equals("")||this.type.equals("")||this.name.equals(null)||this.type.equals(null)){
            throw new IllegalArgumentException("required");
        }
        try (Connection con= DB.sql2o.open()){


            String sql ="INSERT INTO animals (name,type) VALUES (:name,:type)";

            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .executeUpdate()
                    .getKey();
        }

    }


    public void update(int id,String type,String health,String age) {
        try (Connection con = DB.sql2o.open()) {
            if (type.equals("")) {
                throw new IllegalArgumentException("required");
            }
            if (type == "endangered") {
                if (health.equals("") || age.equals("")) {
                    throw new IllegalArgumentException("required");
                }
                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", health)
                        .addParameter("age", age)
                        .addParameter("id", this.id)
                        .executeUpdate();
            } else {

                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", "")
                        .addParameter("age", "")
                        .addParameter("id", this.id)
                        .executeUpdate();
            }

        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }







    public static AnimalType find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql= "SELECT * FROM animals WHERE id=:id";
            AnimalType animal=  con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(AnimalType.class);
            return animal;

        }

    }

    public static List<AnimalType> all(){
        try (Connection con=DB.sql2o.open()) {
            String sql ="SELECT * FROM animals";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(AnimalType.class);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalType animals = (AnimalType) o;
        return name.equals(animals.name) &&
                type.equals(animals.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,type);
    }
}