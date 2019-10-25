package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class BuildPublicContent extends VerticalLayout {
    BuildPublicContent() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Text text1 = new Text("This application can be used to get hands-on experience with practice code for OCA purposes." +
                "To ensure that you can keep track of your progress and save any code you have written, please register and log in." +
                "Best of luck!");
        add(text1);
    }
}