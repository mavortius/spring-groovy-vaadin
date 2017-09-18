package com.example.demo.view

import com.vaadin.annotations.Title
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewDisplay
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.spring.annotation.SpringViewDisplay
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import groovy.transform.CompileStatic

@CompileStatic
@SpringUI(path="/vaadinUI")
@Title("Vaadin and Spring")
@SpringViewDisplay
class MainUI extends UI implements ViewDisplay {
    private static final long serialVersionUID = 8038251320497558002

    private Panel display

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout()
        root.setSizeFull()
        content = root
        display = new Panel()
        display.setSizeFull()

        root.addComponent(buildHeader())
        root.addComponent(display)
        root.setExpandRatio(display, 1.0f)
    }

    static private Label buildHeader() {
        final Label mainTitle = new Label("Welcome to the Garage")

        mainTitle
    }

    @Override
    void showView(View view) {
        display.content = view as Component
    }
}
