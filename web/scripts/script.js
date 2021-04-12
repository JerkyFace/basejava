let posCounter = 0;
let orgCounter = 0;


let posButton = document.getElementById('position-button');
let orgButton = document.getElementById('organization-button');

function createPositionForm(index, sectionType) {
    let positionContainer = document.getElementById('position-container');

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

    posEndDateInput.type = 'date';
    posEndDateInput.name = sectionType + 'end' + index;

    positionContainer.appendChild(posNameInput);
    positionContainer.appendChild(posDescInput);
    positionContainer.appendChild(posStartDateInput);
    positionContainer.appendChild(posEndDateInput);
}

function createOrganizationForm() {

}