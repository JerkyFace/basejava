//TODO: fix new position descriptions
function createPositionForm(index, className, sectionType) {
    let positionContainer = document.getElementById(className);

    let posNameInput = document.createElement('input');
    let posDescInput = document.createElement('textarea');
    let posStartDateInput = document.createElement('input');
    let posEndDateInput = document.createElement('input');

    posNameInput.type = 'text';
    posNameInput.name = sectionType + 'position_name' + index;
    posNameInput.size = 80;
    posNameInput.pattern = "[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+";
    posNameInput.required = true;

    posDescInput.name = sectionType + 'position_description' + index;
    posDescInput.rows = 4;
    posDescInput.cols = 80;

    posStartDateInput.type = 'date';
    posStartDateInput.name = sectionType + 'start' + index;
    posStartDateInput.valueAsDate = new Date();

    posEndDateInput.type = 'date';
    posEndDateInput.name = sectionType + 'end' + index;
    posEndDateInput.valueAsDate = new Date();

    positionContainer.append(posNameInput);
    positionContainer.append(posDescInput);
    positionContainer.append(posStartDateInput);
    positionContainer.append(posEndDateInput);
    positionContainer.append(document.createElement('br'));
}

function createOrganizationForm() {

}