function disabledElements(action) {
    let buttons = document.getElementsByClassName('input');
    for (let button of buttons) {
        button.disabled = action;
    }

    buttons = document.getElementsByClassName('select');
    for (let button of buttons) {
        button.disabled = action;
    }

    buttons = document.getElementById('createButton');
    buttons.disabled = action;

    buttons = document.getElementsByClassName('btn-table');
    for (let button of buttons) {
        button.disabled = action;
    }
}

function hiddenUpdateEntity() {
    let updateElement = document.getElementById('updateEntity');
    updateElement.style.visibility = 'hidden';
    updateElement.style.opacity = '0';
    disabledElements(false);
}

function disabledUpdateEntityElements(updateElement, action) {
    for (let element of updateElement) {
        element.disabled = action;
    }
}

function dateToText(date, split, showHour) {
    let dateText = '';
    if (date.getDate() < 10) {
        dateText = dateText + '0';
    }
    dateText = dateText + date.getDate() + split;
    if (date.getMonth() < 9) {
        dateText = dateText + '0';
    }
    dateText = dateText + (date.getMonth() + 1) + split + date.getFullYear();

    if (showHour) {
        dateText = dateText + ' ';
        if (date.getHours() < 10) {
            dateText = dateText + '0';
        }
        dateText = dateText + date.getHours() + ':';
        if (date.getMinutes() < 10) {
            dateText = dateText + '0';
        }
        dateText = dateText + date.getMinutes() + ':';
        if (date.getSeconds() < 10) {
            dateText = dateText + '0';
        }
        dateText = dateText + date.getSeconds();
    }
    return dateText;
}

function dateToInput(date) {
    date = new Date(date);
    date = date.getFullYear() + '-' +
        ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
        ('0' + date.getDate()).slice(-2) + 'T' +
        ('0' + date.getHours()).slice(-2) + ':' +
        ('0' + date.getMinutes()).slice(-2) + ':' +
        ('0' + date.getSeconds()).slice(-2);
    return date;
}

/*module.exports = {
    disabledElements,
    hiddenUpdateEntity,
    disabledUpdateEntityElements,
    dateToText,
    dateToInput
};*/