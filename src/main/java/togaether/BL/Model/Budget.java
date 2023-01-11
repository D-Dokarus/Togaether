package togaether.BL.Model;

public class Budget {

    private int budget_id;
    private int travel;
    private int category;
    private int collaborator;
    private double limit;

    public Budget(int budget_id, int travel, int category, int collaborator, double limit) {
        this.budget_id = budget_id;
        this.travel = travel;
        this.category = category;
        this.collaborator = collaborator;
        this.limit = limit;
    }

    public Budget(int travel, int category, int collaborator, double limit) {
        this.travel = travel;
        this.category = category;
        this.collaborator = collaborator;
        this.limit = limit;
    }

    public int getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(int budget_id) {
        this.budget_id = budget_id;
    }

    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(int collaborator) {
        this.collaborator = collaborator;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budget_id=" + budget_id +
                ", travel=" + travel +
                ", category=" + category +
                ", collaborator=" + collaborator +
                ", limit=" + limit +
                '}';
    }
}
