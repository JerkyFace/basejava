function createPositionForm(index, className, sectionType) {
    console.warn(index);
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

function createOrganization(index, sectionType) {
    let orgContainer = document.querySelector('.section-content-container');
    let orgNameLabel = document.createTextNode('Название организации');
    let orgName = document.createElement('input');
    let orgUrlLabel = document.createTextNode('Сайт оганизации');
    let orgUrl = document.createElement('input');
    let positionContainer = document.createElement('div');

    orgName.type = 'text';
    orgName.size = 80;
    orgName.classList.add('form-control');
    orgName.required = true;
    orgName.pattern = '[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+';
    orgName.name = sectionType;

    orgUrl.type = 'text';
    orgUrl.size = 80;
    orgUrl.classList.add('form-control');
    orgUrl.name = sectionType + 'organization_url';

    positionContainer.id = 'pos' + index;

    orgContainer.append(document.createElement('br'));
    orgContainer.append(document.createElement('br'));
    orgContainer.append(orgNameLabel);
    orgContainer.append(orgName);
    orgContainer.append(orgUrlLabel);
    orgContainer.append(orgUrl);
    orgContainer.append(positionContainer);
    orgContainer.append(document.createElement('br'));

    createPositionForm(Number(index) + 1, 'pos' + index, sectionType);
}