export class City {
    id?: number;
    name?: string;
    country?: string;
    countryCode?: string;

    constructor() {
        if (this.id === undefined) this.id = null;
        if (this.name === undefined) this.name = null;
        if (this.country === undefined) this.country = null;
        if (this.countryCode === undefined) this.countryCode = null;
    }
}