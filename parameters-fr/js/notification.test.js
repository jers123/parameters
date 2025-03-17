const {
    showNotification,
    showNotificationFull,
    hideNotification
} = require('./notification');
const {disabledElements} = require('./system');
const {disabledUpdateEntity} = require('./gender');

describe('Show notification Functions', () => {
    test('showNotification should set image src and alt correctly', () => {
        let type = 'info';
        document.body.innerHTML = `
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="notification">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications"></div>
                <div id="buttonNotifications"></div>
            </dialog>
        `;
        showNotification(type, ['message1', 'message2']);
        const imgNotification = document.getElementById('imgNotification');
        expect(imgNotification.src).toContain(type + '.png');
        expect(imgNotification.alt).toBe(type);
    });

    test('showNotificationFull should display correct buttons for delete action', () => {
        let type = 'info';
        document.body.innerHTML = `
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="notification">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications"></div>
                <div id="buttonNotifications"></div>
        </dialog>
        `;
        showNotificationFull(type, ['message1', 'message2'], 'delete', 'path', 1);
        const buttonNotifications = document.getElementById('buttonNotifications');
        expect(buttonNotifications.innerHTML).toContain('Delete');
        expect(buttonNotifications.innerHTML).toContain('Cancel');
    });

    test('showNotificationFull should display correct buttons for edit action', () => {
        document.body.innerHTML = `
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="notification">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications">
                </div>
                <div id="buttonNotifications">
                </div>
            </dialog>
        `;
        showNotificationFull('info', ['message1', 'message2'], 'edit', 'path', 1);
        const buttonNotifications = document.getElementById('buttonNotifications');
        expect(buttonNotifications.innerHTML).toContain('Edit');
        expect(buttonNotifications.innerHTML).toContain('Cancel');
    });

    test('showNotificationFull should display correct button for other types', () => {
        let type = 'error'
        document.body.innerHTML = `
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="notification">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications"></div>
                <div id="buttonNotifications"></div>
            </dialog>
        `;
        showNotification(type, ['message1', 'message2']);
        const buttonNotifications = document.getElementById('buttonNotifications');
        expect(buttonNotifications.innerHTML).toContain('Accept');
    });
});

describe('Hidden notification Functions', () => {
    test('hideNotification should hide the notification element', () => {
        document.body.innerHTML = `
            <dialog id="updateEntity" style="visibility: hidden;"></dialog>
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="notification" style="visibility: visible; opacity: 1;">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications"></div>
                <div id="buttonNotifications"></div>
            </dialog>
        `;
        hideNotification();
        const notificationElement = document.getElementById('notification');
        expect(notificationElement.style.visibility).toBe('hidden');
        expect(notificationElement.style.opacity).toBe('0');
    });

    test('hidden Notification should hide the notification element with Update Entity visible', () => {
        document.body.innerHTML = `
            <input class="input">
            <input class="input">
            <select class="select"></select>
            <button id="createButton"></button>
            <button class="btn-table"></button>
            <button class="btn-table"></button>
            <dialog id="updateEntity" style="visibility: visible; opacity: 1;">
                <input class="input-update" id="genderNameUpdate" disabled>
                <select class="select-update" id="genderStatusUpdate" disabled>
                    <option>Select option...</option>
                    <option>True</option>
                    <option>False</option>
                </select>
                <button id="saveButton" class="" disabled>Update gender</button>
                <button id="cancelUpdateBtn" class="btn-notification error-btn-notification" disabled">Cancel</button>
            </dialog>
            <dialog id="notification" style="visibility: visible; opacity: 1;">
                <div id="imgNotifications">
                    <img id="imgNotification" src="res/ing/error.png" alt="">
                </div id="textNotifications">
                <div id="txtNotifications"></div>
                <div id="buttonNotifications"></div>
            </dialog>
        `;
        hideNotification();
        let notificationElement = document.getElementById('notification');
        expect(notificationElement.style.visibility).toBe('hidden');
        expect(notificationElement.style.opacity).toBe('0');

        notificationElement = document.getElementById('genderNameUpdate');
        expect(notificationElement.disabled).toBeFalsy();

        notificationElement = document.getElementById('genderStatusUpdate');
        expect(notificationElement.disabled).toBeFalsy();

        notificationElement = document.getElementById('saveButton');
        expect(notificationElement.disabled).toBeFalsy();

        notificationElement = document.getElementById('cancelUpdateBtn');
        expect(notificationElement.disabled).toBeFalsy();
    });
});