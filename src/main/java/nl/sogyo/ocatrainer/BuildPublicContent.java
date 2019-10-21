package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;

class BuildPublicContent extends VerticalLayout {
    BuildPublicContent() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Text text1 = new Text("This application can be used to get hands-on experience with practice code for OCA purposes." +
                "To ensure that you can keep track of your progress and save any code you have written, please register and log in." +
                "Best of luck!");

//        RichTextEditor editor = new RichTextEditor("[{\"insert\":\"public class test{\\n\\tpublic static void main(String[] args) {\\n\\t\\tSystem.out.println();\\n\\t}\\n}\\n\"}]");
//        editor.setId("code-editor");
//
//        Button testButton = new Button("Test that feature!");
//        testButton.addClickListener(e -> System.out.println(editor.getValue().substring(12,editor.getValue().length()-5)));
        add(text1);
    }
}