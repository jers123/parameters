/*const {
    genderPath
    ,getCreate
    ,getAll
} = require('./apis');
const {showNotificationFull} = require('./notification');
const {
    disabledElements
    ,disabledUpdateEntityElements
    ,dateToInput
} = require('./system');*/

function createData() {
    let status = document.getElementById("genderStatus").value;
    if(status == 'none' || status == 'true') {
        status = true
    } else {
        status = false;
    }
    const gender = {
        genderName: document.getElementById("genderName").value,
        status: status
    };
    getCreate(genderPath, gender);
}

function updateData() {
    let gender;
    let statusValue = document.getElementById("genderStatusUpdate").value;
    if(statusValue == 'none') {
        gender = {
            idGender: document.getElementById("genderIdUpdate").value,
            genderName: document.getElementById("genderNameUpdate").value
        }
    } else {
        let status;
        if(statusValue == 'true') {
            status = true
        } else {
            status = false;
        }
        gender = {
            idGender: document.getElementById("genderIdUpdate").value,
            genderName: document.getElementById("genderNameUpdate").value,
            status: status
        };
    }
    return gender;
}

function displayDataGender(replyMessage) {
    const dataContainer = document.getElementById('tableB');
    dataContainer.innerHTML = '';
    let creationDate;
    let updateDate;
    let i = 1;
    replyMessage.response.forEach(item => {
        creationDate = dateToText(new Date(item.creationDate), '/', true);
        updateDate = dateToText(new Date(item.updateDate), '/', true);
        const postElement = document.createElement('tr');
        postElement.classList.add('tableTr');
        
        postElement.innerHTML = `
            <td class="table-field">${item.idGender}</td>
            <td class="table-field">${item.genderName}</td>
            <td class="table-field">${item.status}</td>
            <td class="table-field">${creationDate}</td>
            <td class="table-field">${updateDate}</td>
            <td class="table-field">
                <button id="editBtn${i}" class="btn-table btn-table-edit" onclick="getById('${genderPath}', ${item.idGender})">
                    <img class="img-table" src="res/ing/${editNotification}.png">
                </button>
            </td>
            <td class="table-field">
                <button id="deleteBtn${i}" class="btn-table btn-table-delete" onclick="showNotificationFull('info', ['Are you sure about deleting the gender: ${item.genderName}?'], '${deleteNotification}', '${genderPath}', ${item.idGender})">
                    <img class="img-table" src="res/ing/${deleteNotification}.png">
                </button>
            </td>
        `;
        dataContainer.appendChild(postElement);
        i++;
    });
}

function cleanFields() {
    let element= document.getElementById("genderName");
    element.value = '';
    console.log(element);
    
    element = document.getElementById("genderStatus");
    element.value = 'none';
    console.log(element);
}

function showUpdateEntity(replyMessage) {
    disabledElements(true);
    let updateElement = document.getElementById('genderIdUpdate');
    updateElement.value = replyMessage.response.idGender;
    updateElement = document.getElementById('genderNameUpdate');
    updateElement.value = replyMessage.response.genderName;
    updateElement = document.getElementById('genderStatusUpdate');
    updateElement.value = replyMessage.response.status;
    updateElement = document.getElementById('genderCreationDateUpdate');
    updateElement.value = dateToInput(replyMessage.response.creationDate);
    updateElement = document.getElementById('genderUpdateDateUpdate');
    updateElement.value = dateToInput(replyMessage.response.updateDate);
    console.log(updateElement);

    updateElement = document.getElementById('updateEntity');
    console.log(updateElement);
    console.log(updateElement.style.visibility);
    updateElement.style.visibility = 'visible';
    updateElement.style.opacity = '1';
}

function disabledUpdateEntity(action) {
    const updateElements = [
        document.getElementById("genderNameUpdate"),
        document.getElementById("genderStatusUpdate"),
        document.getElementById("saveButton"),
        document.getElementById("cancelUpdateBtn")
    ];
    disabledUpdateEntityElements(updateElements,action);
}

function questionUpdate() {
    disabledUpdateEntity(true);
    const name = document.getElementById("genderNameUpdate").value;
    showNotificationFull('info', ['Are you sure about editing the gender: ' + name + '?'], editNotification, genderPath, null);
}

/*module.exports = {
    createData,
    updateData,
    displayDataGender,
    cleanFields,
    showUpdateEntity,
    disabledUpdateEntity,
    questionUpdate
};*/

window.onload = function() {
    getAll(genderPath);
};