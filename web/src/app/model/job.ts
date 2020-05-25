import { Company } from "./company";
import { Category } from './category';

export class Job {
    category?: Category;
    adref?: string;
    redirect_url?: string;
    description?: string;
    id?: string;
    salary_is_predicted?: string;
    company?: Company;
    salary_min?: number;
    latitude?: number;
    salary_max?: number;
    title?: string;
    created?: string;
    location?: Location;
    longitude?: number;

    constructor() {
        if (this.category === undefined) this.category = null;
        if (this.adref === undefined) this.adref = null;
        if (this.redirect_url === undefined) this.redirect_url = null;
        if (this.description === undefined) this.description = null;
        if (this.id === undefined) this.id = null;
        if (this.salary_is_predicted === undefined) this.salary_is_predicted = null;
        if (this.company === undefined) this.company = null;
        if (this.salary_min === undefined) this.salary_min = 0;
        if (this.latitude === undefined) this.latitude = 0;
        if (this.salary_max === undefined) this.salary_max = 0;
        if (this.title === undefined) this.title = null;
        if (this.created === undefined) this.created = null;
        if (this.location === undefined) this.location = null;
        if (this.longitude === undefined) this.longitude = 0;
    }
}



