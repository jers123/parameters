/*const {
    disabledUpdateEntity
    ,displayDataGender
} = require('./gender');
const {showNotification} = require('./notification');
const {hiddenUpdateEntity} = require('./system');*/

const apiUrl = 'http://192.168.1.9:8001/parameters/';
const genderPath = 'genders';
const credentials = btoa(`jers:parameters`);

function getCreate(path, entity) {
    const subUrl = apiUrl + path + '/create';
    fetch(subUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify(entity)
    })
    .then(response => response.json())
    .then(replyMessage => {
        responseApi(path, replyMessage);
    })
    .catch(error => {
        errorConnection(error);
    });
}

function getAll(path) {
    const subUrl = apiUrl + path + '/get-all';
    fetch(subUrl, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => response.json())
    .then(replyMessage => {
        if(replyMessage.error) {
            showNotification('error', replyMessage.message);
        } else {
            if(path == genderPath) {
                displayDataGender(replyMessage);
            }
        }
    })
    .catch(error => {
        errorConnection(error);
    });
}

function getById(path, id) {
    const subUrl = apiUrl + path + '/get-id/' + id;
    fetch(subUrl, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => response.json())
    .then(replyMessage => {
        if(path == genderPath) {
            if(replyMessage.error) {
                showNotification('error', replyMessage.message);
            } else {
                showUpdateEntity(replyMessage);
            }
        }
    })
    .catch(error => {
        errorConnection(error);
    });
}

function getUpdate(path, entity) {
    hideNotification();
    const subUrl = apiUrl + path + '/update';
    fetch(subUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify(entity)
    })
    .then(response => response.json())
    .then(replyMessage => {
        responseApi(path, replyMessage);
    })
    .catch(error => {
        errorConnection(error);
    });
}

function getDelete(path, id) {
    console.log(credentials);
    hideNotification();
    const subUrl = apiUrl + path + '/delete/' + id;
    fetch(subUrl, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Authorization': `Basic ${credentials}`
        }
    })
    .then(response => response.json())
    .then(replyMessage => {
        responseApi(path, replyMessage);
    })
    .catch(error => {
        errorConnection(error);
    });
}

function responseApi(path, replyMessage) {
    const updateElement = document.getElementById('updateEntity');
    if(replyMessage.error) {
        if(getComputedStyle(updateElement).visibility != 'hidden') {
            disabledUpdateEntity(true);
        }
        showNotification('error', replyMessage.message);
    } else {
        if(getComputedStyle(updateElement).visibility != 'hidden') {
            hiddenUpdateEntity();
        }
        getAll(path);
        cleanFields();
        showNotification('ok', replyMessage.message);
    }
}

function errorConnection(error) {
    //console.error('Error fetching data: ', error);
    showNotification('error', ['No connect with the API']);
}

/*module.exports = {
    genderPath,
    getCreate,
    getAll,
    getById,
    getUpdate,
    getDelete,
    responseApi,
    errorConnection
};*/