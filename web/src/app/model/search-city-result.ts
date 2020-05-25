import { City } from './city';

export class SearchCitiesResult {
    data : City[];
    constructor() {
        if(this.data===undefined) this.data = null;
    }
}