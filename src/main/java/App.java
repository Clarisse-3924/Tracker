import spark.ModelAndView;
        import spark.template.handlebars.HandlebarsTemplateEngine;
import wildlifetracker.*;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        get("/",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        //ranger
        get("/create/ranger",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"rangerform.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/ranger/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            String badge_number=request.queryParams("badge");
            String phone_number=request.queryParams("phone");
            Ranger ranger=new Ranger(name,badge_number,phone_number);
            ranger.save();
            return new ModelAndView(model,"rangerform.hbs");
        },new HandlebarsTemplateEngine());
        get("/view/rangers",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("rangers",Ranger.all());
            return new ModelAndView(model,"rangerview.hbs");
        },new HandlebarsTemplateEngine());
        get("/view/ranger/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            int idOfRanger= Integer.parseInt(request.params(":id"));
            Ranger foundRanger=Ranger.find(idOfRanger);
            List<Sighting> sightings=foundRanger.getRangerSightings();
            ArrayList<String> animals=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sighting sighting : sightings){
                String animal_name= AnimalType.find(sighting.getAnimal_id()).getName();
                String animal_type=AnimalType.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("rangers",Ranger.all());
            return new ModelAndView(model,"rangerview.hbs");
        },new HandlebarsTemplateEngine());





        //location
        get("/create/location",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"locationform.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/location/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            Location location=new Location(name);
            try {
                location.save();
            }catch (IllegalArgumentException e){
                System.out.println(e);
            }

            return new ModelAndView(model,"locationform.hbs");
        },new HandlebarsTemplateEngine());
        get("/view/locations",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("locations",Location.all());
            return new ModelAndView(model,"locationview.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/location/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            int idOfLocation= Integer.parseInt(request.params(":id"));
            Location foundLocation=Location.find(idOfLocation);
            List<Sighting> sightings=foundLocation.getLocationSightings();
            ArrayList<String> animals=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sighting sighting : sightings){
                String animal_name=AnimalType.find(sighting.getAnimal_id()).getName();
                String animal_type=AnimalType.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("locations",Location.all());
            return new ModelAndView(model,"locationview.hbs");
        },new HandlebarsTemplateEngine());


        //animal
        get("/create/animal",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"Animalform.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/animal/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String type=request.queryParams("type");
            System.out.println(type);
            String health=request.queryParams("health");
            System.out.println(health);
            String age=request.queryParams("age");
            System.out.println(age);
            String name=request.queryParams("name");
            System.out.println(name);
            if(type.equals(Endangered.ANIMAL_TYPE)){
                Endangered endangered=new Endangered(name,Endangered.ANIMAL_TYPE,health,age);
                endangered.save();
            }
            else {
                AnimalType animal=new AnimalType(name,AnimalType.ANIMAL_TYPE);
                animal.save();
            }

            return new ModelAndView(model,"Animalform.hbs");
        },new HandlebarsTemplateEngine());


        get("/create/animal/endangered",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            List<String> health= new ArrayList<String>();
            health.add(Endangered.HEALTH_HEALTHY);
            health.add(Endangered.HEALTH_ILL);
            health.add(Endangered.HEALTH_OKAY);
            List<String> age= new ArrayList<String>();
            age.add(Endangered.AGE_ADULT);
            age.add(Endangered.AGE_NEWBORN);
            age.add(Endangered.AGE_YOUNG);
            model.put("health",health);
            model.put("age",age);
            String typeChosen="endangered";
            model.put("endangered",typeChosen);
            return new ModelAndView(model,"Animalform.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/animals",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("animals",AnimalType.all());
            return new ModelAndView(model,"Animalview.hbs");
        },new HandlebarsTemplateEngine());


        //sighting
        get("/create/sighting",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("rangers",Ranger.all());
            model.put("locations",Location.all());
            model.put("animals",AnimalType.all());
            return new ModelAndView(model,"sighting.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/sighting/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            int location_id= Integer.parseInt(request.queryParams("location"));
            int ranger_id= Integer.parseInt(request.queryParams("ranger"));
            int animal_id= Integer.parseInt(request.queryParams("animal"));

            Sighting sighting=new Sighting(location_id,ranger_id,animal_id);
            sighting.save();
            return new ModelAndView(model,"sighting.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/sightings",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            List<Sighting> sightings=Sighting.all();
            ArrayList<String> animals=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sighting sighting : sightings){
                String animal_name=AnimalType.find(sighting.getAnimal_id()).getName();
                String animal_type=AnimalType.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            return new ModelAndView(model,"sightingview.hbs");
        },new HandlebarsTemplateEngine());



    }
}