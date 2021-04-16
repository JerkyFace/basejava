function createPositionForm(index, className, sectionType) {
    let positionContainer = document.getElementById(className);

    let posNameInput = document.createElement('input');
    let posDescInput = document.createElement('textarea');
    let posStartDateInput = document.createElement('input');
    let posEndDateInput = document.createElement('input');
    let divForm = document.createElement('div');
    let descLabelBlock = document.createElement('div');
    let posLabelBlock = document.createElement('div');
    let posLabel = document.createTextNode('Должность');
    let descLabel = document.createTextNode('Обязанности');


    divForm.className = 'form-floating';

    posLabelBlock.classList.add('organization-name');

    posNameInput.type = 'text';
    posNameInput.name = sectionType + 'position_name' + index;
    posNameInput.size = 80;
    posNameInput.classList.add('form-control');
    posNameInput.pattern = "[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+";
    posNameInput.required = true;

    descLabelBlock.classList.add('organization-name');

    posDescInput.name = sectionType + 'position_description' + index;
    posDescInput.style.height = '100px';
    posDescInput.cols = 80;
    posDescInput.classList.add('form-control');

    posStartDateInput.type = 'date';
    posStartDateInput.name = sectionType + 'start' + index;
    posStartDateInput.valueAsDate = new Date();

    posEndDateInput.type = 'date';
    posEndDateInput.name = sectionType + 'end' + index;
    posEndDateInput.valueAsDate = new Date();

    posLabelBlock.appendChild(posLabel);
    descLabelBlock.appendChild(descLabel);
    divForm.append(posLabelBlock);
    divForm.append(posNameInput);
    divForm.append(descLabelBlock);
    divForm.append(posDescInput);
    divForm.append(posStartDateInput);
    divForm.append(posEndDateInput)
    positionContainer.append(divForm);
    positionContainer.append(document.createElement('br'));
}