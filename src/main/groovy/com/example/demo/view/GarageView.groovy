package com.example.demo.view

import com.example.demo.domain.Driver
import com.example.demo.domain.Make
import com.example.demo.domain.Model
import com.example.demo.domain.Vehicle
import com.example.demo.repository.DriverRepository
import com.example.demo.repository.MakeRepository
import com.example.demo.repository.ModelRepository
import com.example.demo.repository.VehicleRepository
import com.vaadin.data.HasValue
import com.vaadin.data.ValueProvider
import com.vaadin.event.selection.SingleSelectionEvent
import com.vaadin.event.selection.SingleSelectionListener
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Grid
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.ItemCaptionGenerator
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@Slf4j
@CompileStatic
@SpringView(name = GarageView.VIEW_NAME)
class GarageView extends VerticalLayout implements View {
    public static final String VIEW_NAME = ""

    @Autowired
    private DriverRepository driverRepository

    @Autowired
    private MakeRepository makeRepository

    @Autowired
    private ModelRepository modelRepository

    @Autowired
    private VehicleRepository vehicleRepository

    private Vehicle vehicle = new Vehicle()

    @PostConstruct
    void init() {
        final HorizontalLayout titleRow = new HorizontalLayout()
        titleRow.width = '100%'
        addComponent(titleRow)

        final HorizontalLayout inputRow = new HorizontalLayout()
        inputRow.width = '100%'
        addComponent(inputRow)

        final TextField vehicleName = buildNewVehicleName()
        final ComboBox<Make> vehicleMake = this.buildMakeComponent()
        final ComboBox<Model> vehicleModel = this.buildModelComponent()
        final ComboBox<Driver> vehicleDriver = this.buildDriverComponent()
        final Button submitBtn = buildSubmitButton()

        vehicleName.addValueChangeListener(new UpdateVehicleValueChangeListener('NAME'))
        vehicleMake.addSelectionListener(new UpdateVehicleComboBoxSelectionLister('MAKE'))
        vehicleModel.addSelectionListener(new UpdateVehicleComboBoxSelectionLister('MODEL'))
        vehicleDriver.addSelectionListener(new UpdateVehicleComboBoxSelectionLister('DRIVER'))
        submitBtn.addClickListener { event ->
            this.submit()
        }

        [vehicleName, vehicleMake, vehicleModel, vehicleDriver, submitBtn].each {
            inputRow.addComponent(it)
        }

        final HorizontalLayout dataDisplayRow = new HorizontalLayout()
        dataDisplayRow.width = '100%'
        addComponent(dataDisplayRow)
        dataDisplayRow.addComponent(this.buildVehicleComponent())
    }

    class UpdateVehicleValueChangeListener implements HasValue.ValueChangeListener {
        String eventType

        UpdateVehicleValueChangeListener(String eventType) {
            this.eventType = eventType
        }

        @Override
        void valueChange(HasValue.ValueChangeEvent event) {
            updateVehicle(eventType, event.value)
        }
    }

    class UpdateVehicleComboBoxSelectionLister implements SingleSelectionListener {
        String eventType

        UpdateVehicleComboBoxSelectionLister(String eventType) {
            this.eventType = eventType
        }

        @Override
        void selectionChange(SingleSelectionEvent event) {
            updateVehicle(eventType, event.firstSelectedItem)
        }
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    static private TextField buildNewVehicleName() {
        final TextField vehicleName = new TextField()
        vehicleName.setPlaceholder("Enter a name...")

        vehicleName
    }

    private ComboBox<Make> buildMakeComponent() {
        final List<Make> makes = makeRepository.findAll()

        final ComboBox<Make> select = new ComboBox<>()
        select.setEmptySelectionAllowed(false)
        select.setPlaceholder("Select a Make")
        select.setItemCaptionGenerator(new CustomItemCaptionGenerator())
        select.setItems(makes)

        select
    }

    class CustomItemCaptionGenerator implements ItemCaptionGenerator {
        @Override
        String apply(Object item) {
            if (item instanceof Make) {
                return (item as Make).name
            }
            if (item instanceof Driver) {
                return (item as Driver).name
            }
            if (item instanceof Model) {
                return (item as Model).name
            }
            null
        }
    }

    private ComboBox<Model> buildModelComponent() {
        final List<Model> models = modelRepository.findAll()

        final ComboBox<Model> select = new ComboBox<>()
        select.setEmptySelectionAllowed(false)
        select.setPlaceholder("Select a Model")
        select.setItemCaptionGenerator(new CustomItemCaptionGenerator())
        select.setItems(models)

        select
    }

    private ComboBox<Driver> buildDriverComponent() {
        final List<Driver> drivers = driverRepository.findAll()

        final ComboBox<Driver> select = new ComboBox<>()
        select.setEmptySelectionAllowed(false)
        select.setPlaceholder("Select a Driver")
        select.setItemCaptionGenerator(new CustomItemCaptionGenerator())
        select.setItems(drivers)

        select
    }

    private Grid buildVehicleComponent() {
        final List<Vehicle> vehicles = vehicleRepository.findAll()
        final Grid grid = new Grid<>()

        grid.with {
            setSizeFull()
            items = vehicles
            addColumn(new VehicleValueProvider('id')).caption = 'ID'
            addColumn(new VehicleValueProvider('name')).caption = 'Name'
            addColumn(new VehicleValueProvider('make.name')).caption = 'Make'
            addColumn(new VehicleValueProvider('model.name')).caption = 'Model'
            addColumn(new VehicleValueProvider('driver.name')).caption = 'Name'
        }

        grid
    }

    class VehicleValueProvider implements ValueProvider {
        String propertyName

        VehicleValueProvider(String propertyName) {
            this.propertyName = propertyName
        }

        @Override
        Object apply(Object o) {
            switch (propertyName) {
                case 'id':
                    if (o instanceof Vehicle) {
                        return (o as Vehicle).id
                    }
                    break
                case 'name':
                    if (o instanceof Vehicle) {
                        return (o as Vehicle).name
                    }
                    break
                case 'model.name':
                    if (o instanceof Vehicle) {
                        return (o as Vehicle).model.name
                    }
                    break
                case 'make.name':
                    if (o instanceof Vehicle) {
                        return (o as Vehicle).make.name
                    }
                    break
                case 'driver.name':
                    if (o instanceof Vehicle) {
                        return (o as Vehicle).driver.name
                    }
                    break
            }
            null
        }
    }

    static private Button buildSubmitButton() {
        final Button submitBtn = new Button("Add to Garage")
        submitBtn.styleName = ValoTheme.BUTTON_FRIENDLY

        submitBtn
    }

    private updateVehicle(final String eventType, final eventValue) {
        switch (eventType) {
            case 'NAME':
                if ( eventValue instanceof String ) {
                    this.vehicle.name = eventValue as String
                }
                break
            case 'MAKE':
                if ( eventValue instanceof Optional<Make> ) {
                    this.vehicle.make = (eventValue as Optional<Make>).get()
                }
                break
            case 'MODEL':
                if ( eventValue instanceof Optional<Model> ) {
                    this.vehicle.model = (eventValue as Optional<Model>).get()
                }
                break
            case 'DRIVER':
                if ( eventValue instanceof Optional<Driver> ) {
                    this.vehicle.driver = (eventValue as Optional<Driver>).get()
                }
                break
            default:
                log.error 'updateVehicle invoked with wrong eventType: {}', eventType
        }
    }

    private submit() {
        vehicleRepository.save(vehicle)
        UI.navigator.navigateTo(VIEW_NAME)
    }
}
