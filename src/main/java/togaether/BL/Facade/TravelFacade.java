package togaether.BL.Facade;

import togaether.BL.Model.Travel;

import java.util.List;

public class TravelFacade {

    static private Travel travel = null;

    static private String collaborator = null;

    static private List<String> collaborators = null;

    public static Travel getTravel() {
        return travel;
    }

    public static String getCollaborator() {
        return collaborator;
    }

    public static List<String> getCollaborators() {
        return collaborators;
    }
}
