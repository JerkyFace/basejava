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
    posNameInput.pattern = "[a-zA-Zа-яА-Я0-9,-:(-)-]+[a-zA-Zа-яА-Я0-9\s,-:(-)-]+";
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

    positionContainer.append(document.createElement('br'));
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
    index = document.getElementsByClassName(sectionType).length;
    let orgBlock = document.createElement('div');
    let orgContainer = document.querySelector('.' + sectionType + 'content-container');
    let orgNameLabel = document.createTextNode('Название организации');
    let orgName = document.createElement('input');
    let orgUrlLabel = document.createTextNode('Сайт организации');
    let orgUrl = document.createElement('input');
    let positionContainer = document.createElement('div');
    let positionButton = document.createElement('button');

    orgBlock.classList.add(sectionType);

    orgName.type = 'text';
    orgName.size = 80;
    orgName.classList.add('form-control');
    orgName.classList.add(sectionType);
    orgName.required = true;
    orgName.pattern = "[a-zA-Zа-яА-Я0-9,-:(-)-]+[a-zA-Zа-яА-Я0-9\s,-:(-)-]+";
    orgName.name = sectionType;

    orgUrl.type = 'text';
    orgUrl.size = 80;
    orgUrl.classList.add('form-control');
    orgUrl.name = sectionType + 'organization_url';

    positionContainer.id = sectionType + index;

    positionButton.classList.add('btn');
    positionButton.classList.add('btn-outline-dark');
    positionButton.type = 'button';
    positionButton.style.marginBottom = '10px';
    positionButton.innerHTML = 'Добавить должность'

    positionButton.onclick = function () {
        createPositionForm(Number(index), sectionType + index, sectionType);
    }

    orgBlock.appendChild(document.createElement('br'));
    orgBlock.appendChild(orgNameLabel);
    orgBlock.appendChild(orgName);
    orgBlock.appendChild(orgUrlLabel);
    orgBlock.appendChild(orgUrl);
    orgBlock.appendChild(positionContainer);
    orgBlock.appendChild(positionButton);
    orgContainer.appendChild(orgBlock);

    createPositionForm(Number(index), sectionType + index, sectionType);
}