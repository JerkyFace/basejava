
//todo: delete position
function createPositionForm(index, sectionType) {
    let positionContainer = document.getElementById(sectionType);

    let posNameInput = document.createElement('input');
    let posDescInput = document.createElement('textarea');
    let posStartDateInput = document.createElement('input');
    let posEndDateInput = document.createElement('input');

    posNameInput.type = 'text';
    posNameInput.name = sectionType + 'position_name' + index;
    posNameInput.size = 80;

    posDescInput.name = sectionType + 'position_description' + index;
    posDescInput.rows = 4;
    posDescInput.cols = 80;

    posStartDateInput.type = 'date';
    posStartDateInput.name = sectionType + 'start' + index;
    posStartDateInput.valueAsDate = new Date();

    posEndDateInput.type = 'date';
    posEndDateInput.name = sectionType + 'end' + index;
    posEndDateInput.valueAsDate = new Date();

    positionContainer.appendChild(posNameInput);
    positionContainer.appendChild(posDescInput);
    positionContainer.appendChild(posStartDateInput);
    positionContainer.appendChild(posEndDateInput);
}

function createOrganizationForm() {

}