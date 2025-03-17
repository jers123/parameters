const {
    createData,
    updateData,
    displayDataGender,
    cleanFields,
    showUpdateEntity,
    disabledUpdateEntity,
    questionUpdate  
} = require('./gender');
const {dateToInput} = require('./system');


jest.mock('./apis', () => ({
    getCreate: jest.fn()
}));

describe('createData function', () => {
    beforeEach(() => {
        document.getElementById = jest.fn().mockImplementation(() => ({
            value: '',
        }));
    });

    it('should call getCreate with correct parameters when status is none', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatus' ? 'none' : '',
        }));

        createData();

        expect(document.getElementById).toHaveBeenCalledWith('genderStatus');
        expect(document.getElementById).toHaveBeenCalledWith('genderName');
        expect(getCreate).toHaveBeenCalledWith(expect.any(String), { genderName: '', status: true });
    });

    it('should call getCreate with correct parameters when status is true', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatus' ? 'true' : '',
        }));

        createData();

        expect(document.getElementById).toHaveBeenCalledWith('genderStatus');
        expect(document.getElementById).toHaveBeenCalledWith('genderName');
        expect(getCreate).toHaveBeenCalledWith(expect.any(String), { genderName: '', status: true });
    });

    it('should call getCreate with correct parameters when status is false', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatus' ? 'false' : '',
        }));

        createData();

        expect(document.getElementById).toHaveBeenCalledWith('genderStatus');
        expect(document.getElementById).toHaveBeenCalledWith('genderName');
        expect(getCreate).toHaveBeenCalledWith(expect.any(String), { genderName: '', status: false });
    });
});

describe('updateData function', () => {
    beforeEach(() => {
        document.getElementById = jest.fn().mockImplementation((id) => ({
            value: '',
        }));
    });

    it('should return gender object with status true when statusValue is true', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatusUpdate' ? 'true' : '',
        }));

        const result = updateData();

        expect(result).toEqual({
            idGender: '',
            genderName: '',
            status: true
        });
    });

    it('should return gender object with status false when statusValue is false', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatusUpdate' ? 'false' : '',
        }));

        const result = updateData();

        expect(result).toEqual({
            idGender: '',
            genderName: '',
            status: false
        });
    });

    it('should return gender object without status when statusValue is none', () => {
        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderStatusUpdate' ? 'none' : '',
        }));

        document.getElementById.mockImplementation((id) => ({
            value: id === 'genderIdUpdate' ? '123' : 'Test Gender',
        }));

        const result = updateData();
        expect(result).toEqual({
            idGender: '123',
            genderName: 'Test Gender',
            status: false
        });
    });
});

describe('cleanFields function', () => {
    test('should clear input fields', () => {
        document.body.innerHTML = `
            <input id="genderName" type="text" value="Test Gender">
            <select id="genderStatus">
                <option value="none">None</option>
                <option value="true">Active</option>
                <option value="false">Inactive</option>
            </select>
        `;

        console.log(document.body.innerHTML);

        cleanFields();
        expect(document.getElementById('genderName').value).toBe('');
        expect(document.getElementById('genderStatus').value).toBe('none');
    });
});

describe('showUpdateEntity function', () => {
    test('should update elements correctly with reply message', () => {
        document.body.innerHTML = `
            <dialog id="updateEntity" style="visibility: hidden; opacity: 0;">
                <input id="genderIdUpdate" type="number">
                <input id="genderNameUpdate" type="text">
                <input id="genderStatusUpdate" type="text">
                <input id="genderCreationDateUpdate" type="text">
                <input id="genderUpdateDateUpdate" type="text">
            </dialog>
        `;
        
        const replyMessage = {
            object: {
                idGender: 1,
                genderName: 'Test Gender',
                status: true,
                creationDate: new Date(),
                updateDate: new Date()
            }
        };

        showUpdateEntity(replyMessage);

        expect(document.getElementById('genderIdUpdate').value).toBe(1);
        expect(document.getElementById('genderNameUpdate').value).toBe('Test Gender');
        expect(document.getElementById('genderStatusUpdate').value).toBe(true);
        expect(document.getElementById('genderCreationDateUpdate').value).toBe(dateToInput(replyMessage.object.creationDate));
        expect(document.getElementById('genderUpdateDateUpdate').value).toBe(dateToInput(replyMessage.object.updateDate));
        expect(document.getElementById('updateEntity').style.visibility).toBe('visible');
        expect(document.getElementById('updateEntity').style.opacity).toBe('1');
    });
});

describe('disabledUpdateEntity function', () => {
    test('should disable elements when action is true', () => {
        document.body.innerHTML = `
            <input id="genderNameUpdate" type="text">
            <select id="genderStatusUpdate"></select>
            <button id="saveButton">Save</button>
            <button id="cancelUpdateBtn">Cancel</button>
        `;
        
        disabledUpdateEntity(true);

        let elements = document.getElementById('genderNameUpdate');
        expect(elements.disabled).toBeTruthy();
        
        elements = document.getElementById('genderStatusUpdate');
        expect(elements.disabled).toBeTruthy();
        
        elements = document.getElementById('saveButton');
        expect(elements.disabled).toBeTruthy();
        
        elements = document.getElementById('cancelUpdateBtn');
        expect(elements.disabled).toBeTruthy();
    });

    test('should enable elements when action is false', () => {
        document.body.innerHTML = `
            <input id="genderNameUpdate" type="text" disabled>
            <select id="genderStatusUpdate" disabled></select>
            <button id="saveButton" disabled>Save</button>
            <button id="cancelUpdateBtn" disabled>Cancel</button>
        `;
        
        disabledUpdateEntity(false);

        let elements = document.getElementById('genderNameUpdate');
        expect(elements.disabled).toBeFalsy();
        
        elements = document.getElementById('genderStatusUpdate');
        expect(elements.disabled).toBeFalsy();
        
        elements = document.getElementById('saveButton');
        expect(elements.disabled).toBeFalsy();
        
        elements = document.getElementById('cancelUpdateBtn');
        expect(elements.disabled).toBeFalsy();
    });
});

