//const {disabledElements} = require('./system');
//const {disabledUpdateEntity} = require('./gender');

const editNotification = 'edit';
const deleteNotification = 'delete';

function showNotification(type, messages) {
    showNotificationFull(type, messages, null, null, null);
}

function showNotificationFull(type, messages, actionNotification, path, idEntity) {
    disabledElements(true);
    let notificationElement = document.getElementById('imgNotification');
    notificationElement.src = 'res/ing/' + type + '.png';
    notificationElement.alt = type;
    
    notificationElement = document.getElementById('txtNotifications');
    notificationElement.innerHTML = '';
    
    let messageElement;
    messages.forEach(message => {
        messageElement = document.createElement('p');
        messageElement.textContent = message;
        notificationElement.appendChild(messageElement);
    });

    notificationElement = document.getElementById('buttonNotifications');
    if(type == 'info') {
        if(actionNotification == deleteNotification) {
            notificationElement.innerHTML = `
                <button class="btn-notification error-btn-notification" onclick="getDelete('${path}', ${idEntity})">Delete</button>
                <button class="btn-notification "onclick="hideNotification()">Cancel</button>
            `;
        } else if(actionNotification == editNotification) {
            notificationElement.innerHTML = `
                <button class="btn-notification" onclick="getUpdate('${path}', updateData())">Edit</button>
                <button class="btn-notification error-btn-notification" onclick="hideNotification()">Cancel</button>
            `;
        }
    } else {
        notificationElement.innerHTML = `
            <button class="btn-notification ${type}-btn-notification" onclick="hideNotification()">Accept</button>
        `;
    }

    notificationElement = document.getElementById('notification');
    notificationElement.className = type;
    notificationElement.style.visibility = 'visible';
    notificationElement.style.opacity = '1';
    
    //notificationElement.style.top = ((window.innerHeight - notificationElement.getBoundingClientRect().height) /2) + "px";
    //notificationElement.style.left = ((window.innerWidth - notificationElement.getBoundingClientRect().width) /2) + "px";
}

function hideNotification() {
    let notificationElement = document.getElementById('notification');
    notificationElement.style.visibility = 'hidden';
    notificationElement.style.opacity = '0';
    
    notificationElement = document.getElementById('updateEntity');
    if(getComputedStyle(notificationElement).visibility == 'hidden') {
        disabledElements(false);
    } else {
        disabledUpdateEntity(false);
    }
}

/*module.exports = {
    showNotification,
    showNotificationFull,
    hideNotification
};*/