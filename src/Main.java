/*
    Sprint 1:
    Done Basic UI (different views for different screen?)
    Done Basic Controller
    Done Implement:
        Done TourDay class
        Done ResortDay class

    Sprint 2:
    DONE Spin up DB CRUD **Complete except for Update** (See next sprint)
    Done Setup arraylist for SkiDay and associated methods
    Done Finish all gui elements for "Ski Day" Tab

    Sprint 3:
    DONE Finish update functionality for SkiModel
        This will include another Popup, that will re-prompt the user with the existing fields being populated.
    DONE Implement GUI for summary
    TODO and forecast screens (Ask Mal how this should be done)
    TODO Implement functionality for summary and forecast
    TODO Implement print page functionality
    TODO Sorting and (possibly) filtering contents of DB (Partially Complete)

    Beyond sprint 3, I want to use a more elegant way of displaying data, see if I can refactor my DB and codebase
    to include the original vision with TourDay and ResortDay, as well as try to do what we discussed with the
    "Social media aspect", basically using a backend server (probably local but I do have an aws sever I can use
    as well) to handle logins and then serve the associated skiday db to the client which would otherwise function
    exactly the same, uploading the updated db when the user is finished (or when it updates idk)

 */

public class Main {
    public static void main(String[] args){
        SkiDayView view = new SkiDayView();

        view.initUI();

        SkiDayController controller = new SkiDayController(view);

public class Main {
    public static void main(String[] args) throws IOException {
        MasterView masterView = new MasterView();
    }
}
