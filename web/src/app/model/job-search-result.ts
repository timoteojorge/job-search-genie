import { Job } from "./job";

export class SearchJobsResult {
     count : number;
     results : Array<Job>;

    constructor() {
        if(this.count===undefined) this.count = null;
        if(this.results===undefined) this.results = null;
    }
}