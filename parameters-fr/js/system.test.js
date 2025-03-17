const {
    disabledElements,
    hiddenUpdateEntity,
    disabledUpdateEntityElements,
    dateToText,
    dateToInput
} = require('./system');

describe('disabledElements function', () => {
    test('should disable all elements with class "input"', () => {
        document.body.innerHTML = `
        <input class="input">
        <input class="input">
        <select class="select"></select>
        <button id="createButton"></button>
        <button class="btn-table"></button>
        <button class="btn-table"></button>
      `;

        disabledElements(true);

        let elements = document.querySelectorAll('.input');
        elements.forEach(input => {
            expect(input.disabled).toBeTruthy();
        });

        elements = document.querySelectorAll('.select');
        elements.forEach(select => {
            expect(select.disabled).toBeTruthy();
        });

        elements = document.getElementById('createButton');
        expect(elements.disabled).toBeTruthy();

        elements = document.querySelectorAll('.btn-table');
        elements.forEach(button => {
            expect(button.disabled).toBeTruthy();
        });
    });
});

describe('hiddenUpdateEntity function', () => {
    test('should hide update entity element and enable disabled elements', () => {
        document.body.innerHTML = `
        <input class="input" disabled>
        <input class="input" disabled>
        <select class="select" disabled></select>
        <button id="createButton" disabled></button>
        <button class="btn-table" disabled></button>
        <button class="btn-table" disabled></button>
        <dialog id="updateEntity" style="visibility: visible; opacity: 1;">
          <input class="input" disabled>
          <button class="btn-table" disabled></button>
        </dialog>
      `;

        hiddenUpdateEntity();

        let elements = document.getElementById('updateEntity');
        expect(elements.style.visibility).toBe('hidden');
        expect(elements.style.opacity).toBe('0');

        elements = document.querySelectorAll('.input');
        elements.forEach(input => {
            expect(input.disabled).toBeFalsy();
        });

        elements = document.querySelectorAll('.select');
        elements.forEach(select => {
            expect(select.disabled).toBeFalsy();
        });

        elements = document.getElementById('createButton');
        expect(elements.disabled).toBeFalsy();

        elements = document.querySelectorAll('.btn-table');
        elements.forEach(button => {
            expect(button.disabled).toBeFalsy();
        });
    });
});

describe('disabledUpdateEntityElements function', () => {
    test('should disable all elements in the array when action is true', () => {
        const updateElement = [document.createElement('input'), document.createElement('button')];
        disabledUpdateEntityElements(updateElement, true);
        expect(updateElement[0].disabled).toBe(true);
        expect(updateElement[1].disabled).toBe(true);
    });

    test('should enable all elements in the array when action is false', () => {
        const updateElement = [document.createElement('input'), document.createElement('button')];
        updateElement[0].disabled = true;
        updateElement[1].disabled = true;
        disabledUpdateEntityElements(updateElement, false);
        expect(updateElement[0].disabled).toBe(false);
        expect(updateElement[1].disabled).toBe(false);
    });

    test('should do nothing with an empty array', () => {
        const updateElement = [];
        disabledUpdateEntityElements(updateElement, true);
        expect(updateElement.length).toBe(0);
    });

    test('should ignore non-element values in the array', () => {
        const updateElement = [document.createElement('input'), 'string', 10];
        disabledUpdateEntityElements(updateElement, true);
        expect(updateElement[0].disabled).toBe(true);
        expect(updateElement[2]).toBe(10);
    });
});

describe('dateToText function', () => {
    test('should format date without time', () => {
        const date = new Date(2024, 1, 23);
        const formattedDate = dateToText(date, '-');
        expect(formattedDate).toBe('23-02-2024');
    });

    test('should format date with time', () => {
        const date = new Date(2024, 1, 23, 10, 25, 30);
        const formattedDate = dateToText(date, '-', true);
        expect(formattedDate).toBe('23-02-2024 10:25:30');
    });

    test('should format date with single digit date', () => {
        const date = new Date(2024, 0, 1);
        const formattedDate = dateToText(date, '/');
        expect(formattedDate).toBe('01/01/2024');
    });

    test('should format date with single digit month', () => {
        const date = new Date(2024, 4, 15);
        const formattedDate = dateToText(date, '-', true);
        expect(formattedDate).toBe('15-05-2024 00:00:00');
    });

    test('should format date with month 0', () => {
        const date = new Date(2024, 11, 31);
        const formattedDate = dateToText(date, '.');
        expect(formattedDate).toBe('31.12.2024');
    });
});

describe('dateToInput function', () => {
    test('should convert valid date object to formatted string', () => {
        const date = new Date(2024, 1, 23, 10, 25, 30);
        const expectedOutput = '2024-02-23T10:25:30';
        const formattedDate = dateToInput(date);
        expect(formattedDate).toBe(expectedOutput);
    });

    test('should format date with single digit date and month', () => {
        const date = new Date(2024, 0, 1, 12, 34, 56);
        const expectedOutput = '2024-01-01T12:34:56';
        const formattedDate = dateToInput(date);
        expect(formattedDate).toBe(expectedOutput);
    });

    test('should format date with leading zeros for single digit time values', () => {
        const date = new Date(2024, 1, 23, 0, 5, 9);
        const expectedOutput = '2024-02-23T00:05:09';
        const formattedDate = dateToInput(date);
        expect(formattedDate).toBe(expectedOutput);
    });
});