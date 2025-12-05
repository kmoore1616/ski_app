/*
    Sprint 1:
    Done Basic UI (different views for different screen?)
    Done Basic Controller
    Done Implement:
        Done TourDay class
        Done ResortDay class

    Sprint 2:
    TODO Spin up DB CRUD
    TODO Setup arraylist for SkiDay and associated methods
    TODO Finish all gui elements for "Ski Day" Tab
 */

public class Main {
    public static void main(String[] args){
        ReviewView view = new ReviewView();
        view.initUI();

        SkiDayController controller = new SkiDayController(view);

    }
}
