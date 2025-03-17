import { HttpHeaders } from "@angular/common/http";

export const environment = {
    name: 'Parameters',
    httpOptions: {
        headers: new HttpHeaders(
            {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(`jers:parameters`),
                'Accept': 'application/json'
            }
        )
    },
    paths: {
        globalPath: 'http://192.168.1.9:8001/parameters',
        subPath: {
            cityPath: 'city',
            cityToCityPath: '/city_to_cities',
            cityTypePath: '/city_types',
            civilStatusPath: '/civil_statuses',
            countryPath: '/countries',
            documentTypePath: '/document_types',
            genderPath: '/genders',
            sectorPath: '/sectors',
            statePath: '/states',
            telephoneTypePath: '/telephone_types'
        },
        functionPath: {
            createPath: '/create',
            deletePath: '/delete/',
            getAllPath: '/get-all',
            getIdPath: '/get-id/',
            updatePath: '/update'
        }
    },
    displayedColumns: {
        city: ['idCity', 'cityName', 'idState', 'idCityType', 'landlinePhoneIdentifier', 'area', 'minimumTemperature', 'maximumTemperature', 'heightAboveSeaLevel', 'edit', 'delete'],
        cityToCity: ['idCityToCity', 'idOriginCity', 'idDestinationCity', 'distance', 'routeNumbers', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        cityType: ['idCityType', 'cityTypeName', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        civilStatus: ['idCivilStatus', 'civilStatusName', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        country: ['idCountry', 'countryName', 'phoneIdentifier', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        documentType: ['idDocumentType', 'documentTypeName', 'documentTypeAcronym', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        gender: ['idGender', 'genderName', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        sector: ['idSector', 'sectorName', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        state: ['idState', 'stateName', 'idCountry', 'landlinePhoneIdentifier', 'status', 'creationDate', 'updateDate', 'edit', 'delete'],
        telephoneType: ['idTelephoneType', 'telephoneTypeName', 'status', 'creationDate', 'updateDate', 'edit', 'delete']
    }
};