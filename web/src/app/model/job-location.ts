export class JobLocation {
    display_name: string;
    area: Array<string>;

    constructor() {
        if (this.display_name === undefined) this.display_name = null;
        if (this.area === undefined) this.area = null;
    }
}